package net.henrylang.calcy.application.evaluator;

import net.henrylang.calcy.Calcy;
import net.henrylang.calcy.Screen;
import net.henrylang.calcy.Utils;
import net.henrylang.calcy.application.GrapherApplication;
import net.henrylang.calcy.application.InputApplication;
import net.henrylang.calcy.application.UpdateResult;
import net.henrylang.calcy.evaluate.*;

import static net.henrylang.calcy.CalcySpec.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluatorApplication extends InputApplication {
    Environment env = Environment.defaultEnv();
    List<HistoryEntry> history = new ArrayList<>();

    private static final int MAX_COLS = SCREEN_COLS / (GLYPH_COLS + 1);

    @Override
    public UpdateResult update(long frameCount, Screen screen) {
        super.update(frameCount, screen);
        int maxCols = SCREEN_COLS / (GLYPH_COLS + 1);
        screen.fill(false);
        if(input.length > maxCols) {
            int overflow = input.length - SCREEN_COLS / (GLYPH_COLS + 1);
            screen.drawGlyphs(this.input, 1, 1 - (GLYPH_COLS + 1) * overflow, 1);
        } else {
            screen.drawGlyphs(this.input, 1, 1, 1);
        }

        var fittedResponses = SCREEN_ROWS / (GLYPH_ROWS + 1) / 2 + 1;

        for(int i = 0; i < Math.min(history.size(), fittedResponses); i++) {
            var entry = history.get(history.size() - i - 1);
            screen.drawGlyphs(entry.input(), (1 + GLYPH_ROWS) * i * 2 + 1 + GLYPH_ROWS + 1, 1, 1);
            screen.drawGlyphs(entry.response(), (1 + GLYPH_ROWS) * (i * 2 + 1) + 1 + GLYPH_ROWS + 1, SCREEN_COLS - (GLYPH_COLS + 1) * entry.response().length, 1);
        }

        if((this.cursorFrames / 30) % 2 == 0) {
            screen.drawGlyph(224, 1, Math.min(SCREEN_COLS - 3, 1 + GLYPH_COLS * this.input.length + this.input.length));
        }
        return UpdateResult.OK;
    }

    @Override
    protected boolean submitInput() {
        if(this.input.length == 0) {
            return false;
        }

        if(Arrays.equals(this.input, Utils.stringToGlyphs("graph"))) { // I know this is "inefficient" but idc
            Calcy.getInstance().runApplication(new GrapherApplication());
            return true;
        }

        try {
            var tokens = Tokenizer.tokenize(this.input);
            var node = new Parser(tokens).getExpression();
            var response = node.eval(env);

            env.insertVar("ans", response);
            history.add(new HistoryEntry(input, Utils.stringToGlyphs(formatDouble(response))));
        } catch (EvaluateException e) {
            history.add(new HistoryEntry(input, e.getGlyphs()));
        }

        return true;
    }

    private String formatDouble(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        DecimalFormat scientificFormat = new DecimalFormat("0.#####E0");

        String formatted = decimalFormat.format(value);
        if (formatted.length() > MAX_COLS) {
            formatted = scientificFormat.format(value);
        }

        return formatted;
    }
}
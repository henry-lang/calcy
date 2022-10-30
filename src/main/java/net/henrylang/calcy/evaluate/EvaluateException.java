package net.henrylang.calcy.evaluate;

import net.henrylang.calcy.Utils;

public class EvaluateException extends java.lang.Exception {
    private final int[] glyphs;

    /*public Exception(int[] glyphs) {
        this.glyphs = glyphs;
    }*/

    public EvaluateException(String string) {
        this.glyphs = Utils.stringToGlyphs(string);
    }

    public int[] getGlyphs() {
        return this.glyphs;
    }
}
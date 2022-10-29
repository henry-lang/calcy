package net.henrylang.calcy.application;

import net.henrylang.calcy.Screen;

import java.util.Arrays;

import static processing.core.PConstants.*;

public abstract class InputApplication implements Application {
    protected int[] input = new int[] {};
    protected long cursorFrames = 0;

    protected abstract boolean submitInput(); // Returns whether to clear input array afterwards

    @Override
    public ApplicationUpdateResult update(long frameCount, Screen screen) {
        cursorFrames += 1;
        return ApplicationUpdateResult.OK;
    }

    @Override
    public void handleInput(char key, int keyCode) {
        this.cursorFrames = 0;
        switch(keyCode) {
            case ENTER: {
                if(this.submitInput()) this.input = new int[] {};
                break;
            }
            case BACKSPACE: {
                this.input = Arrays.copyOf(this.input, this.input.length - 1);
                break;
            }
            case SHIFT:
            case CONTROL:
            case 32: // Space bar
            case ALT: break;
            default: {
                this.input = Arrays.copyOf(this.input, this.input.length + 1);
                this.input[this.input.length - 1] = key;
                break;
            }
        }
    }
}

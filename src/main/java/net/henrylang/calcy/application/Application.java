package net.henrylang.calcy.application;

import net.henrylang.calcy.Screen;

public interface Application {
    default UpdateResult update(long frameCount, Screen screen) {
        screen.fill(true);
        return UpdateResult.OK;
    }

    default void handleInput(char key, int keyCode) {}
}
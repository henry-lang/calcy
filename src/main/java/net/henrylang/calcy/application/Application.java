package net.henrylang.calcy.application;

import net.henrylang.calcy.Screen;

public interface Application {
    default ApplicationUpdateResult update(long frameCount, Screen screen) {
        screen.fill(true);
        return ApplicationUpdateResult.OK;
    }

    default void handleInput(char key, int keyCode) {}
}
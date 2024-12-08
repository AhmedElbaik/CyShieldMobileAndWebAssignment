package com.testing.todorvachev.enums;

public final class TestConstants {
    private TestConstants() {} // Prevent instantiation

    public enum TestRunType {
        LOCAL,
        GRID,
        ANDROID
    }

    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }
}

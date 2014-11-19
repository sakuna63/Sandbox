package com.sandbox.common;

public enum Color {
    RED, BLUE, YELLOW;

    public static Color[] valuesExceptWith(Color color) {
        if (color == RED) {
            return new Color[] {BLUE, YELLOW};
        }
        else if (color == BLUE) {
            return new Color[] {RED, YELLOW};
        }
        else {
            return new Color[] {RED, BLUE};
        }
    }
}

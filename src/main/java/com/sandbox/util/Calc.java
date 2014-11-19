package com.sandbox.util;

public final class Calc {
    public static int factorial(int n){
        return n <= 1 ? 1 : n * factorial(n-1);
    }

    public static int sigma(int n) {
        return n == 1 ? 1 : n + sigma(n-1);
    }
}

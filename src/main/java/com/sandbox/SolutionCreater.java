package com.sandbox;

import java.util.Random;

public class SolutionCreater {

    public static Color[] createSolution(int n, Random rnd) {
        Color[] solution = new Color[n];

        for (int i = 0; i < solution.length; i++) {
            solution[i] = Color.values()[rnd.nextInt(Color.values().length)];
        }
        return solution;
    }

    enum Color {
        RED, BLUE, YELLOW
    }
}

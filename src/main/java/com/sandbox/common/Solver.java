package com.sandbox.common;

public interface Solver<E extends Problem, T extends Result> {

    public T solve(E problem);

    public static int countViolation(boolean[][] link, Color[] solution) {
        int count = 0;
        for(int i = 0; i < link.length; i++) {
            for (int j = i+1; j < link[i].length; j++) {
                if (link[i][j] && solution[i] == solution[j]) {
                    count++;
                }
            }
        }
        return count;
    }
}

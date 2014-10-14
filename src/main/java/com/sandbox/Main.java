package com.sandbox;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 9;
        boolean[][] links = new boolean[n][n];
        long seed = Calendar.getInstance().getTimeInMillis();
        Random rand = new Random(seed);

        for (int i = n/3; i < n/3*2; i++) {
            for (int j = 0; j < n/3; j++) {
                links[j][i] = rand.nextBoolean();
            }
        }

        for (int i = n/3*2; i < n; i++) {
            for (int j = 0; j < n/3; j++) {
                links[j][i] = rand.nextBoolean();
            }
        }

        for (int i = n/3*2; i < n; i++) {
            for (int j = n/3; j < n/3*2; j++) {
                links[j][i] = rand.nextBoolean();
            }
        }

        IO.p("seed: " + seed);
        for (boolean[] l : links) {
            IO.p(Arrays.toString(l));
        }
    }
}

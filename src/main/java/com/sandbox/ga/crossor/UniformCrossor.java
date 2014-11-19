package com.sandbox.ga.crossor;

import com.sandbox.common.Color;
import com.sandbox.ga.Generation;

import java.util.Random;

public class UniformCrossor implements Crossor {
    private final Random rnd;

    public UniformCrossor(Random rnd) {
        this.rnd = rnd;
    }

    @Override
    public Generation[] cross(Generation parent1, Generation parent2) {
        int num = parent1.solution.length;
        boolean[] mask = createMask(num, rnd);
        Color[] childSolution1 = new Color[num], childSolution2 = new Color[num];

        for (int i = 0; i < mask.length; i++) {
            if (mask[i]) {
                childSolution1[i] = parent1.solution[i];
                childSolution2[i] = parent2.solution[i];
            } else {
                childSolution1[i] = parent2.solution[i];
                childSolution2[i] = parent1.solution[i];
            }
        }

        return new Generation[]{
                new Generation(childSolution1),
                new Generation(childSolution2)
        };
    }

    private boolean[] createMask(int num, Random rnd) {
        boolean[] mask = new boolean[num];
        for (int i = 0; i < num; i++) {
            mask[i] = rnd.nextBoolean();
        }
        return mask;
    }

}

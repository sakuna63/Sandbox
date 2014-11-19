package com.sandbox.ga.scaler;

import com.sandbox.ga.Generation;

import java.util.Arrays;

public class LinearScaler extends Scaler {

    @Override
    public Generation[] scale(Generation[] gens) {
        Generation[] copyGens = new Generation[gens.length];
        for (int i = 0; i < gens.length; i++) {
            copyGens[i] = (Generation) gens[i].clone();
        }
        Arrays.sort(copyGens);

        double max = copyGens[0].point;
        double min = copyGens[copyGens.length-1].point;
        double a = -min / (max - min);
        double b = 1 / (max - min);

        for (Generation gen : copyGens) {
            gen.point = a + b * gen.point;
        }

        return copyGens;
    }

    @Override
    public String getName() {
        return "線形スケーリング";
    }
}

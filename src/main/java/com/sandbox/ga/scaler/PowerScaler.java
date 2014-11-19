package com.sandbox.ga.scaler;

import com.sandbox.ga.Generation;

import java.util.Arrays;

public class PowerScaler extends Scaler {
    private static final int D = 2;

    @Override
    public Generation[] scale(Generation[] gens) {
        Generation[] copyGens = gens.clone();
        for (int i = 0; i < gens.length; i++) {
            copyGens[i] = (Generation) gens[i].clone();
        }
        Arrays.sort(copyGens);

        for (Generation gen : copyGens) {
            gen.point = Math.pow(gen.point, D);
        }

        return copyGens;
    }

    @Override
    public String getName() {
        return "べき乗スケーリング";
    }
}

package com.sandbox.ga.selector;

import com.sandbox.ga.Generation;

import java.util.Random;

public class RouletteSelector extends Selector {
    double total;
    final Random rnd;

    public RouletteSelector(Random rnd) {
        this.rnd = rnd;
    }

    @Override
    public Generation select() {
        double threshold = rnd.nextDouble() * total;
        double temp = 0;
        for (Generation gen : gens) {
            temp += gen.point;
            if (temp >= threshold) {
                return gen;
            }
        }
        return gens[gens.length-1];
    }

    @Override
    public void reset(Generation[] gens) {
        super.reset(gens);
        this.total = 0;
        for (Generation gen : gens) {
            total += gen.point;
        }
    }
}

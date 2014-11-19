package com.sandbox.ga.selector;

import com.sandbox.ga.Generation;

public abstract class Selector {
    protected Generation[] gens;

    abstract public Generation select();

    public void reset(Generation[] gens) {
        this.gens = gens;
    }
}

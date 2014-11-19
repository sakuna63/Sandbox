package com.sandbox.ga.scaler;

import com.sandbox.ga.Generation;

public abstract class Scaler {
    public abstract Generation[] scale(Generation[] gens);
    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }
}

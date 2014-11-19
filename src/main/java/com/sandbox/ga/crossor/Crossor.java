package com.sandbox.ga.crossor;

import com.sandbox.ga.Generation;

public interface Crossor {
    public Generation[] cross(Generation parent1, Generation parent2);
}

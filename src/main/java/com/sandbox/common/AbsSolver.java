package com.sandbox.common;

import java.util.Random;

public abstract class AbsSolver<E extends Problem, T extends Result, V> implements Solver<E, T, V> {
    protected Random rnd;

    public AbsSolver(Random rnd) {
        this.rnd = rnd;
    }

    public void setRnd(Random rnd) {
        this.rnd = rnd;
    }
}

package com.sandbox.ga;

import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.ga.crossor.Crossor;
import com.sandbox.ga.scaler.Scaler;
import com.sandbox.ga.selector.Selector;

public class Builder {
    private boolean[][] link;
    private int n;
    private Type type;
    private int size;
    private int maxGen;
    private double variationThreshold;
    private Scaler scaler;
    private Crossor crossor;
    private Selector selector;
    private long seed;

    public Builder setLink(boolean[][] link) {
        this.link = link;
        return this;
    }

    public Builder setN(int n) {
        this.n = n;
        return this;
    }

    public Builder setType(Type type) {
        this.type = type;
        return this;
    }

    public Builder setSize(int size) {
        this.size = size;
        return this;
    }

    public Builder setMaxGen(int maxGen) {
        this.maxGen = maxGen;
        return this;
    }

    public Builder setVariationThreshold(double variationThreshold) {
        this.variationThreshold = variationThreshold;
        return this;
    }

    public Builder setScaler(Scaler scaler) {
        this.scaler = scaler;
        return this;
    }

    public Builder setCrossor(Crossor crossor) {
        this.crossor = crossor;
        return this;
    }

    public Builder setSelector(Selector selector) {
        this.selector = selector;
        return this;
    }

    public GAProblem createGAProblem() {
        return new GAProblem(seed, link, n, type, size, maxGen, variationThreshold, scaler, crossor, selector);
    }

    public Builder setSeed(long seed) {
        this.seed = seed;
        return this;
    }
}
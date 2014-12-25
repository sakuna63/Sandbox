package com.sandbox.ga;

import com.sandbox.common.Problem;
import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.ga.crossor.Crossor;
import com.sandbox.ga.scaler.Scaler;
import com.sandbox.ga.selector.Selector;

public class GAProblem extends Problem {
    public final int size;
    public final int maxGen;
    public final double variationThreshold;
    public final Scaler scaler;
    public final Crossor crossor;
    public final Selector selector;
    private final long seed;

    public GAProblem(long seed, boolean[][] link, int n, Type type, int size, int maxGen, double variationThreshold, Scaler scaler, Crossor crossor, Selector selector) {
        super(link, n, type);
        this.seed = seed;
        this.size = size;
        this.maxGen = maxGen;
        this.variationThreshold = variationThreshold;
        this.scaler = scaler;
        this.crossor = crossor;
        this.selector = selector;
    }


    @Override
    public String toTitle() {
        return String.format("問題:%s， スケール方法: %s， N=%d， 個体数=%d， 突然変異率=%f， シード=%d",
                type.getJPName(),
                scaler.getName(),
                n, size, variationThreshold * 100, seed
        );
    }

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
}

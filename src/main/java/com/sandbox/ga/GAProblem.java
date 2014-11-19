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
}

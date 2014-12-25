package com.sandbox.hga;

import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.ga.GAProblem;
import com.sandbox.ga.crossor.Crossor;
import com.sandbox.ga.scaler.Scaler;
import com.sandbox.ga.selector.Selector;

public class HGAProblem extends GAProblem {
    public HGAProblem(long seed, boolean[][] link, int n, Type type, int size, int maxGen, double variationThreshold, Scaler scaler, Crossor crossor, Selector selector) {
        super(seed, link, n, type, size, maxGen, variationThreshold, scaler, crossor, selector);
    }
}

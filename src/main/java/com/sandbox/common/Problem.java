package com.sandbox.common;

import com.sandbox.common.ProblemCreater.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class Problem {
    public final boolean[][] link;
    public final int n;
    public final Type type;
    public final List<Integer> violationPoints;

    public Problem(boolean[][] link, int n, Type type) {
        this.link = link;
        this.n = n;
        this.type = type;
        this.violationPoints = new ArrayList<>();
    }

    public abstract String toTitle();
}

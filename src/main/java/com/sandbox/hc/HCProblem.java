package com.sandbox.hc;

import com.sandbox.common.Problem;
import com.sandbox.common.ProblemCreater.Type;

public class HCProblem extends Problem {
    public final int loop;

    private HCProblem(boolean[][] link, int n, int loop, Type type) {
        super(link, n, type);
        this.loop = loop;
    }

    @Override
    public String toTitle() {
       return String.format("ノード数: %d 世代数: %d 問題: %s", n, loop, type.getJPName());
    }
}

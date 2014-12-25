package com.sandbox.ihc;

import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.hc.HCProblem;

public class IHCProblem extends HCProblem {

    public final int restart;

    public IHCProblem(boolean[][] link, int n, int loop, Type type, int restart) {
        super(link, n, type, loop);
        this.restart = restart;
    }
}

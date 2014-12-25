package com.sandbox.ihc;

import com.sandbox.common.AbsSolver;
import com.sandbox.common.Color;
import com.sandbox.hc.HCResult;
import com.sandbox.hc.HCSolver;

import java.util.Random;

public class IHCSolver extends AbsSolver<IHCProblem, IHCResult, Color[]>{
    private final HCSolver hcSolver;

    public IHCSolver(Random rnd) {
        super(rnd);
        hcSolver = new HCSolver(rnd);
    }

    @Override
    public IHCResult solve(Color[] solution, IHCProblem problem) {
        IHCResult result = new IHCResult();
        for (int i = 0; i < problem.restart; i++) {
            HCResult internalResult = hcSolver.solve(solution, problem);
            result.setSuccess(internalResult.isSuccess());
            result.loops.add(internalResult.loop);
            if (result.isSuccess()) {
                break;
            }
        }
        return result;
    }
}

package com.sandbox.hga;

import com.sandbox.ga.GAProblem;
import com.sandbox.ga.GAResult;
import com.sandbox.ga.GASolver;
import com.sandbox.ga.Generation;
import com.sandbox.hc.HCProblem;
import com.sandbox.hc.HCSolver;

import java.util.Random;

public class HGASolver extends GASolver {

    private final HCSolver hcSolver;
    private final int Ne;
    private final int loop;
    private HCProblem hcProblem;

    public HGASolver(Random rnd, int Ne, int loop) {
        super(rnd);
        this.hcSolver = new HCSolver(rnd);
        this.Ne = Ne;
        this.loop = loop;
    }

    @Override
    public GAResult solve(Generation[] solution, GAProblem problem) {
        this.hcProblem = new HCProblem(problem.link, problem.n, problem.type, loop);
        GAResult result = super.solve(solution, problem);
        int num = result.count / solution.length;
        result.count += num * loop * Ne;
        return result;
    }

    @Override
    protected void geneticCalculation(GAProblem problem, Generation[] gens) {
        super.geneticCalculation(problem, gens);
        // exec hc algorithm
        for (int i = 0; i < Ne; i++) {
            hcSolver.solve(gens[i].solution, hcProblem);
        }
    }
}

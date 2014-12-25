package com.sandbox.ga;

import com.sandbox.common.AbsSolver;
import com.sandbox.common.Color;

import java.util.Arrays;
import java.util.Random;

public class GASolver extends AbsSolver<GAProblem, GAResult, Generation[]> {

    public GASolver(Random rnd) {
        super(rnd);
    }

    @Override
    public GAResult solve(Generation[] solution, GAProblem problem) {
        GAResult result = new GAResult();
        addResult(result, solution);

        for (int i = 0; i < problem.maxGen; i++) {
            Generation[] parentScaled = problem.scaler.scale(solution);
            problem.selector.reset(parentScaled);
            geneticCalculation(problem, solution);
            addResult(result, solution);
            result.count += solution.length;
            if (solution[0].point == 1) {
                result.setSuccess(true);
                break;
            }
        }

        return result;
    }

    protected void geneticCalculation(GAProblem problem, Generation[] gens) {
        for (int j = 0; j < gens.length; j+=2) {
            Generation parent1 = problem.selector.select();
            Generation parent2 = problem.selector.select();
            Generation[] children = problem.crossor.cross(parent1, parent2);
            Generation child1 = variation(children[0], problem.variationThreshold);
            Generation child2 = variation(children[1], problem.variationThreshold);
            child1.calculatePoint(problem.link);
            child2.calculatePoint(problem.link);
            gens[j] = child1;
            if (j+1 < gens.length) {
                gens[j+1] = child2;
            }
        }
    }

    protected void addResult(GAResult result, Generation[] gens) {
        Arrays.sort(gens);
        result.max.add(gens[0].point);
        result.min.add(gens[gens.length-1].point);
        result.ave.add(Arrays.stream(gens).mapToDouble(c -> c.point).average().getAsDouble());
    }

    protected Generation variation(Generation child, double threshold) {
        for (int i = 0; i < child.solution.length; i++) {
            double prob = rnd.nextDouble();
            if (prob <= threshold) {
                Color[] candidate = Color.valuesExceptWith(child.solution[i]);
                child.solution[i] = candidate[rnd.nextInt(candidate.length)];
            }
        }
        return child;
    }
}

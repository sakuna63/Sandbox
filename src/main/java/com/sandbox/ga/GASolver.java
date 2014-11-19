package com.sandbox.ga;

import com.sandbox.common.AbsSolver;
import com.sandbox.common.Color;
import com.sandbox.common.SolutionCreater;

import java.util.Arrays;
import java.util.Random;

public class GASolver extends AbsSolver<GAProblem, GAResult> {

    public GASolver(Random rnd) {
        super(rnd);
    }

    @Override
    public GAResult solve(GAProblem problem) {
        Generation[] parent = new Generation[problem.size];
        for (int i = 0; i < parent.length; i++) {
            Color[] solution = SolutionCreater.createSolution(problem.n, rnd);
            parent[i] = new Generation(solution);
            parent[i].calculatePoint(problem.link);
        }

        GAResult result = new GAResult();
        Arrays.sort(parent);
        result.max.add(parent[0].point);
        result.min.add(parent[parent.length-1].point);
        result.ave.add(Arrays.stream(parent).mapToDouble(c -> c.point).average().getAsDouble());

        for (int i = 0; i < problem.maxGen; i++) {
            Generation[] parentScaled = problem.scaler.scale(parent);
            problem.selector.reset(parentScaled);
            for (int j = 0; j < parent.length; j+=2) {
                Generation parent1 = problem.selector.select();
                Generation parent2 = problem.selector.select();
                Generation[] children = problem.crossor.cross(parent1, parent2);
                Generation child1 = variation(children[0], problem.variationThreshold);
                Generation child2 = variation(children[1], problem.variationThreshold);
                child1.calculatePoint(problem.link);
                child2.calculatePoint(problem.link);
                parent[j] = child1;
                if (j+1 < parent.length) {
                    parent[j+1] = child2;
                }
            }

            Arrays.sort(parent);
            result.max.add(parent[0].point);
            result.min.add(parent[parent.length-1].point);
            result.ave.add(Arrays.stream(parent).mapToDouble(c -> c.point).average().getAsDouble());
//            IO.p(parent[0].point);
            if (parent[0].point == 1) {
                result.setSuccess(true);
                break;
            }
        }

        return result;
    }

    private Generation variation(Generation child, double threshold) {
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

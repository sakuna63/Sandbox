package com.sandbox.es;

import com.sandbox.common.AbsSolver;
import com.sandbox.common.Color;

import java.util.Arrays;
import java.util.Random;

public class ESSolver extends AbsSolver<ESProblem, ESResult, Generation[]> {

    public ESSolver(Random rnd) {
        super(rnd);
    }

    @Override
    public ESResult solve(Generation[] solution, ESProblem problem) {
        ESResult result = new ESResult();
        for (int i = 0; i < problem.maxGen; i++) {
            Generation[] children = genChildren(problem, solution);
            result.count += problem.lamda;
            children = problem.selectionType.selectNextGeneration(solution, children);
            problem.min.add(children[0].point);
            problem.max.add(children[children.length-1].point);
            problem.ave.add(Arrays.stream(children).mapToInt(c -> c.point).average().getAsDouble());
            solution = children;
            if (solution[0].point == 0) {
                break;
            }
        }

        result.setSuccess(solution[0].point == 0);
        return result;
    }

    private Generation[] genChildren(ESProblem problem, Generation[] parent) {
        Generation[] children = new Generation[problem.lamda];
        for (int i = 0; i < problem.lamda; i++) {
            children[i] = variation(problem, parent);
        }
        return children;
    }

    private Generation variation(ESProblem problem, Generation[] parent) {
        int parentIndex = rnd.nextInt(parent.length);
        int nodeIndex = rnd.nextInt(parent[0].solution.length);
        Color[] parentSolution = parent[parentIndex].solution;
        Color[] solution = Arrays.copyOf(parentSolution, parentSolution.length);
        solution[nodeIndex] = Color.values()[rnd.nextInt(Color.values().length)];
        Generation child = new Generation(solution);
        child.calculatePoint(problem.link);
        return child;
    }
}

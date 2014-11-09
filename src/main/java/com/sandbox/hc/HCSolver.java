package com.sandbox.hc;

import com.sandbox.common.AbsSolver;
import com.sandbox.common.SolutionCreater;
import com.sandbox.common.SolutionCreater.Color;
import com.sandbox.common.Solver;
import com.sandbox.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HCSolver extends AbsSolver<HCProblem> {
    public HCSolver(Random rnd) {
        super(rnd);
    }

    @Override
    public boolean solve(HCProblem problem) {
        Color[] solution = SolutionCreater.createSolution(problem.link.length, rnd);

        int violationCount = Solver.countViolation(problem.link, solution);
        int loopCount = 0;
        while(violationCount > 1 && loopCount++ < problem.loop) {
            int index = choiceViolentNode(problem.link, solution);
            switchColor(problem.link, solution, violationCount, index);
            violationCount = Solver.countViolation(problem.link, solution);
            problem.violationPoints.add(violationCount);
        }

        return violationCount == 0;
    }

    @SuppressWarnings("unchecked")
    private void switchColor(boolean[][] problem, Color[] solution, int violationCount, int index) {
        Color color = solution[index];
        Pair<Color, Integer>[] counts = new Pair[Color.values().length];
        for (int i = 0; i < Color.values().length; i++) {
            Color c = Color.values()[i];
            if (color == c) {
                counts[i] = new Pair<>(c, violationCount);
                continue;
            }
            solution[index] = c;
            counts[i] = new Pair<>(c, Solver.countViolation(problem, solution));
        }
        Arrays.sort(counts, (o1, o2) -> o1.second - o2.second);

        int bestScore = counts[0].second;
        List<Color> bestColor = new ArrayList<>();
        for (Pair<Color, Integer> pair : counts) {
            if (bestScore == pair.second) {
                bestColor.add(pair.first);
            }
        }

        solution[index] = bestColor.get(rnd.nextInt(bestColor.size()));
    }

    private int choiceViolentNode(boolean[][] link, Color[] solution) {
        List<Integer> violations = new ArrayList<>();
        for(int i = 0; i < link.length; i++) {
            for (int j = i+1; j < link[i].length; j++) {
                if (link[i][j] && solution[i] == solution[j]) {
                    violations.add(i);
                    break;
                }
            }
        }
        return violations.get(rnd.nextInt(violations.size()));
    }
}

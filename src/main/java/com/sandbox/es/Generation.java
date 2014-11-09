package com.sandbox.es;

import com.sandbox.common.SolutionCreater.Color;
import com.sandbox.common.Solver;

public class Generation implements Comparable<Generation> {
    public final Color[] solution;
    public int point;

    public Generation(Color[] solution) {
        this.solution = solution;
    }

    public int calculatePoint(boolean[][] links) {
        point = Solver.countViolation(links, solution);
        return point;
    }

    @Override
    public int compareTo(Generation o) {
        return this.point - o.point;
    }
}

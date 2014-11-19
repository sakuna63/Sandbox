package com.sandbox.ga;

import com.sandbox.common.Color;
import com.sandbox.common.Solver;
import com.sandbox.util.Calc;

import java.util.Arrays;

public class Generation implements Comparable<Generation>, Cloneable {
    public final Color[] solution;
    public int violation;
    public double point;

    public Generation(Color[] solution) {
        this.solution = solution;
    }

    public double calculatePoint(boolean[][] links) {
        violation = Solver.countViolation(links, solution);
        point = 1 - (double) violation / Calc.sigma(links.length-1);
        return point;
    }

    @Override
    public int compareTo(Generation o) {
        double diff = o.point - this.point;
        if (diff > 0) {
            return 1;
        }
        else if (diff < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }

    @SuppressWarnings({"CloneDoesntCallSuperClone", "CloneDoesntDeclareCloneNotSupportedException"})
    @Override
    public Object clone() {
        Generation clone = new Generation(Arrays.copyOf(solution, solution.length));
        clone.violation = this.violation;
        clone.point = this.point;
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Generation that = (Generation) o;

        if (Double.compare(that.point, point) != 0) return false;
        if (violation != that.violation) return false;
        if (!Arrays.equals(solution, that.solution)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = solution != null ? Arrays.hashCode(solution) : 0;
        result = 31 * result + violation;
        temp = Double.doubleToLongBits(point);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

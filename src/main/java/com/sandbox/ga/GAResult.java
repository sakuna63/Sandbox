package com.sandbox.ga;

import com.sandbox.common.Result;

import java.util.ArrayList;

public class GAResult extends Result {
    public final ArrayList<Double> min;
    public final ArrayList<Double> max;
    public final ArrayList<Double> ave;

    protected GAResult() {
        min = new ArrayList<>();
        max = new ArrayList<>();
        ave = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "GAResult{" +
                "isSuccess=" + isSuccess() +
                ", min=" + min.get(min.size()-1) +
                ", max=" + max.get(min.size()-1) +
                ", ave=" + ave.get(min.size()-1) +
                '}';
    }
}

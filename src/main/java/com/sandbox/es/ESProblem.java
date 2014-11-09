package com.sandbox.es;

import com.sandbox.common.Problem;
import com.sandbox.common.ProblemCreater;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ESProblem extends Problem {
    public final int mu;
    public final int lamda;
    public final int maxGen;
    public final Type selectionType;
    public final List<Integer> max;
    public final List<Integer> min;
    public final List<Double> ave;

    public ESProblem(boolean[][] link, int n, int maxGen, ProblemCreater.Type type, Type selectionType, int mu, int lamda) {
        super(link, n, type);
        this.maxGen = maxGen;
        this.mu = mu;
        this.lamda = lamda;
        this.selectionType = selectionType;
        max = new ArrayList<>();
        min = new ArrayList<>();
        ave = new ArrayList<>();
    }

    @Override
    public String toTitle() {
       return String.format("ノード数: %d 世代数: %d 問題: %s μ: %d λ: %d 突然変異方法: %s", n, maxGen, type.getJPName(), mu, lamda, selectionType.getJpName());
    }

    public enum Type {
        PLUS("(μ+λ)") {
            @Override
            public Generation[] selectNextGeneration(Generation[] parent, Generation[] child) {
                child = ArrayUtils.addAll(parent, child);
                return CONMA.selectNextGeneration(parent, child);
            }
        },
        CONMA("(μ，λ)") {
            @Override
            public Generation[] selectNextGeneration(Generation[] parent, Generation[] child) {
                Arrays.sort(child);
                return Arrays.copyOf(child, parent.length);
            }
        };

        private final String jpName;

        Type(String jpName) {
            this.jpName = jpName;
        }

        public abstract Generation[] selectNextGeneration(Generation[] parent, Generation[] child);

        public String getJpName() {
            return jpName;
        }
    }
}

package com.sandbox;

import com.sandbox.common.Color;
import com.sandbox.common.Problem;
import com.sandbox.common.ProblemCreater;
import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.common.Result;
import com.sandbox.common.SolutionCreater;
import com.sandbox.es.ESProblem;
import com.sandbox.es.ESResult;
import com.sandbox.es.ESSolver;
import com.sandbox.es.Generation;
import com.sandbox.ga.GAProblem;
import com.sandbox.ga.GAResult;
import com.sandbox.ga.GASolver;
import com.sandbox.ga.crossor.UniformCrossor;
import com.sandbox.ga.scaler.LinearScaler;
import com.sandbox.ga.selector.RouletteSelector;
import com.sandbox.hga.HGAProblem;
import com.sandbox.hga.HGASolver;
import com.sandbox.ihc.IHCProblem;
import com.sandbox.ihc.IHCResult;
import com.sandbox.ihc.IHCSolver;
import com.sandbox.util.IO;
import com.sandbox.util.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    private final static int[] N = new int[]{60, 90, 120, 150};
    private final static long[] SEED = new long[]{113, 367, 647, 947, 491, 997, 839, 1733, 271, 569};
    private final static ProblemCreater DENSE_CREATER = new ProblemCreater(Type.DENSE);
    private final static ProblemCreater SPARSE_CREATER = new ProblemCreater(Type.SPARSE);
    private final static List<Pair<Problem, Result>> results = new ArrayList<>();
    private final static Class[] problemClasses = new Class[] {IHCProblem.class, ESProblem.class, GAProblem.class, HGAProblem.class};
    private final static Map<Integer, Map<Type, Map<Long, boolean[][]>>> links = new HashMap<>();
    private final static Map<Integer, Map<Type, PrintWriter>> writers = new HashMap<>();

    static {
        for (int n : N) {
            links.put(n, new HashMap<>());
            writers.put(n, new HashMap<>());
            for (Type type : Type.values()) {
                links.get(n).put(type, new HashMap<>());
                for (long seed : SEED) {
                    boolean[][] link = new ProblemCreater(type).createProblem(n, new Random(seed));
                    links.get(n).get(type).put(seed, link);
                }
                try {
                    writers.get(n).put(type, new PrintWriter(n + "-" + type.toString() + ".csv"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) throws FileNotFoundException {
        for (long seed : SEED) {
            ihc(60, 13500, 3, Type.DENSE, seed);
            ihc(90, 10800, 5, Type.DENSE, seed);
            ihc(120, 13500, 7, Type.DENSE, seed);
            ihc(150, 16200, 10, Type.DENSE, seed);

            ihc(60, 18900, 5, Type.SPARSE, seed);
            ihc(90, 24750, 6, Type.SPARSE, seed);
            ihc(120, 16200, 10, Type.SPARSE, seed);
            ihc(150, 13500, 15, Type.SPARSE, seed);

            es(60, 270, Type.DENSE, ESProblem.Type.PLUS, 50, 150, seed);
            es(90, 270, Type.DENSE, ESProblem.Type.PLUS, 120, 200, seed);
            es(120, 270, Type.DENSE, ESProblem.Type.PLUS, 100, 350, seed);
            es(150, 270, Type.DENSE, ESProblem.Type.PLUS, 200, 600, seed);

            es(60, 270, Type.SPARSE, ESProblem.Type.CONMA, 50, 350, seed);
            es(90, 270, Type.SPARSE, ESProblem.Type.CONMA, 120, 550, seed);
            es(120, 270, Type.SPARSE, ESProblem.Type.CONMA, 100, 600, seed);
            es(150, 270, Type.SPARSE, ESProblem.Type.CONMA, 200, 750, seed);

            ga(seed, 60, Type.DENSE, 150, 270, 0.05);
            ga(seed, 90, Type.DENSE, 200, 270, 0.00);
            ga(seed, 120, Type.DENSE, 350, 270, 0.01);
            ga(seed, 150, Type.DENSE, 600, 270, 0.02);

            ga(seed, 60, Type.SPARSE, 350, 270, 0.04);
            ga(seed, 90, Type.SPARSE, 550, 270, 0.00);
            ga(seed, 120, Type.SPARSE, 600, 270, 0.001);
            ga(seed, 150, Type.SPARSE, 750, 270, 0.03);

            hga(seed, 60, Type.DENSE, 150, 150, 0.05, 5, 24);
            hga(seed, 90, Type.DENSE, 200, 200, 0.00, 5, 14);
            hga(seed, 120, Type.DENSE, 310, 210, 0.01, 7, 20);
            hga(seed, 150, Type.DENSE, 600, 200, 0.02, 5, 42);

            hga(seed, 60, Type.SPARSE, 310, 210, 0.04, 7, 20);
            hga(seed, 90, Type.SPARSE, 510, 220, 0.00, 11, 15);
            hga(seed, 120, Type.SPARSE, 600, 200, 0.001, 10, 21);
            hga(seed, 150, Type.SPARSE, 650, 250, 0.03, 8, 20);
        }

        output();
        IO.p("fin");
    }

    private static void ihc(int n, int loop, int restart, Type type, long seed) {
        Random rnd = new Random(seed);
        Color[] solution = SolutionCreater.createSolution(n, rnd);
        IHCProblem problem = new IHCProblem(links.get(n).get(type).get(seed), n, loop, type, restart);
        IHCSolver ihcSolver = new IHCSolver(rnd);
        IHCResult result = ihcSolver.solve(solution, problem);
        results.add(new Pair<>(problem, result));
    }

    private static void es(int n, int maxGen, Type type, ESProblem.Type selectionType, int mu, int lamda, long seed) {
        Random rnd = new Random(seed);
        ESProblem problem = new ESProblem(links.get(n).get(type).get(seed), n, maxGen, type, selectionType, mu, lamda);
        Generation[] solution = createFirstGeneration(rnd, problem);
        ESSolver solver = new ESSolver(rnd);
        ESResult result = solver.solve(solution, problem);
        results.add(new Pair<>(problem, result));
    }

    private static void ga(long seed, int n, Type type, int size, int maxGen, double variationThreshold) {
        Random rnd = new Random(seed);
        GAProblem problem = new GAProblem(seed, links.get(n).get(type).get(seed), n, type, size, maxGen, variationThreshold,
                new LinearScaler(), new UniformCrossor(rnd), new RouletteSelector(rnd));
        com.sandbox.ga.Generation[] solution = createFirstGeneration(rnd, problem);
        GASolver solver = new GASolver(rnd);
        GAResult result = solver.solve(solution, problem);
        results.add(new Pair<>(problem, result));
    }

    private static void hga(long seed, int n, Type type, int size, int maxGen, double variationThreshold, int loop, int Ne) {
        Random rnd = new Random(seed);
        HGAProblem problem = new HGAProblem(seed, links.get(n).get(type).get(seed), n, type, size, maxGen, variationThreshold,
                new LinearScaler(), new UniformCrossor(rnd), new RouletteSelector(rnd));
        com.sandbox.ga.Generation[] solution = createFirstGeneration(rnd, problem);
        HGASolver solver = new HGASolver(rnd, Ne, loop);
        GAResult result = solver.solve(solution, problem);
        results.add(new Pair<>(problem, result));
    }

    protected static Generation[] createFirstGeneration(Random rnd, ESProblem problem) {
        Generation[] parent = new Generation[problem.mu];
        for (int i = 0; i < parent.length; i++) {
            Color[] solution = SolutionCreater.createSolution(problem.n, rnd);
            parent[i] = new Generation(solution);
            parent[i].calculatePoint(problem.link);
        }
        return parent;
    }

    protected static com.sandbox.ga.Generation[] createFirstGeneration(Random rnd, GAProblem problem) {
        com.sandbox.ga.Generation[] parent = new com.sandbox.ga.Generation[problem.size];
        for (int i = 0; i < parent.length; i++) {
            Color[] solution = SolutionCreater.createSolution(problem.n, rnd);
            parent[i] = new com.sandbox.ga.Generation(solution);
            parent[i].calculatePoint(problem.link);
        }
        return parent;
    }

    private static void output() {
        for (int n : N) {
            for (Type type : Type.values()) {
                PrintWriter writer = writers.get(n).get(type);
                for (Class problemClass : problemClasses) {
                    String name = problemClass.getSimpleName();
                    writer.print(name.substring(0, name.indexOf('P')) + ",");
                    results.stream()
                            .filter(p -> p.first.n == n)
                            .filter(p -> p.first.type == type)
                            .filter(p -> p.first.getClass().equals(problemClass))
                            .forEach(p -> writer.print(p.second.isSuccess() + ","));
                    writer.println();
                    writer.print(name.substring(0, name.indexOf('P')) + ",");
                    results.stream()
                            .filter(p -> p.first.n == n)
                            .filter(p -> p.first.type == type)
                            .filter(p -> p.first.getClass().equals(problemClass))
                            .forEach(p -> writer.print(p.second.count() + ","));
                    writer.println();
                }
                writer.close();
            }
        }
    }
}

package com.sandbox;

import com.sandbox.common.ProblemCreater;
import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.ga.Builder;
import com.sandbox.ga.GAProblem;
import com.sandbox.ga.GAResult;
import com.sandbox.ga.GASolver;
import com.sandbox.ga.crossor.UniformCrossor;
import com.sandbox.ga.scaler.LinearScaler;
import com.sandbox.ga.scaler.PowerScaler;
import com.sandbox.ga.scaler.Scaler;
import com.sandbox.ga.selector.RouletteSelector;
import com.sandbox.util.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private final static int[] N = new int[]{30, 60, 120, 150};
    private final static int GEN_MAX = 270;
    private final static long SEED = 113; // new long[]{13, 367, 647, 947, 491, 997, 839, 1733, 271, 569};
    private final static ProblemCreater DENSE_CREATER = new ProblemCreater(Type.DENSE);
    private final static ProblemCreater SPARSE_CREATER = new ProblemCreater(Type.SPARSE);
    private final static Scaler LINEAR_SCALER = new LinearScaler();
    private final static Scaler POWER_SCALER = new PowerScaler();

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Pair<GAProblem, GAResult>> problemDense = new ArrayList<>();
        ArrayList<Pair<GAProblem, GAResult>> problemSparse = new ArrayList<>();
        GASolver solver = new GASolver(null);

        for (int n : N) {
            for (int size = 50; size <= 1000; size+=50) {
                for (double threshold = 0.0; threshold <= 1.0; threshold += 0.05) {
                    Random rnd = new Random(SEED);
                    solver.setRnd(rnd);
                    boolean[][] link = DENSE_CREATER.createProblem(n, rnd);
                    Builder builder = new Builder()
                            .setSeed(SEED)
                            .setLink(link)
                            .setN(n)
                            .setType(Type.DENSE)
                            .setSize(size)
                            .setMaxGen(GEN_MAX)
                            .setVariationThreshold(threshold);

                    rnd = new Random(SEED);
                    GAProblem problem = builder
                            .setScaler(LINEAR_SCALER)
                            .setCrossor(new UniformCrossor(rnd))
                            .setSelector(new RouletteSelector(rnd))
                            .createGAProblem();
                    GAResult result = solver.solve(problem);
                    problemDense.add(new Pair<>(problem, result));

                    rnd = new Random(SEED);
                    problem = builder
                            .setScaler(POWER_SCALER)
                            .setCrossor(new UniformCrossor(rnd))
                            .setSelector(new RouletteSelector(rnd))
                            .createGAProblem();
                    result = solver.solve(problem);
                    problemDense.add(new Pair<>(problem, result));

                    rnd = new Random(SEED);
                    link = SPARSE_CREATER.createProblem(n, rnd);
                    builder.setLink(link).setType(Type.SPARSE);

                    rnd = new Random(SEED);
                    problem = builder
                            .setScaler(LINEAR_SCALER)
                            .setCrossor(new UniformCrossor(rnd))
                            .setSelector(new RouletteSelector(rnd))
                            .createGAProblem();
                    result = solver.solve(problem);
                    problemSparse.add(new Pair<>(problem, result));

                    rnd = new Random(SEED);
                    problem = builder
                            .setScaler(POWER_SCALER)
                            .setCrossor(new UniformCrossor(rnd))
                            .setSelector(new RouletteSelector(rnd))
                            .createGAProblem();
                    result = solver.solve(problem);
                    problemSparse.add(new Pair<>(problem, result));
                }
            }
        }

        PrintWriter pw = new PrintWriter("result.csv");
        String title = "max, min, ave, ";
        StringBuilder titleBuilder = new StringBuilder();
        List<StringBuilder> builderList = new ArrayList<>();
        for (int i = 0; i < GEN_MAX; i++) {
            builderList.add(new StringBuilder());
        }
        problemDense.addAll(problemSparse);
        problemDense.forEach(pair -> {
            GAProblem problem = pair.first;
            GAResult result = pair.second;

            pw.print(problem.toTitle() + ",,,");
            titleBuilder.append(title);
            for (int i = 0; i < builderList.size(); i++) {
                StringBuilder builder = builderList.get(i);
                if (result.max.size() <= i) {
                    builder.append(",,,");
                }
                else {
                    builder.append(result.max.get(i)).append(",");
                    builder.append(result.min.get(i)).append(",");
                    builder.append(result.ave.get(i)).append(",");
                }
            }
        });

        pw.println();
        pw.println(titleBuilder.toString());
        builderList.forEach(b -> {
            pw.println(b.toString());
        });
        pw.close();
    }
}

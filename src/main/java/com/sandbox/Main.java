package com.sandbox;

import com.sandbox.common.ProblemCreater;
import com.sandbox.common.ProblemCreater.Type;
import com.sandbox.es.ESProblem;
import com.sandbox.es.ESSolver;
import com.sandbox.util.IO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private final static int[] N = new int[]{30, 60, 90, 120, 150};
    private final static int[] MU = new int[]{180, 360, 720};
    private final static int[] LAMDA = new int[]{360, 720, 1440};
    private final static int GEN_MAX = 270;
    private final static long SEED = 113;//new long[]{113, 367, 647, 947, 491};

    private final static ProblemCreater DENSE_CREATER = new ProblemCreater(Type.DENSE);
    private final static ProblemCreater SPARSE_CREATER = new ProblemCreater(Type.SPARSE);


    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<ESProblem> problemDense = new ArrayList<>();
        ArrayList<ESProblem> problemSparse = new ArrayList<>();
        ESSolver solver = new ESSolver(null);

        for (ESProblem.Type type : ESProblem.Type.values()) {
            for (int n : N) {
                for (int i = 0; i < MU.length; i++) {
                    int mu = MU[i];
                    int lamda = LAMDA[i];

                    Random rnd = new Random(SEED);
                    solver.setRnd(rnd);
                    boolean[][] link = DENSE_CREATER.createProblem(n, rnd);
                    ESProblem problem = new ESProblem(link, n, GEN_MAX, Type.DENSE, type, mu, lamda);
                    problemDense.add(problem);
                    IO.p(solver.solve(problem));

                    rnd = new Random(SEED);
                    solver.setRnd(rnd);
                    link = SPARSE_CREATER.createProblem(n, rnd);
                    problem = new ESProblem(link, n, GEN_MAX, Type.SPARSE, type, mu, lamda);
                    problemSparse.add(problem);
                    IO.p(solver.solve(problem));
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
        problemDense.forEach(r -> {
            pw.print(r.toTitle() + ",,,");
            titleBuilder.append(title);
            for (int i = 0; i < builderList.size(); i++) {
                StringBuilder builder = builderList.get(i);
                if (r.max.size() <= i) {
                    builder.append(",,,");
                }
                else {
                    builder.append(r.max.get(i)).append(",");
                    builder.append(r.min.get(i)).append(",");
                    builder.append(r.ave.get(i)).append(",");
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

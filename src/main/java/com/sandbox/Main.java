package com.sandbox;

import com.sandbox.ProblemCreater.Type;
import com.sandbox.SolutionCreater.Color;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private final static int[] N = new int[]{30, 60, 90, 120, 150};
    private final static long SEED = 367;//new long[]{113, 367, 647, 947, 491};
    private final static int RESTART = 5;
    private final static int[] LOOP_MAX = new int[]{100, 300, 500};

    private static Random RND;

    private final static ProblemCreater DENSE_CREATER = new ProblemCreater(Type.DENSE);
    private final static ProblemCreater SPARSE_CREATER = new ProblemCreater(Type.SPARSE);


    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Problem> problemDense = new ArrayList<>();
        ArrayList<Problem> problemSparse = new ArrayList<>();

        for (int n : N) {
            for (int loop : LOOP_MAX) {
                RND = new Random(SEED);

                boolean[][] link = DENSE_CREATER.createProblem(n, RND);
                Problem problem = new Problem(link, n, loop, Type.DENSE);
                problemDense.add(problem);

                int restartCount = 0;
                while (!solve(problem, loop) && restartCount++ < RESTART) {}

                RND = new Random(SEED);
                restartCount = 0;
                link = SPARSE_CREATER.createProblem(n, RND);
                problem = new Problem(link, n, loop, Type.SPARSE);
                problemSparse.add(problem);
                while (!solve(problem, loop) && restartCount++ < RESTART) {}
//                }
            }
        }

        PrintWriter pw = new PrintWriter("result.csv");
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < 500 * 6; i++) { strs.add(""); }

        problemDense.addAll(problemSparse);
        problemDense.forEach(r -> {
            pw.print(r.toTitle() + ",");
            for (int i = 0; i < 500 * 6; i++) {
                String s = r.violationPoints.size() > i ? String.valueOf(r.violationPoints.get(i)) : "";
                strs.set(i, strs.get(i) + s + ",");
            }
        });

        pw.println();
        strs.forEach(pw::println);
        pw.close();
    }

    private static boolean solve(Problem problem, int maxLoop) {
        Color[] solution = SolutionCreater.createSolution(problem.link.length, RND);

        int violationCount = countViolation(problem.link, solution);
        int loopCount = 0;
        while(violationCount > 1 && loopCount++ <  maxLoop) {
            int index = choiceViolentNode(problem.link, solution);
            switchColor(problem.link, solution, violationCount, index);
            violationCount = countViolation(problem.link, solution);
            problem.violationPoints.add(violationCount);
        }

        return violationCount == 0;
    }

    @SuppressWarnings("unchecked")
    private static void switchColor(boolean[][] problem, Color[] solution, int violationCount, int index) {
        Color color = solution[index];
        Pair<Color, Integer>[] counts = new Pair[Color.values().length];
        for (int i = 0; i < Color.values().length; i++) {
            Color c = Color.values()[i];
            if (color == c) {
                counts[i] = new Pair<>(c, violationCount);
                continue;
            }
            solution[index] = c;
            counts[i] = new Pair<>(c, countViolation(problem, solution));
        }
        Arrays.sort(counts, (o1, o2) -> o1.second - o2.second);

        int bestScore = counts[0].second;
        List<Color> bestColor = new ArrayList<>();
        for (Pair<Color, Integer> pair : counts) {
            if (bestScore == pair.second) {
                bestColor.add(pair.first);
            }
        }

        solution[index] = bestColor.get(RND.nextInt(bestColor.size()));
    }

    private static int choiceViolentNode(boolean[][] link, Color[] solution) {
        List<Integer> violations = new ArrayList<>();
        for(int i = 0; i < link.length; i++) {
            for (int j = i+1; j < link[i].length; j++) {
                if (link[i][j] && solution[i] == solution[j]) {
                    violations.add(i);
                    break;
                }
            }
        }
        return violations.get(RND.nextInt(violations.size()));
    }

    private static int countViolation(boolean[][] link, Color[] solution) {
        int count = 0;
        for(int i = 0; i < link.length; i++) {
            for (int j = i+1; j < link[i].length; j++) {
                if (link[i][j] && solution[i] == solution[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static class Problem {
        public final boolean[][] link;
        public final int n;
        public final int loop;
        public final Type type;
        public final List<Integer> violationPoints;

        private Problem(boolean[][] link, int n, int loop, Type type) {
            this.link = link;
            this.n = n;
            this.loop = loop;
            this.type = type;
            this.violationPoints = new ArrayList<>();
        }

        public String toTitle() {
           return String.format("ノード数: %d 世代数: %d 問題: %s", n, loop, type.getJPName());
        }
    }
}

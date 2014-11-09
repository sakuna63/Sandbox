package com.sandbox.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProblemCreater {

    private final Type type;

    public ProblemCreater(Type type) {
        this.type = type;
    }

    public boolean[][] createProblem(int n, Random rnd) {
        int linkNum = type.calculateLinkNum(n);
        boolean[][] links = new boolean[n][n];

        Boolean[] tempLinks = new Boolean[n * n / 3];
        for (int i = 0; i < tempLinks.length; i++) {
            tempLinks[i] = linkNum > i;
        }

        List<Boolean> tempList = Arrays.asList(tempLinks);
        Collections.shuffle(tempList, rnd);
        Boolean[] tempLinks2 = tempList.toArray(new Boolean[tempList.size()]);

        int index = 0;
        for (int i = 0; i < n/3; i++) {
            for (int j = n/3; j < n; j++) {
                links[i][j] = tempLinks2[index];
                index++;
            }
        }
        for (int i = n/3; i < n/3*2; i++) {
            for (int j = n/3*2; j < n; j++) {
                links[i][j] = tempLinks2[index];
                index++;
            }
        }

//        IO.p("link num = " + linkNum);

        return links;
    }

    public enum Type {
        DENSE("密結合") {
            @Override
            public int calculateLinkNum(int n) {
                return n * (n - 1) / 4;
            }
        },
        SPARSE("疎結合") {
            @Override
            public int calculateLinkNum(int n) {
                return 3 * n;
            }
        };

        private final String jpName;

        Type(String jpName) {
            this.jpName = jpName;
        }


        public abstract int calculateLinkNum(int n);

        public String getJPName() {
            return jpName;
        }
    }

}

package com.sandbox.ihc;

import com.sandbox.common.Result;

import java.util.ArrayList;
import java.util.List;

public class IHCResult extends Result {
    public List<Integer> loops = new ArrayList<>();


    @Override
    public int count() {
        return loops.stream().mapToInt(i->i).sum();
    }
}

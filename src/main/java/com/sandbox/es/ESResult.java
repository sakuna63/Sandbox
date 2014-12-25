package com.sandbox.es;

import com.sandbox.common.Result;

public class ESResult extends Result{
    public int count = 0;

    @Override
    public int count() {
        return count;
    }
}

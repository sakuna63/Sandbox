package com.sandbox.common;

public abstract class Result {
    private boolean isSuccess;

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public abstract int count();
}

package com.adj.xiaotuanti.pojo.constants;

public enum ActivityStatus {
    PLANNING(0),
    RUNNING(1),
    END(2);
    private int status;

    ActivityStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

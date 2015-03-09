package com.nomad.internethaber.model;

import com.nomad.internethaber.constant.ApplicationConstants;

public final class Interval {
    private static int mFrom;
    private static int mTo;


    public Interval calculate(int page) {
        mFrom = page * ApplicationConstants.PAGE_SIZE;
        mTo = (page + 1) * ApplicationConstants.PAGE_SIZE;
        return this;
    }

    public String getFrom() {
        return mFrom + "";
    }

    public String getTo() {
        return mTo + "";
    }

}

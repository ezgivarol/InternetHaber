package com.nomad.internethaber.model;

import com.nomad.internethaber.constant.ApplicationConstants;

public final class Range {
    private static int mPageCount;
    private int mFrom;
    private int mTo;

    public Range() {
        mPageCount = 0;
        calculate();
    }

    private void calculate() {
        mFrom = mPageCount * ApplicationConstants.PAGE_SIZE;
        mTo = (mPageCount + 1) * ApplicationConstants.PAGE_SIZE;
    }

    public Range nextPage() {
        mPageCount++;
        calculate();

        return this;
    }

    public Range resetPage() {
        mPageCount = 0;
        calculate();

        return this;
    }

    public static int getPage() {
        return mPageCount;
    }

    public static int turnPage() {
        mPageCount++;
        return mPageCount;
    }

    public String getFrom() {
        return mFrom + "";
    }

    public String getTo() {
        return mTo + "";
    }

}

package com.nomad.internethaber.model;

import com.nomad.internethaber.constant.ApplicationConstants;

public final class Interval {
    private static int mFrom;
    private static int mTo;

    public Interval getNextPager(int position) {
        int pageCount = position % ApplicationConstants.PAGE_SIZE;
        pageCount++;

        mFrom = pageCount * ApplicationConstants.PAGE_SIZE;
        mTo = (pageCount + 1) * ApplicationConstants.PAGE_SIZE;
        return this;
    }

    public Interval getPreviousPager(int position) {
        int pageCount = position % ApplicationConstants.PAGE_SIZE;
        pageCount--;

        mFrom = pageCount * ApplicationConstants.PAGE_SIZE;
        mTo = (pageCount + 1) * ApplicationConstants.PAGE_SIZE;
        return this;
    }

    public int getFrom() {
        return mFrom;
    }

    public int getTo() {
        return mTo;
    }

}

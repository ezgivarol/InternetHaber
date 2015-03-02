package com.nomad.internethaber.helper;

import com.nomad.internethaber.constant.ApplicationConstants;

public class PaginationHelper {
    private int mPageCount;
    private Range mRange;

    public PaginationHelper() {
        mPageCount = 0;

        mRange = new Range();
        mRange.calculate();
    }

    public Range nextPage() {
        mPageCount++;
        mRange.calculate();

        return mRange;
    }

    public Range getRange() {
        return mRange;
    }

    
    public final class Range {
        private int mFrom;
        private int mTo;

        private void calculate() {
            mFrom = mPageCount * ApplicationConstants.PAGE_SIZE;
            mTo = (mPageCount + 1) * ApplicationConstants.PAGE_SIZE;
        }

        public int getFrom() {
            return mFrom;
        }

        public int getTo() {
            return mTo;
        }
    }

}

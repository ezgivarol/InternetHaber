package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.VideosResponseBean;
import com.nomad.internethaber.constant.ApplicationConstants;
import com.nomad.internethaber.event.VideosMoreFailureResponseEvent;
import com.nomad.internethaber.event.VideosMoreNoItemResponseEvent;
import com.nomad.internethaber.event.VideosMoreRequestEvent;
import com.nomad.internethaber.event.VideosMoreSuccessResponseEvent;
import com.nomad.internethaber.interfaces.VideosRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;


public final class VideosMoreAsyncTask extends AsyncTask<Void, Void, VideosMoreAsyncTask.State> {

    private VideosResponseBean mBean;
    private String mCategoryId;
    private String mFrom;
    private String mTo;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        VideosMoreRequestEvent event = new VideosMoreRequestEvent();
        BusProvider.getInstance().post(event);
    }

    @Override
    protected State doInBackground(Void... params) {
        try {
            VideosRestInterface videosRestInterface = RestAdapterProvider.getInstance().create(VideosRestInterface.class);
            mBean = videosRestInterface.get(mCategoryId, mFrom, mTo);

            int size = mBean.getVideos().size();
            if (size < ApplicationConstants.PAGE_SIZE)
                return State.NO_ITEM;

            return State.SUCCESS;
        } catch (Exception e) {
            Logger.e(e);
        }

        return State.FAILURE;
    }

    @Override
    protected void onPostExecute(State result) {
        super.onPostExecute(result);

        switch (result) {
            case SUCCESS:
                VideosMoreSuccessResponseEvent successEvent = new VideosMoreSuccessResponseEvent();
                successEvent.setBean(mBean);
                BusProvider.getInstance().post(successEvent);
                return;
            case NO_ITEM:
                VideosMoreNoItemResponseEvent noItemEvent = new VideosMoreNoItemResponseEvent();
                BusProvider.getInstance().post(noItemEvent);
                return;
            case FAILURE:
                VideosMoreFailureResponseEvent failureResponseEvent = new VideosMoreFailureResponseEvent();
                BusProvider.getInstance().post(failureResponseEvent);
                return;
        }
    }

    public void setCategoryId(String id) {
        mCategoryId = id;
    }

    public void setFrom(String from) {
        mFrom = from;
    }

    public void setTo(String to) {
        mTo = to;
    }

    protected enum State {
        SUCCESS, FAILURE, NO_ITEM;
    }
}
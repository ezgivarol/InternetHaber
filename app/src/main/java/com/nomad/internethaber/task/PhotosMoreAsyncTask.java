package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.PhotosResponseBean;
import com.nomad.internethaber.constant.ApplicationConstants;
import com.nomad.internethaber.event.NewsMoreRequestEvent;
import com.nomad.internethaber.event.PhotosMoreFailureResponseEvent;
import com.nomad.internethaber.event.PhotosMoreNoItemResponseEvent;
import com.nomad.internethaber.event.PhotosMoreSuccessResponseEvent;
import com.nomad.internethaber.interfaces.PhotosRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;


public final class PhotosMoreAsyncTask extends AsyncTask<Void, Void, PhotosMoreAsyncTask.State> {

    private PhotosResponseBean mBean;
    private String mCategoryId;
    private String mFrom;
    private String mTo;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        NewsMoreRequestEvent event = new NewsMoreRequestEvent();
        BusProvider.getInstance().post(event);
    }

    @Override
    protected State doInBackground(Void... params) {
        try {
            PhotosRestInterface photosRestInterface = RestAdapterProvider.getInstance().create(PhotosRestInterface.class);
            mBean = photosRestInterface.get(mCategoryId, mFrom, mTo);

            int size = mBean.getPhotos().size();
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
                PhotosMoreSuccessResponseEvent successEvent = new PhotosMoreSuccessResponseEvent();
                successEvent.setBean(mBean);
                BusProvider.getInstance().post(successEvent);
                return;
            case NO_ITEM:
                PhotosMoreNoItemResponseEvent noItemEvent = new PhotosMoreNoItemResponseEvent();
                BusProvider.getInstance().post(noItemEvent);
                return;
            case FAILURE:
                PhotosMoreFailureResponseEvent failureResponseEvent = new PhotosMoreFailureResponseEvent();
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
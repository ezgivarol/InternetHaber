package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.PhotosResponseBean;
import com.nomad.internethaber.event.NewsRequestEvent;
import com.nomad.internethaber.event.PhotosFailureResponseEvent;
import com.nomad.internethaber.event.PhotosSuccessResponseEvent;
import com.nomad.internethaber.interfaces.PhotosRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;

public final class VideosAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private PhotosResponseBean mBean;
    private String mCategoryId;
    private String mFrom;
    private String mTo;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        NewsRequestEvent event = new NewsRequestEvent();
        BusProvider.getInstance().post(event);
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            PhotosRestInterface photosRestInterface = RestAdapterProvider.getInstance().create(PhotosRestInterface.class);
            mBean = photosRestInterface.get(mCategoryId, mFrom, mTo);
            return true;
        } catch (Exception e) {
            Logger.e(e);
        }

        return false;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            PhotosSuccessResponseEvent successEvent = new PhotosSuccessResponseEvent();
            successEvent.setBean(mBean);
            BusProvider.getInstance().post(successEvent);
        } else {
            PhotosFailureResponseEvent failureResponseEvent = new PhotosFailureResponseEvent();
            BusProvider.getInstance().post(failureResponseEvent);
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
}
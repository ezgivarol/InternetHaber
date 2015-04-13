package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.VideosResponseBean;
import com.nomad.internethaber.event.VideosFailureResponseEvent;
import com.nomad.internethaber.event.VideosRequestEvent;
import com.nomad.internethaber.event.VideosSuccessResponseEvent;
import com.nomad.internethaber.interfaces.VideosRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;

public final class VideosAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private VideosResponseBean mBean;
    private String mCategoryId;
    private String mFrom;
    private String mTo;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        VideosRequestEvent event = new VideosRequestEvent();
        BusProvider.getInstance().post(event);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            VideosRestInterface videosRestInterface = RestAdapterProvider.getInstance().create(VideosRestInterface.class);
            mBean = videosRestInterface.get(mCategoryId, mFrom, mTo);
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
            VideosSuccessResponseEvent successEvent = new VideosSuccessResponseEvent();
            successEvent.setBean(mBean);
            BusProvider.getInstance().post(successEvent);
        } else {
            VideosFailureResponseEvent failureResponseEvent = new VideosFailureResponseEvent();
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
package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsRequestEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.interfaces.NewsRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;

public final class NewsAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private NewsResponseBean mBean;
    private Exception mException;

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
            NewsRestInterface newsRestInterface = RestAdapterProvider.getInstance().create(NewsRestInterface.class);
            mBean = newsRestInterface.get(mCategoryId, mFrom, mTo);
            return true;
        } catch (Exception e) {
            mException = e;
            Logger.e(e);
        }

        return false;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            NewsSuccessResponseEvent successEvent = new NewsSuccessResponseEvent();
            successEvent.setBean(mBean);
            BusProvider.getInstance().post(successEvent);
        } else {
            NewsFailureResponseEvent failureResponseEvent = new NewsFailureResponseEvent();
            failureResponseEvent.setException(mException);
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
package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.interfaces.NewsRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;

import timber.log.Timber;

public class NewsAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private NewsResponseBean mBean;
    private String mCategoryId;
    private String mFrom;
    private String mTo;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        NewsResponseBean event = new NewsResponseBean();
        BusProvider.getInstance().post(event);
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            NewsRestInterface newsRestInterface = RestAdapterProvider.getInstance().create(NewsRestInterface.class);
            mBean = newsRestInterface.get(mCategoryId, mFrom, mTo);
            return true;
        } catch (Exception e) {
            Timber.e("Categories could not get.");
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
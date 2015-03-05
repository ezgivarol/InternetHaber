package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsMoreFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreRequestEvent;
import com.nomad.internethaber.event.NewsMoreSuccessResponseEvent;
import com.nomad.internethaber.interfaces.NewsRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;

import timber.log.Timber;

public final class NewsMoreAsyncTask extends AsyncTask<Void, Void, NewsMoreAsyncTask.State> {

    protected enum State {
        SUCCESS, FAILURE, NO_ITEM;
    }

    private NewsResponseBean mBean;
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
            NewsRestInterface newsRestInterface = RestAdapterProvider.getInstance().create(NewsRestInterface.class);
            mBean = newsRestInterface.get(mCategoryId, mFrom, mTo);

            int size = mBean.getNews().size();
            if (size < 20)
                return State.NO_ITEM;

            return State.SUCCESS;
        } catch (Exception e) {
            Timber.e("Categories could not get.");
        }

        return State.FAILURE;
    }


    @Override
    protected void onPostExecute(State result) {
        super.onPostExecute(result);

        switch (result) {
            case SUCCESS:
                NewsMoreSuccessResponseEvent successEvent = new NewsMoreSuccessResponseEvent();
                successEvent.setBean(mBean);
                BusProvider.getInstance().post(successEvent);
                return;
            case NO_ITEM:
                NewsMoreSuccessResponseEvent noItemEvent = new NewsMoreSuccessResponseEvent();
                noItemEvent.setBean(mBean);
                BusProvider.getInstance().post(noItemEvent);
                return;
            case FAILURE:
                NewsMoreFailureResponseEvent failureResponseEvent = new NewsMoreFailureResponseEvent();
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
}
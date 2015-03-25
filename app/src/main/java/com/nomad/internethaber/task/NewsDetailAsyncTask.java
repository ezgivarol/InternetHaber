package com.nomad.internethaber.task;

import android.os.AsyncTask;

import com.nomad.internethaber.bean.NewsDetailResponseBean;
import com.nomad.internethaber.event.NewsDetailFailureResponseEvent;
import com.nomad.internethaber.event.NewsDetailRequestEvent;
import com.nomad.internethaber.event.NewsDetailSuccessResponseEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.interfaces.NewsDetailRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;

public final class NewsDetailAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private NewsDetailResponseBean mBean;
    private String mNewsId;
    private String mNewsType;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        NewsDetailRequestEvent event = new NewsDetailRequestEvent();
        BusProvider.getInstance().post(event);
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            NewsDetailRestInterface newsDetailRestInterface = RestAdapterProvider.getInstance().create(NewsDetailRestInterface.class);
            mBean = newsDetailRestInterface.get(mNewsId, mNewsType);
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
            NewsDetailSuccessResponseEvent successEvent = new NewsDetailSuccessResponseEvent();
            successEvent.setBean(mBean);
            BusProvider.getInstance().post(successEvent);
        } else {
            NewsDetailFailureResponseEvent failureResponseEvent = new NewsDetailFailureResponseEvent();
            BusProvider.getInstance().post(failureResponseEvent);
        }
    }

    public String getNewsId() {
        return mNewsId;
    }

    public void setNewsId(String newsId) {
        mNewsId = newsId;
    }

    public String getNewsType() {
        return mNewsType;
    }

    public void setNewsType(String newsType) {
        mNewsType = newsType;
    }
}
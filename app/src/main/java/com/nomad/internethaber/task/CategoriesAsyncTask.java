package com.nomad.internethaber.task;

import android.os.AsyncTask;
import android.util.Log;

import com.nomad.internethaber.bean.CategoryResponseBean;
import com.nomad.internethaber.event.CategoriesFailureResponseEvent;
import com.nomad.internethaber.event.CategoriesRequestEvent;
import com.nomad.internethaber.event.CategoriesSuccessResponseEvent;
import com.nomad.internethaber.interfaces.CategoryRestInterface;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.provider.RestAdapterProvider;

public final class CategoriesAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private CategoryResponseBean mBean;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        CategoriesRequestEvent event = new CategoriesRequestEvent();
        BusProvider.getInstance().post(event);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            CategoryRestInterface categoryRestInterface = RestAdapterProvider.getInstance().create(CategoryRestInterface.class);
            mBean = categoryRestInterface.get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA", e.getMessage());
        }

        return false;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {
            CategoriesSuccessResponseEvent successEvent = new CategoriesSuccessResponseEvent();
            successEvent.setBean(mBean);
            BusProvider.getInstance().post(successEvent);
        } else {
            CategoriesFailureResponseEvent failureResponseEvent = new CategoriesFailureResponseEvent();
            BusProvider.getInstance().post(failureResponseEvent);
        }
    }
}
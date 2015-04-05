package com.nomad.internethaber.handler;

import com.orhanobut.logger.Logger;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.ui.SASAdView;

public final class LoggerAdResponseHandler implements SASAdView.AdResponseHandler {

    @Override
    public void adLoadingCompleted(SASAdElement sasAdElement) {
        String message = "SmartAd is loaded.";
        Logger.v(message);
    }

    @Override
    public void adLoadingFailed(Exception exception) {
        Logger.e(exception);
    }
}

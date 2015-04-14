package com.nomad.internethaber.application;

import android.support.multidex.MultiDex;

import com.nomad.internethaber.helper.ResponsiveController;
import com.orhanobut.logger.Logger;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.SaveCallback;

public final class InternetHaberApplication extends BaseApplication implements SaveCallback {
    private static ResponsiveController mResponsiveController;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        Parse.initialize(this, "JAuXIRsmTMHRfNUFIsaJSDBvwcqJwhXc5b0uTtTH", "hUwnXGB4B7ngkBRjUBVyAxnE3Xrz4NueT0IJtiSJ");

        mResponsiveController = new ResponsiveController(getResources());
    }

    public static ResponsiveController getResponsiveController() {
        return mResponsiveController;
    }

    @Override
    public void done(ParseException e) {
        if (e == null){
            Logger.v("Subscribed to push.");
            return;
        }

        Logger.e(e);
    }
}

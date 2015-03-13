package com.nomad.internethaber.application;

import com.nomad.internethaber.helper.ResponsiveController;

public final class InternetHaberApplication extends BaseApplication {
    private static ResponsiveController mResponsiveControler;

    @Override
    public void onCreate() {
        super.onCreate();

        mResponsiveControler = new ResponsiveController(getResources());

//        PushRestInterface pushRestInterface = RestAdapterProvider.getInstance().create(PushRestInterface.class);
//        pushRestInterface.get();
    }

    public static ResponsiveController getResponsiveController() {
        return mResponsiveControler;
    }
}

package com.nomad.internethaber.application;

import com.nomad.internethaber.helper.ResponsiveControler;
import com.nomad.internethaber.interfaces.PushRestInterface;
import com.nomad.internethaber.provider.RestAdapterProvider;

public final class InternetHaberApplication extends BaseApplication {
    private static ResponsiveControler mResponsiveControler;

    @Override
    public void onCreate() {
        super.onCreate();

        mResponsiveControler = new ResponsiveControler(getResources());

//        PushRestInterface pushRestInterface = RestAdapterProvider.getInstance().create(PushRestInterface.class);
//        pushRestInterface.get();
    }

    public static ResponsiveControler getResponsiveControler() {
        return mResponsiveControler;
    }
}

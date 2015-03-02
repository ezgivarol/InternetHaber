package com.nomad.internethaber.application;

import com.nomad.internethaber.interfaces.PushRestInterface;
import com.nomad.internethaber.provider.RestAdapterProvider;

public final class InternetHaberApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

//        PushRestInterface pushRestInterface = RestAdapterProvider.getInstance().create(PushRestInterface.class);
//        pushRestInterface.get();
    }

}

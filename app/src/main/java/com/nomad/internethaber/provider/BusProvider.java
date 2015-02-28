package com.nomad.internethaber.provider;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider {
    private static Bus sBus;

    public static Bus getInstance() {
        if (sBus == null)
            sBus = new Bus(ThreadEnforcer.ANY);

        return sBus;
    }

}
package com.nomad.internethaber.util;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

public final class ThreadUtils {

    public static void kill(@Nullable AsyncTask task) {
        if (task == null)
            return;

        task.cancel(true);
    }

}

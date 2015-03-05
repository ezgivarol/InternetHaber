package com.nomad.internethaber.util;

import android.os.AsyncTask;

public final class ThreadUtils {

    public static void kill(AsyncTask task) {
        if (task == null)
            return;
        
        task.cancel(true);
    }

}

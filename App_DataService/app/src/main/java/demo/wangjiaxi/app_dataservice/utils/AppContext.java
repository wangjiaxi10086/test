package demo.wangjiaxi.app_dataservice.utils;

import android.content.Context;

/**
 * Created by wangjiaxi on 17-9-20.
 */

public class AppContext {
    private static Context appContext = null;

    public static Context getContext() {
        return appContext;
    }

    public static void setContext(Context context) {
        appContext = context;
    }
}
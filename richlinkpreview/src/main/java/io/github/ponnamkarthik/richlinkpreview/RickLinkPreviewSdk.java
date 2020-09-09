package io.github.ponnamkarthik.richlinkpreview;

import android.content.Context;

public class RickLinkPreviewSdk {
    private Context appContext;
    private static final RickLinkPreviewSdk ourInstance = new RickLinkPreviewSdk();

    public static void init(Context context) {
        if (get() == null) {
            getInstance().setContext(context.getApplicationContext());
        }
    }

    private Context getContext() {
        return appContext;
    }

    private void setContext(Context applicationContext) {
        this.appContext = applicationContext;
    }

    public static Context get() {
        return getInstance().getContext();
    }

    public static synchronized RickLinkPreviewSdk getInstance() {
        return ourInstance;
    }

    private RickLinkPreviewSdk() {

    }
}

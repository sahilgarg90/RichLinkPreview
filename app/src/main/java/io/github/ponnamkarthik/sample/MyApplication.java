package io.github.ponnamkarthik.sample;

import android.app.Application;

import io.github.ponnamkarthik.richlinkpreview.RickLinkPreviewSdk;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RickLinkPreviewSdk.init(getApplicationContext());
    }
}

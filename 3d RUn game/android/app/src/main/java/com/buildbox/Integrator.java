package com.buildbox;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public interface Integrator
{
    void setUserConsent(boolean consentGiven);

    void onActivityCreated(Activity activity);

    void onActivityStarted(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityStopped(Activity activity);

    void onActivityDestroyed(Activity activity);
}

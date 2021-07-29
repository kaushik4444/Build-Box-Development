package com.buildbox.analytics;

import android.app.Activity;

import com.buildbox.Integrator;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public interface AnalyticsIntegratorInterface extends Integrator {
    void initSdk(HashMap<String, String> initValues, WeakReference<Activity> activity);

    void logEvent(String eventId);

    void setUserConsent(boolean consentGiven);

    void sdkLoaded();

    void sdkFailed();

    void onActivityCreated(Activity activity);

    void onActivityStarted(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityStopped(Activity activity);

    void onActivityDestroyed(Activity activity);
}

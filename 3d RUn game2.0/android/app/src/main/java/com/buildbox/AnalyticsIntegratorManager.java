package com.buildbox;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.buildbox.analytics.AnalyticsIntegratorInterface;
import com.buildbox.consent.ConsentHelper;
import com.buildbox.consent.SdkConsentInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.preference.PreferenceManager;

public class AnalyticsIntegratorManager {

    private static HashMap<String, AnalyticsIntegratorInterface> integrators = new HashMap<>();

    private static WeakReference<Activity> activity;
    private static String TAG = "AnalyticsIntegratorManager";

    public static void initBridge(Activity act) {
        activity = new WeakReference<>(act);
        /* facebook-analytics */ /* integrators.put("facebook-analytics", new com.buildbox.analytics.facebook.AnalyticsIntegrator()); */ /* facebook-analytics */
        /* custom-analytics */ /* integrators.put("custom-analytics", new com.buildbox.analytics.custom.AnalyticsIntegrator()); */ /* custom-analytics */
    }

    public static void initSdk(String sdkId, final HashMap<String, String> initValues) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.get());
        final boolean userConsent = sharedPreferences.getBoolean(ConsentHelper.getConsentKey(sdkId), false);
        Log.d(TAG, "analytics initSdk hit with SDK " + sdkId);
        final AnalyticsIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing analytics for " + sdkId);
            Log.d(TAG, "setting user consent: " + userConsent);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setUserConsent(userConsent);
                    integrator.initSdk(initValues, activity);
                }
            });
        } else {
            Log.e(TAG, "SDK " + sdkId + " not found in map");
            Log.d(TAG, "Failing SDK " + sdkId);
            sdkFailed(sdkId);
        }
    }

    public static void logEvent(String sdkId, final String eventId) {
        Log.d(TAG, "logEvent");
        final AnalyticsIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "log event for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.logEvent(eventId);
                }
            });
        } else {
            Log.e(TAG, "SDK " + sdkId + " not found in map");
        }
    }

    public static void setUserConsent(String sdkId, final boolean consentGiven) {
        Log.d(TAG, "setuserconsent");
        final AnalyticsIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setUserConsent(consentGiven);
                }
            });
        } else {
            Log.e(TAG, "SDK " + sdkId + " not found in map");
        }
    }

    public static void revokeAllConsent() {
        Log.d(TAG, "revokeAllConsent");
        activity.get().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(activity.get());
                for (SdkConsentInfo sdkConsentInfo : ConsentHelper.getSdkConsentInfos()) {
                    Log.d(TAG, "Revoking consent for " + sdkConsentInfo.getSdkId());
                    sharedPreferences
                        .edit()
                        .putBoolean(ConsentHelper.getConsentKey(sdkConsentInfo.getSdkId()), false)
                        .commit();
                    final AnalyticsIntegratorInterface integrator = integrators.get(sdkConsentInfo.getSdkId());
                    if (integrator!=null){
                        integrator.setUserConsent(false);
                    } else {
                        Log.e(TAG, "SDK " + sdkConsentInfo.getSdkId() + " not found in map");
                    }
                }
                Toast toast = Toast.makeText(activity.get(), "Consent revoked for all analytics SDKs", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public static void onActivityCreated(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityCreated(activity);
        }
    }

    public static void onActivityStarted(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityStarted(activity);
        }
    }

    public static void onActivityResumed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityResumed(activity);
        }
    }

    public static void onActivityPaused(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityPaused(activity);
        }
    }

    public static void onActivityStopped(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityStopped(activity);
        }
    }

    public static void onActivityDestroyed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AnalyticsIntegratorInterface) mapElement.getValue()).onActivityDestroyed(activity);
        }
    }

    public static void sdkLoaded(String sdkId) {
        sdkLoadedNative(sdkId);
    }

    public static void sdkFailed(String sdkId) {
        sdkFailedNative(sdkId);
    }

    public static native void sdkLoadedNative(String sdkId);

    public static native void sdkFailedNative(String sdkId);
}

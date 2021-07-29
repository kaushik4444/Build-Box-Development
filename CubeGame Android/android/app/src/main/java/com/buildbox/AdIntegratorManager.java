package com.buildbox;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.apponboard.aob_sessionreporting.AOBReporting;
import com.buildbox.adapter.AdIntegratorInterface;
import com.buildbox.consent.ConsentHelper;
import com.buildbox.consent.SdkConsentInfo;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.preference.PreferenceManager;

public class AdIntegratorManager {

    private static HashMap<String, AdIntegratorInterface> integrators = new HashMap<>();

    private static WeakReference<Activity> activity;
    private static String TAG = "AdIntegratorManager";

    public static void initBridge(Activity act) {
        activity = new WeakReference<>(act);
        /* ironsource */ /* integrators.put("ironsource", new com.buildbox.adapter.ironsource.AdIntegrator()); */ /* ironsource */
        /* admob */ /* integrators.put("admob", new com.buildbox.adapter.admob.AdIntegrator()); */ /* admob */
        /* custom */ /* integrators.put("custom", new com.buildbox.adapter.custom.AdIntegrator()); */ /* custom */
    }

    public static void initSdk(String sdkId, final HashMap<String, String> initValues) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.get());
        final boolean userConsent = sharedPreferences.getBoolean(ConsentHelper.getConsentKey(sdkId), false);
        Log.d(TAG, "ad init ads hit with network " + sdkId);
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing ads for " + sdkId);
            Log.d(TAG, "setting user consent: " + userConsent);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setUserConsent(userConsent);
                    integrator.initAds(initValues, activity);
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
            Log.d(TAG, "failing ad network " + sdkId);
            networkFailed(sdkId);
        }
    }

    public static void initBanner(String sdkId) {
        Log.d(TAG, "initBanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void initInterstitial(String sdkId) {
        Log.d(TAG, "initInterstitial");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing inter for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initInterstitial();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void initRewardedVideo(String sdkId) {
        Log.d(TAG, "initrewarded");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "initializing reward for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.initRewardedVideo();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showBanner(String sdkId) {
        Log.d(TAG, "showbanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void hideBanner(String sdkId) {
        Log.d(TAG, "hidebanner");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "hide banner for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.hideBanner();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showInterstitial(String sdkId) {
        Log.d(TAG, "showinterstitial");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show inter for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showInterstitial();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static void showRewardedVideo(String sdkId) {
        Log.d(TAG, "showrewarded");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "show rewarded for " + sdkId);
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.showRewardedVideo();
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
    }

    public static boolean isBannerVisible(String sdkId) {
        Log.d(TAG, "isbannervisible");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            return integrator.isBannerVisible();
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
        return false;
    }

    public static boolean isRewardedVideoAvailable(String sdkId) {
        Log.d(TAG, "isrewardedavailable");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            Log.d(TAG, "is rewarded available for " + sdkId);
            return integrator.isRewardedVideoAvailable();
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
        }
        return false;
    }

    public static void setUserConsent(String sdkId, final boolean consentGiven) {
        Log.d(TAG, "setuserconsent");
        final AdIntegratorInterface integrator = integrators.get(sdkId);
        if (integrator != null) {
            activity.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    integrator.setUserConsent(consentGiven);
                }
            });
        } else {
            Log.e(TAG, "Ad network " + sdkId + " not found in map");
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
                    final AdIntegratorInterface integrator = integrators.get(sdkConsentInfo.getSdkId());
                    if (integrator!=null){
                        integrator.setUserConsent(false);
                    } else {
                        Log.e(TAG, "Ad network " + sdkConsentInfo.getSdkId() + " not found in map");
                    }
                }
                Toast toast = Toast.makeText(activity.get(), "Consent revoked for all ad networks", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public static void bannerImpression(String sdkId) {
        AOBReporting.bannerAdAttempt(sdkId, true);
    }

    public static void interstitialImpression(String sdkId) {
        AOBReporting.interstitialAdAttempt(sdkId, true);
    }

    public static void rewardedVideoImpression(String sdkId) {
        AOBReporting.rewardedVideoAdAttempt(sdkId, true);
    }

    public static void onActivityCreated(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityCreated(activity);
        }
    }

    public static void onActivityStarted(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityStarted(activity);
        }
    }

    public static void onActivityResumed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityResumed(activity);
        }
    }

    public static void onActivityPaused(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityPaused(activity);
        }
    }

    public static void onActivityStopped(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityStopped(activity);
        }
    }

    public static void onActivityDestroyed(Activity activity) {
        for (Map.Entry mapElement : integrators.entrySet()) {
            ((AdIntegratorInterface) mapElement.getValue()).onActivityDestroyed(activity);
        }
    }

    public static void interstitialClosed(String sdkId) {
        interstitialClosedNative(sdkId);
    }

    public static void rewardedVideoDidReward(String sdkId, boolean value) {
        rewardedVideoDidRewardNative(sdkId, value);
    }

    public static void rewardedVideoDidEnd(String sdkId, boolean value) {
        rewardedVideoDidEndNative(sdkId, value);
    }

    public static void bannerLoaded(String sdkId) {
        bannerLoadedNative(sdkId);
    }

    public static void interstitialLoaded(String sdkId) {
        interstitialLoadedNative(sdkId);
    }

    public static void rewardedVideoLoaded(String sdkId) {
        rewardedVideoLoadedNative(sdkId);
    }

    public static void bannerFailed(String sdkId) {
        bannerFailedNative(sdkId);
        AOBReporting.bannerAdAttempt(sdkId, false);
    }

    public static void interstitialFailed(String sdkId) {
        interstitialFailedNative(sdkId);
        AOBReporting.interstitialAdAttempt(sdkId, false);
    }

    public static void rewardedVideoFailed(String sdkId) {
        rewardedVideoFailedNative(sdkId);
        AOBReporting.rewardedVideoAdAttempt(sdkId, false);
    }

    public static void sdkLoaded(String sdkId) {
        sdkLoadedNative(sdkId);
    }

    /**
     * @deprecated Replaced with {@link #sdkLoaded(String)}.
     */
    public static void networkLoaded(String adNetworkId) {
        sdkLoadedNative(adNetworkId);
    }

    public static void sdkFailed(String sdkId) {
        sdkFailedNative(sdkId);
    }

    /**
     * @deprecated Replaced with {@link #sdkFailed(String)}.
     */
    public static void networkFailed(String adNetworkId) {
        sdkFailedNative(adNetworkId);
    }

    public static native void sdkLoadedNative(String sdkId);

    public static native void sdkFailedNative(String sdkId);

    public static native void interstitialClosedNative(String sdkId);

    public static native void rewardedVideoDidRewardNative(String sdkId, boolean value);

    public static native void rewardedVideoDidEndNative(String sdkId, boolean value);

    public static native void bannerLoadedNative(String sdkId);

    public static native void interstitialLoadedNative(String sdkId);

    public static native void rewardedVideoLoadedNative(String sdkId);

    public static native void bannerFailedNative(String sdkId);

    public static native void interstitialFailedNative(String sdkId);

    public static native void rewardedVideoFailedNative(String sdkId);
}

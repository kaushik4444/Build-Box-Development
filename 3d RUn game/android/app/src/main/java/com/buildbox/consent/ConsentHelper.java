package com.buildbox.consent;

import java.util.ArrayList;
import java.util.List;

public class ConsentHelper {

    public static List<SdkConsentInfo> getSdkConsentInfos() {
        ArrayList<SdkConsentInfo> sdks = new ArrayList<>();
        /* admob */ /*
        sdks.add( new SdkConsentInfo("admob", "Admob", "https://policies.google.com/technologies/partner-sites"));
        */ /* admob */
        //sdks.add( new SdkConsentInfo("{networkname}", "{DisplayName}", "{PrivacyPolicyUrl}"));
        /* ironsource */ /*
        sdks.add( new SdkConsentInfo("ironsource", "ironSource", "https://developers.ironsrc.com/ironsource-mobile/android/ironsource-mobile-privacy-policy/"));
        */ /* ironsource */
        /* facebook-analytics */ /*
        sdks.add(new SdkConsentInfo("facebook-analytics", "Facebook Analytics", "https://www.facebook.com/about/privacy/"));
        */ /* facebook-analytics */
        return sdks;
    }

    public static String getConsentKey(String sdkId) {
        return sdkId + "_CONSENT_KEY";
    }
}

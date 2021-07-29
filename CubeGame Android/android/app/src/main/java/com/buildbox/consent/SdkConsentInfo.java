package com.buildbox.consent;

public class SdkConsentInfo {
    private String sdkId;
    private String displayName;
    private String privacyPolicyUrl;
    private boolean didConsent = false;

    public SdkConsentInfo(String sdkId, String displayName, String privacyPolicyUrl){
        this.sdkId = sdkId;
        this.displayName = displayName;
        this.privacyPolicyUrl = privacyPolicyUrl;
    }

    public String getSdkId() {
        return sdkId;
    }

    String getDisplayName() {
        return displayName;
    }

    String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    public Boolean getConsent() {
        return didConsent;
    }

    public void setConsent(Boolean didConsent) {
        this.didConsent = didConsent;
    }
}

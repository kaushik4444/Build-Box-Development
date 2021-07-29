package com.buildbox.consent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import jumpAvoidance.com.PTPlayer;
import jumpAvoidance.com.R;

import java.util.List;

public class ConsentActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consent);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button agreeButton = findViewById(R.id.buttonYesToAll);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConsentForAll(true);
                startPTPlayer();
            }
        });

        TextView disagree = findViewById(R.id.buttonNoToAll);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConsentForAll(false);
                startPTPlayer();
            }
        });

        TextView privacyPolicy = findViewById(R.id.textPrivacyPolicy);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConsentDetailFragment().show(getSupportFragmentManager(), "consent");
            }
        });
    }

    private void setConsentForAll(boolean consent) {
        List<SdkConsentInfo> sdkConsentInfos = ConsentHelper.getSdkConsentInfos();

        for (SdkConsentInfo sdkConsentInfo : sdkConsentInfos) {
            sharedPreferences
                .edit()
                .putBoolean(ConsentHelper.getConsentKey(sdkConsentInfo.getSdkId()), consent)
                .commit();
        }
    }

    void startPTPlayer() {
        Intent intent = new Intent(this, PTPlayer.class);
        startActivity(intent);
        finish();
    }
}

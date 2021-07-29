package com.buildbox.consent;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import jumpAvoidance.com.R;

import java.util.List;

public class ConsentDetailFragment extends DialogFragment implements ConsentAdapter.Listener {
    private SharedPreferences sharedPreferences;
    private List<SdkConsentInfo> sdkConsentInfos = ConsentHelper.getSdkConsentInfos();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consent_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rvConsentDetails);
        ConsentAdapter adapter = new ConsentAdapter(sdkConsentInfos, this);
        recyclerView.setAdapter(adapter);
        TextView buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button buttonConfirm = view.findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SdkConsentInfo sdkConsentInfo : sdkConsentInfos) {
                    boolean consent = sdkConsentInfo.getConsent();
                    sharedPreferences
                        .edit()
                        .putBoolean(ConsentHelper.getConsentKey(sdkConsentInfo.getSdkId()), consent)
                        .commit();
                }

                ConsentActivity consentActivity = ((ConsentActivity) getActivity());
                if (consentActivity != null) {
                    ((ConsentActivity) getActivity()).startPTPlayer();
                }
            }
        });
    }

    @Override
    public void toggleConsent(int adapterPosition, boolean consent) {
        sdkConsentInfos.get(adapterPosition).setConsent(consent);
    }

    @Override
    public void clickPrivacyPolicy(SdkConsentInfo sdkConsentInfo) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(sdkConsentInfo.getPrivacyPolicyUrl()));
        startActivity(i);
    }
}

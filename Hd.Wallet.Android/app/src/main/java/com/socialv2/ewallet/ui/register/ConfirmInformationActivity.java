package com.socialv2.ewallet.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.idCard.IdCardDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;
import com.socialv2.ewallet.ui.facialRecognition.IntroduceFacialRecognitionActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class ConfirmInformationActivity extends BaseActivity {

    private final String TAG = ConfirmInformationActivity.class.getName();

    private Button mRetakeButton;
    private Button mContinueButton;
    private TextView mNameTextView;
    private TextView mBirthdayTextView;
    private TextView mIdNumberTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mPlaceOfResidenceTextView;
    private TextView mGenderTextView;
    private View mGenderFieldView;

    public ConfirmInformationActivity() {
        super(R.layout.activity_confirm_information);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContinueButton = findViewById(R.id.continueButton);
        mRetakeButton = findViewById(R.id.retakeButton);
        mNameTextView = findViewById(R.id.nameTextView);
        mBirthdayTextView = findViewById(R.id.birthdayTextView);
        mIdNumberTextView = findViewById(R.id.idNumberTextView);
        mPlaceOfOriginTextView = findViewById(R.id.placeOfOriginTextView);
        mPlaceOfResidenceTextView = findViewById(R.id.placeOfResidenceTextView);
        mGenderFieldView = findViewById(R.id.genderFieldView);
        mGenderTextView = findViewById(R.id.genderTextView);
        initView();
        bindExtract();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mContinueButton.setOnClickListener(view -> {
            //NavigateUtil.navigateTo(this, IntroduceFacialRecognitionActivity.class);
            Bundle bundle = getIntent()
                    .getExtras();
            Intent newintent = new Intent(this, SignUpAccountActivity .class);
            newintent.putExtra("IdCardExtract", bundle.getString("IdCardExtract"));
            startActivity(newintent);
        });

        mRetakeButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void bindExtract() {

        Bundle bundle = getIntent()
                .getExtras();

        if (bundle == null) {
            return;
        }
        String json = bundle.getString("IdCardExtract");

        Gson gson = new Gson();
        IdCardExtractDto idCardExtract = gson.fromJson(json, IdCardExtractDto.class);

        if (idCardExtract != null) {
            IdCardDto idCard = idCardExtract.getIdCard();

            Log.i(TAG, idCardExtract.toString());

            mNameTextView.setText(idCard.getFullName());
            mBirthdayTextView.setText(idCard.getDateOfBirth());
            mIdNumberTextView.setText(idCard.getId());
            mPlaceOfOriginTextView.setText(idCard.getPlaceOfOrigin());
            mPlaceOfResidenceTextView.setText(idCard.getPlaceOfResidence());

            if (idCardExtract.getType().equals("CMND")) {
                mGenderFieldView.setVisibility(TextView.GONE);
            } else {
                mGenderFieldView.setVisibility(TextView.VISIBLE);
                mGenderTextView.setText(idCard.getSex());
            }
        }
    }

    private void resetFields() {
        mNameTextView.setText("");
        mBirthdayTextView.setText("");
        mIdNumberTextView.setText("");
        mPlaceOfOriginTextView.setText("");
        mPlaceOfResidenceTextView.setText("");
    }
}
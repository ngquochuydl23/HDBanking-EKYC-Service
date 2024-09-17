package com.socialv2.ewallet.ui.register;

import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.chaos.view.PinView;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.idCardTaken.GettingTakenIdCardActivity;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

public class RegisterCheckOtpActivity extends AppCompatActivity {

    private final int N_PIN_ITEMS = 6;

    private PinView mOtpTextView;
    private Button mContinueButton;
    private ProgressBar mLoadingProgressBar;
    private TextView mErrorTextView;
    private View mResendButton;
    private TextView mCountDownTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_check_otp);

        mOtpTextView = findViewById(R.id.otpTextView);
        mContinueButton = findViewById(R.id.continueButton);
        mLoadingProgressBar = findViewById(R.id.loadingProgressBar);
        mErrorTextView = findViewById(R.id.errorTextView);
        mResendButton = findViewById(R.id.resendButton);
        mCountDownTextView = findViewById(R.id.countDownTextView);

        initView();
        startResendOtpCountdown();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mLoadingProgressBar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
        mResendButton.setVisibility(View.GONE);
        mOtpTextView.setEnabled(true);
        mContinueButton.setEnabled(false);
        mOtpTextView.requestFocus();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - (2 * DpToPx.convert(this, 15));
        int totalSpacing = width - (DpToPx.convert(this, 48) * N_PIN_ITEMS);
        mOtpTextView.setItemSpacing(totalSpacing / (N_PIN_ITEMS - 1));
        mOtpTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mContinueButton.setEnabled(s.length() + count == N_PIN_ITEMS);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mContinueButton.setEnabled(s.length() == N_PIN_ITEMS);
                if (s.length() == N_PIN_ITEMS) {
                    new Handler().postDelayed(() -> {
                        onOtpCompleted();
                    }, 500); // 500ms delay
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mResendButton.setOnClickListener(view -> {
            startResendOtpCountdown();
        });

        mContinueButton.setOnClickListener(view -> {
            onOtpCompleted();
        });
    }

    private void onOtpCompleted() {
        mOtpTextView.setEnabled(false);
        mContinueButton.setEnabled(false);
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.GONE);
        mOtpTextView.setLineColor(getColor(R.color.borderWidthColor));
        String otpCode = mOtpTextView
                .getText()
                .toString();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingProgressBar.setVisibility(View.GONE);
                mOtpTextView.setEnabled(true);
                mContinueButton.setEnabled(true);

                if (otpCode.equals("123456")) {
                    countDownTimer.cancel();
                    finish();
                    NavigateUtil.navigateTo(RegisterCheckOtpActivity.this, GettingTakenIdCardActivity.class);
                } else {
                    mErrorTextView.setVisibility(View.VISIBLE);
                    mErrorTextView.setText("Xác thực OTP thất bại. Vui lòng thử lại");

                    mOtpTextView.setEnabled(true);
                    mContinueButton.setEnabled(false);
                    mOtpTextView.setText("");
                    mOtpTextView.setLineColor(ResourcesCompat.getColorStateList(getResources(), R.color.error, getTheme()));
                }
            }
        }, 2000);

    }

    private void startResendOtpCountdown() {
        mCountDownTextView.setVisibility(View.VISIBLE);
        mResendButton.setVisibility(View.GONE);

        // 60 seconds countdown (60000 milliseconds), updating every 1 second (1000 milliseconds)
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                // Update the countdown text
                long second = millisUntilFinished / 1000;
                mCountDownTextView.setText(second + " giây");
            }

            public void onFinish() {
                mCountDownTextView.setText("");
                mCountDownTextView.setVisibility(View.GONE);
                mResendButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
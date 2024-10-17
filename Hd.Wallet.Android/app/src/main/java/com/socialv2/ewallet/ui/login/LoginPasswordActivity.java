package com.socialv2.ewallet.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.HttpResponseDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.authHttp.AuthHttpImpl;
import com.socialv2.ewallet.https.api.authHttp.IAuthService;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.sharedReferences.SaveTokenSharedPreference;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.ParseHttpError;
import com.socialv2.ewallet.utils.WindowUtils;


public class LoginPasswordActivity extends AppCompatActivity {

    private static final String TAG = LoginPasswordActivity.class.getName();

    private Button mContinue;
    private TextView mHiddenPhoneNumberTextView, tvChangeSdt;
    private EditText mPasswordEditText;
    private Button mChangePhoneNumberButton;
    private View mForgotPasswordTextView;
    private BackdropLoadingDialogFragment mLoadingBackdropDialog;
    private IAuthService mAuthService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_password);

        mAuthService = new AuthHttpImpl(this);

        mContinue = findViewById(R.id.continueButton);
        mForgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        mChangePhoneNumberButton = findViewById(R.id.changePhoneNumberButton);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mHiddenPhoneNumberTextView = findViewById(R.id.hiddenPhoneNumberTextView);

        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());

        getPhoneNumber();
        initView();
    }

    private void getPhoneNumber() {

        String phoneNumber = new KeyValueSharedPreferences(this, "PhoneNumberLogin").getData();
        if (phoneNumber != null) {

            String maskedPhoneNumber = phoneNumber.replaceAll("\\d(?=\\d{3})", "*");
            String text = "Số điện thoại " + maskedPhoneNumber + " đang đăng nhập tài khoản HDBank. Quý khách vui lòng nhập mật khẩu để tiếp tục";
            SpannableString spannableString = new SpannableString(text);
            int start = text.indexOf(maskedPhoneNumber);
            int end = start + maskedPhoneNumber.length();

            if (start >= 0) {
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            mHiddenPhoneNumberTextView.setText(spannableString);
        }
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mLoadingBackdropDialog.setLoading(false);
        mContinue.setOnClickListener(view -> {
            login();
        });

        mForgotPasswordTextView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, ResetPasswordActivity.class);
        });

        mChangePhoneNumberButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(this, LoginActivity.class);
        });
    }

    @SuppressLint("CheckResult")
    private void login() {
        mLoadingBackdropDialog.setLoading(true);
        String phoneNumber = new KeyValueSharedPreferences(this, "PhoneNumberLogin").getData();
        String password = mPasswordEditText
                .getText()
                .toString();

        mAuthService.login(phoneNumber, password)
                .subscribe(response -> {
                    Log.i(TAG, response.toString());

                    mLoadingBackdropDialog.setLoading(false);

                    String token = response
                            .getResult()
                            .getToken();

                    UserDto userDto = response
                            .getResult()
                            .getUser();


                    new KeyValueSharedPreferences(this, "PhoneNumberLogin").setData(phoneNumber);
                    new SaveTokenSharedPreference(this).setData(token);

                    NavigateUtil.navigateTo(this, com.socialv2.ewallet.ui.main.MainHomeActivity.class);
                    finish();

                }, throwable -> {
                    mLoadingBackdropDialog.setLoading(false);

                    int statusCode = ParseHttpError.getStatusCode(throwable);
                    HttpResponseDto<?> errorBody = ParseHttpError.parse(throwable);


                    if (statusCode == 400) {
                        Log.e(TAG, errorBody.getError());

                        if (errorBody.getError().equals("User not found")) {
                            // ít xảy trường hợp này.
                        } else if (errorBody.getError().equals("Password is incorrect")) {
                            // cảnh báo sai mật khẩu.
                        }
                    } else if (statusCode == 500) {
                        Log.e(TAG, errorBody.getError());

                        // Đưa ra lỗi chung chung
                    }
                    throwable.printStackTrace();
                }, () -> {
                    mLoadingBackdropDialog.setLoading(false);
                });

    }
}
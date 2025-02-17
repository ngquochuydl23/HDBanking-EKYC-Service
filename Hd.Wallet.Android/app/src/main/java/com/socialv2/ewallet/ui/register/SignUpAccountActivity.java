package com.socialv2.ewallet.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.RequestSignUpDto;
import com.socialv2.ewallet.dtos.idCard.IdCardExtractDto;
import com.socialv2.ewallet.https.api.registerHttp.IRegisterService;
import com.socialv2.ewallet.https.api.registerHttp.RegisterHttpImpl;
import com.socialv2.ewallet.singleton.RegisterDataSingleton;
import com.socialv2.ewallet.ui.idCardTaken.IdCardTakenActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.List;

public class SignUpAccountActivity extends BaseActivity implements
        Validator.ValidationListener {

    private final String TAG = SignUpAccountActivity.class.getName();

    private BackdropLoadingDialogFragment mLoadingBackdropDialog;
    private Validator mValidator;

    @Email(message = "Email phải đúng định dạng")
    @NotEmpty(message = "Không được bỏ trống")
    @Order(1)
    private TextInputEditText mEmailEditText;
    private TextInputLayout mEmailTextInputLayout;

    @Order(2)
    @Password(
            min = 8,
            scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,
            message = "Vui lòng nhập đúng định dạng"
    )
    @NotEmpty(message = "Không được bỏ trống")
    private TextInputEditText mPasswordEditText;
    private TextInputLayout mPasswordTextInputLayout;

    @Order(3)
    @ConfirmPassword(message = "Mật khẩu không khớp")
    @NotEmpty(message = "Không được bỏ trống")
    private TextInputEditText mConfirmPasswordEditText;
    private TextInputLayout mConfirmPasswordTextInputLayout;

    @Order(4)
    @Checked(value = true)
    private MaterialCheckBox mTermsCheckBox;

    @Order(5)
    @Checked(value = true)
    private MaterialCheckBox mPrivacyCheckBox;

    private Button mSignUpButton;

    public SignUpAccountActivity() {
        super(R.layout.activity_sign_up_account);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mValidator = new Validator(this);
        mLoadingBackdropDialog = new BackdropLoadingDialogFragment();
        mLoadingBackdropDialog.setFragmentManager(getSupportFragmentManager());


        mEmailEditText = findViewById(R.id.emailEditText);
        mEmailTextInputLayout = findViewById(R.id.emailTextInputLayout);

        mPasswordEditText = findViewById(R.id.passwordEditText);
        mPasswordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

        mConfirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        mConfirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);

        mTermsCheckBox = findViewById(R.id.termsCheckBox);
        mPrivacyCheckBox = findViewById(R.id.privacyCheckBox);

        mSignUpButton = findViewById(R.id.signUpButton);
        initView();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
        mLoadingBackdropDialog.setLoading(false);
        mSignUpButton.setEnabled(false);

        mPasswordEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mValidator.validateTill(mEmailEditText);
            }
        });

        mConfirmPasswordEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mValidator.validateTill(mPasswordEditText);
            }
        });

        mTermsCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            mValidator.validate();
        });

        mPrivacyCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            mValidator.validate();
        });
        mSignUpButton.setOnClickListener(view -> {
            signUp();
        });
    }

    @Override
    public void onValidationSucceeded() {
        clearFieldStates();
        mSignUpButton.setEnabled(true);
    }

    private void clearFieldStates() {
        mEmailTextInputLayout.setError(null);
        mPasswordTextInputLayout.setError(null);
        mConfirmPasswordTextInputLayout.setError(null);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        clearFieldStates();
        mSignUpButton.setEnabled(false);
        mLoadingBackdropDialog.setLoading(false);

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view.equals(mEmailEditText) && mEmailEditText.isDirty()) {
                mEmailTextInputLayout.setError(message);
            }

            if (view.equals(mPasswordEditText) && mPasswordEditText.isDirty()) {
                mPasswordTextInputLayout.setError(message);
            }

            if (view.equals(mConfirmPasswordEditText) && mConfirmPasswordEditText.isDirty()) {
                mConfirmPasswordTextInputLayout.setError(message);
            }
        }
    }

    @SuppressLint("CheckResult")
    private void signUp() {
        mLoadingBackdropDialog.setLoading(true);
        mSignUpButton.setEnabled(false);

        mEmailEditText.setEnabled(false);
        mPasswordEditText.setEnabled(false);
        mConfirmPasswordEditText.setEnabled(false);


        String email = mEmailEditText
                .getText()
                .toString()
                .trim();

        String password = mPasswordEditText
                .getText()
                .toString()
                .trim();

        RequestSignUpDto body = RegisterDataSingleton
                .getInstance()
                .getData()
                .getValue();

        body.setEmail(email);
        body.setPassword(password);

        Log.d(TAG, body.toString());
        IRegisterService registerService = new RegisterHttpImpl(this);

        registerService.signUp(body)
                .subscribe(response -> {
                            Log.d(TAG, response.getResult().toString());

                            Intent intent = new Intent(this, RegistrationSuccessfulActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        });

    }

    private IdCardExtractDto getIdCard() {
        Bundle bundle = getIntent()
                .getExtras();

        if (bundle == null) {
            return null;
        }
        String json = bundle.getString("IdCardExtract");

        Gson gson = new Gson();
        return gson.fromJson(json, IdCardExtractDto.class);
    }

    @Override
    public void onBackPressed() {
        if (mLoadingBackdropDialog != null && mLoadingBackdropDialog.isLoading()) {
            Log.d(TAG, "Backdrop loading dialog is visible, back press is ignored");
            // Do nothing to prevent the dialog from being dismissed
        } else {
            super.onBackPressed(); // Call the super method if the dialog is not visible
        }
    }
}
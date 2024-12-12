package com.socialv2.ewallet.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.RequestSignUpDto;
import com.socialv2.ewallet.sharedReferences.KeyValueSharedPreferences;
import com.socialv2.ewallet.singleton.RegisterDataSingleton;
import com.socialv2.ewallet.ui.login.LoginActivity;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.WindowUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RegistrationSuccessfulActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView mNumberAccountTextView;
    private TextView mNameUserTextView;
    private TextView mDateRegisterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_successful);

        loginButton = findViewById(R.id.loginButton);
        mNumberAccountTextView = findViewById(R.id.numberAccountTextView);
        mNameUserTextView = findViewById(R.id.nameUserTextView);
        mDateRegisterTextView = findViewById(R.id.dateRegisterTextView);
        initView();
        bindData();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));
        loginButton.setOnClickListener(view -> {


            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void bindData() {
        RequestSignUpDto data = RegisterDataSingleton.getInstance()
                .getData()
                .getValue();

        if (data != null) {
            mNameUserTextView.setText(data.getFullName());
            mNumberAccountTextView.setText(data.getPhoneNumber());


            LocalDateTime dateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

            // Define a custom formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'Tháng' M, yyyy", new Locale("vi", "VN"));

            // Format the LocalDateTime to the desired string
            String formattedDate = dateTime.format(formatter);

            mDateRegisterTextView.setText(formattedDate);
        }
    }
}

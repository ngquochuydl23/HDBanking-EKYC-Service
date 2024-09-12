package com.socialv2.ewallet.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.textfield.TextInputEditText;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button mContinueButton;
    private TextInputEditText editTextPhoneNumber;

    // Regex để kiểm tra số điện thoại Việt Nam
    private static final Pattern VIETNAM_PHONE_PATTERN = Pattern.compile("^0[3|5|7|8|9]\\d{8}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mContinueButton = findViewById(R.id.continueButton);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // Gọi hàm khởi tạo và thiết lập TextWatcher
        initView();
        initPhoneNumberValidation();
    }

    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mContinueButton.setOnClickListener(view -> {
            String phoneNumber = editTextPhoneNumber.getText().toString();
            if (isValidVietnamPhoneNumber(phoneNumber)) {
                NavigateUtil.navigateTo(this, GettingTakenIdCardActivity.class);
            } else {
                Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm kiểm tra số điện thoại
    private boolean isValidVietnamPhoneNumber(String phoneNumber) {
        return VIETNAM_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    // Thêm TextWatcher để theo dõi khi người dùng nhập
    private void initPhoneNumberValidation() {
        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần làm gì
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Khi người dùng nhập, kiểm tra tính hợp lệ của số điện thoại
                String phoneNumber = charSequence.toString();
                if (!isValidVietnamPhoneNumber(phoneNumber)) {
                    editTextPhoneNumber.setError("Số điện thoại không hợp lệ");
                } else {
                    editTextPhoneNumber.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần làm gì
            }
        });
    }
}

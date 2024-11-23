package com.socialv2.ewallet.ui.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.BackdropLoadingDialogFragment;
import com.socialv2.ewallet.dtos.users.RequestUpdateUserDto;
import com.socialv2.ewallet.https.api.userHttp.IUserService;
import com.socialv2.ewallet.https.api.userHttp.UserHttpImpl;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.utils.DateFormatter;
import com.socialv2.ewallet.utils.WindowUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = ProfileActivity.class.getName();

    private AutoCompleteTextView mJobAutoCompleteTextView;
    private AutoCompleteTextView mStudyLevelAutoCompleteTextView;
    private AutoCompleteTextView mMarriageStatusAutoCompleteTextView;
    private AutoCompleteTextView mPositionAutoCompleteTextView;
    private AutoCompleteTextView mProvinceAutoCompleteTextView;
    private AutoCompleteTextView mHometownAutoCompleteTextView;
    private AutoCompleteTextView mGenderAutoCompleteTextView;

    @Email(message = "Email phải đúng định dạng")
    @NotEmpty(message = "Không được bỏ trống")
    private TextInputEditText mEmailEditText;

    private TextInputEditText mDateOfBirthEditText;

    private Button mSaveButton;

    private IUserService mUserService;

    public ProfileActivity() {
        super(R.layout.activity_profile);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserService = new UserHttpImpl(this);

        mJobAutoCompleteTextView = findViewById(R.id.jobAutoCompleteTextView);
        mStudyLevelAutoCompleteTextView = findViewById(R.id.studyLevelAutoCompleteTextView);
        mMarriageStatusAutoCompleteTextView = findViewById(R.id.marriageStatusAutoCompleteTextView);
        mPositionAutoCompleteTextView = findViewById(R.id.positionAutoCompleteTextView);
        mProvinceAutoCompleteTextView = findViewById(R.id.provinceAutoCompleteTextView);
        mHometownAutoCompleteTextView = findViewById(R.id.hometownAutoCompleteTextView);
        mDateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mGenderAutoCompleteTextView = findViewById(R.id.genderAutoCompleteTextView);

        mSaveButton = findViewById(R.id.saveButton);

        initView();
        getData();
        getUser();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mJobAutoCompleteTextView.setDropDownBackgroundResource(R.drawable.drawable_common_dropdown_popup);
        mJobAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOption = getResources().getStringArray(R.array.jobs)[position];
            Log.d(TAG, selectedOption);
        });

        mSaveButton.setOnClickListener(view -> {
            save();
        });
    }

    private void getData() {
        mJobAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.jobs)
        ));

        mStudyLevelAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.study_level)
        ));

        mMarriageStatusAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.marriage_status)
        ));

        mPositionAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.job_positions)
        ));

        mProvinceAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.vietnam_provinces)
        ));

        mHometownAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.vietnam_provinces)
        ));

        mGenderAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                getResources().getStringArray(R.array.genders)
        ));
    }

    private void getUser() {
        UserSingleton.getInstance()
                .getData()
                .observe(this, user -> {
                    mEmailEditText.setText(user.getEmail());
                    mDateOfBirthEditText.setText(DateFormatter.formatToVietnameseDate(user.getDateOfBirth()));

                    if (user.getSex() != null && user.getSex() != -1) {
                        mGenderAutoCompleteTextView.setText(getResources().getStringArray(R.array.genders)[user.getSex()]);
                        mGenderAutoCompleteTextView.setEnabled(false);
                    } else {
                        mGenderAutoCompleteTextView.setEnabled(true);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void save() {
        Log.d(TAG, "Updating user information");

        BackdropLoadingDialogFragment loadingDialog = BackdropLoadingDialogFragment.getInstance(getSupportFragmentManager());

        loadingDialog.setLoading(true);
        mUserService.updateUserInfo(getDataFields())
                .subscribe(response -> {

                    Log.d(TAG, "Update user information successfully");
                    loadingDialog.setLoading(false);

                    UserSingleton
                            .getInstance()
                            .setData(response.getResult());

                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Log.e(TAG, "Update user failed", throwable);

                    Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                    loadingDialog.setLoading(false);
                });
    }

    private RequestUpdateUserDto getDataFields() {
        String occupation = mJobAutoCompleteTextView
                .getText()
                .toString()
                .trim();

        String position = mPositionAutoCompleteTextView
                .getText()
                .toString()
                .trim();

        String marriageStatus = mMarriageStatusAutoCompleteTextView
                .getText()
                .toString()
                .trim();

        String studyLevel = mStudyLevelAutoCompleteTextView
                .getText()
                .toString()
                .trim();

        String email = mEmailEditText
                .getText()
                .toString();

        String currentLiving = mProvinceAutoCompleteTextView
                .getText()
                .toString();

        int sex = mGenderAutoCompleteTextView
                .getText()
                .toString()
                .trim()
                .equals("Nam") ? 1 : 0;

        return new RequestUpdateUserDto(occupation, position, email, currentLiving, marriageStatus, studyLevel, sex);
    }
}
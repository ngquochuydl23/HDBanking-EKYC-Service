package com.socialv2.ewallet.ui.main.moreTab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.MenuAppDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.userHttp.IUserService;
import com.socialv2.ewallet.https.api.userHttp.UserHttpImpl;
import com.socialv2.ewallet.permissions.Permissions;
import com.socialv2.ewallet.s3.S3Service;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.ui.dev.WriteNfcActivity;
import com.socialv2.ewallet.ui.nfcScan.IdCardNfcScanActivity;
import com.socialv2.ewallet.ui.privacyAndSecurity.PrivacyAndSecurity1Activity;
import com.socialv2.ewallet.ui.profile.ProfileActivity;
import com.socialv2.ewallet.ui.settings.notifications.NotificationSettingActivity;
import com.socialv2.ewallet.utils.LocalFile;
import com.socialv2.ewallet.utils.NavigateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = ProfileFragment.class.getName();
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 2;
    private RecyclerView mMenuAppRecyclerView;
    private MenuAppAdapter mMenuAppAdapter;
    private Button mLogOutButton;
    private AvatarView mAvatarImageView;
    private TextView mFullnameTextView;
    private TextView mPhoneNumberTextView;
    private View mUserInfoContainerView;
    private View mProvideIdCardViaNFCView;
    private NfcAdapter mNFCAdapter;
    private Button mUpdateButton;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private IUserService mUserService;


    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mNFCAdapter = NfcAdapter.getDefaultAdapter(getContext());
        mMenuAppAdapter = new MenuAppAdapter();
        mUserService = new UserHttpImpl(getContext());

        mUserInfoContainerView = view.findViewById(R.id.userInfoContainerView);
        mMenuAppRecyclerView = view.findViewById(R.id.menuAppRecyclerView);
        mLogOutButton = view.findViewById(R.id.logOutButton);
        mAvatarImageView = view.findViewById(R.id.avatarImageView);
        mFullnameTextView = view.findViewById(R.id.fullnameTextView);
        mPhoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        mProvideIdCardViaNFCView = view.findViewById(R.id.provideIdCardViaNFCView);
        mUpdateButton = view.findViewById(R.id.updateButton);


        initView();
        checkNFCSupport();
        observeUserData();
    }

    private void initView() {
        List<MenuAppDto> menuAppItems = new ArrayList<>();
        menuAppItems.add(new MenuAppDto("Tài khoản", "Xác minh", R.drawable.icon_profile_unactive, 0, null));
        menuAppItems.add(new MenuAppDto("Quyền riêng tư", null, R.drawable.ic_privacy, 0, PrivacyAndSecurity1Activity.class));
        menuAppItems.add(new MenuAppDto("Thông báo", null, R.drawable.ic_menu_notification, 0, NotificationSettingActivity.class));
        menuAppItems.add(new MenuAppDto("Ngôn ngữ", "Hệ thống mặc định", R.drawable.ic_language, 0, null));
        menuAppItems.add(new MenuAppDto("Ghi NFC", "Dành cho nhà phát triển", R.drawable.ic_language, 0, WriteNfcActivity.class));


        mMenuAppRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMenuAppRecyclerView.setAdapter(mMenuAppAdapter);
        mMenuAppAdapter.setItems(menuAppItems);



        mUserInfoContainerView.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), ProfileActivity.class);
        });

        mLogOutButton.setOnClickListener(view -> {

        });

        mUpdateButton.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), IdCardNfcScanActivity.class);
        });


        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result
                                .getData()
                                .getData();
                        uploadImageToS3(selectedImageUri);
                    }
                }
        );

        mAvatarImageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
    }

    private void observeUserData() {
        UserSingleton.getInstance().getData().observe(getViewLifecycleOwner(), user -> {

            mFullnameTextView.setText(user.getFullName());
            mPhoneNumberTextView.setText(user.getPhoneNumber());
            mAvatarImageView.setSrc(S3Service.getUrl(user.getAvatar()));
        });
    }

    private void checkNFCSupport() {
        if (mNFCAdapter == null) {
            Log.w(TAG, "NFS is not supported in this device.");

            mProvideIdCardViaNFCView.setVisibility(View.GONE);
            return;
        }

        Log.i(TAG, "NFS is supported in this device.");
        mProvideIdCardViaNFCView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("CheckResult")
    private void uploadImageToS3(Uri uri) {
        try {
            String userId = UserSingleton
                    .getInstance()
                    .getData()
                    .getValue()
                    .getId();
            String fileName = userId + "-" + System.currentTimeMillis() + ".jpg";

            File file = LocalFile.getFileFromUri(getContext(), uri);
            S3Service.getInstance(getContext())
                    .upload(fileName, file)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {

                        Log.d(TAG, "Upload successful, ETag: " + result.getETag());
                        uploadAvatar(fileName);
                    }, error -> {
                        Log.e(TAG, "Failed to upload: " + error.getMessage());
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("CheckResult")
    private void uploadAvatar(String fileName) {
        mUserService.updateAvatar(fileName)
                .subscribe(response -> {
                    Toast.makeText(getContext(), "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                    UserDto user = response.getResult();

                    UserSingleton
                            .getInstance()
                            .setData(user);

                    }, throwable -> {
                    Toast.makeText(getContext(), "Cập nhật ảnh đại diện thất bại", Toast.LENGTH_SHORT).show();
                });
    }
}

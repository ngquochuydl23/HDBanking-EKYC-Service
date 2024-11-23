package com.socialv2.ewallet.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.amazonaws.services.s3.AmazonS3Client;
import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.s3.S3Service;
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
    private String[] jobs = new String[] {
            "Bác sỹ/y tá, điều dưỡng",
            "Bảo hiểm",
            "Bất động sản",
            "Giảng viên/Giáo viên",
            "Học sinh/sinh viên",
            "Kinh Doanh/Tự doanh",
            "Bảo hiểm",
            "Nhà hàng/Khách sạn",
            "Nhân viên văn phòng",
            "Tài chính/ngân hàng",
            "Ủy thác đầu tư/Chứng khoán",
            "Khác"
    };
    public ProfileActivity() {
        super(R.layout.activity_profile);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mJobAutoCompleteTextView = findViewById(R.id.jobAutoCompleteTextView);


        initView();
        getAllJobs();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mJobAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOption = jobs[position];
            Log.d(TAG, selectedOption);
        });
    }

    private void getAllJobs() {

        mJobAutoCompleteTextView.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.item_common_option,
                jobs
        ));
    }
}
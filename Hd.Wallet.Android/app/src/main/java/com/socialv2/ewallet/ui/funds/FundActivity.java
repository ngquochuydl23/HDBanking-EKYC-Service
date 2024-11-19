package com.socialv2.ewallet.ui.funds;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseActivity;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.constants.Currency;
import com.socialv2.ewallet.dtos.FaceValueDto;
import com.socialv2.ewallet.utils.VietnameseConcurrency;
import com.socialv2.ewallet.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

public class FundActivity extends BaseActivity {

    private static final String TAG = FundActivity.class.getName();

    private RecyclerView mFaceValueRecyclerView;
    private FaceValueAdapter mFaceValueAdapter;
    private EditText mAmountMoneyEditText;
    private boolean isProgrammaticChange = false;

    private FaceValueDto selectedFaceValue;

    public FundActivity() {
        super(R.layout.activity_fund);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFaceValueAdapter = new FaceValueAdapter();

        mAmountMoneyEditText = findViewById(R.id.amountMoneyEditText);
        mFaceValueRecyclerView = findViewById(R.id.faceValueRecyclerView);
        initView();
        setUpFaceValue();
    }

    private void initView() {
        WindowUtils.applyPadding(findViewById(R.id.main));

        mFaceValueRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mFaceValueRecyclerView.addItemDecoration(new FundGapGrid(3, 20, false));
        mFaceValueRecyclerView.setAdapter(mFaceValueAdapter);

        mFaceValueAdapter.setOnChooseFaceValueClick(faceValue -> {
            selectedFaceValue = faceValue;
            mAmountMoneyEditText.setText(VietnameseConcurrency.formatWithoutSymbol(faceValue.getValue()));
            Log.d(TAG, "Choose face value: " + faceValue);
        });

        mAmountMoneyEditText.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isProgrammaticChange) {
                    isProgrammaticChange = false;
                } else {
                    Log.d("EditText", "Text changed by user typing");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    if (selectedFaceValue != null) {
                        // reset
                        Log.d(TAG, "Reset face value");
                    }

                    mAmountMoneyEditText.removeTextChangedListener(this);
                    double parsedValue = VietnameseConcurrency.parseToDouble(s.toString());

                    String formatted = VietnameseConcurrency.formatWithoutSymbol(parsedValue);
                    current = formatted;

                    mAmountMoneyEditText.setText(formatted);
                    mAmountMoneyEditText.setSelection(formatted.length());
                    mAmountMoneyEditText.addTextChangedListener(this);
                }

            }
        });
    }

    private void setUpFaceValue() {
        List<FaceValueDto> items = new ArrayList<>();
        items.add(new FaceValueDto(50000, Currency.VND));
        items.add(new FaceValueDto(100000, Currency.VND));
        items.add(new FaceValueDto(200000, Currency.VND));
        items.add(new FaceValueDto(500000, Currency.VND));
        items.add(new FaceValueDto(1000000, Currency.VND));
        items.add(new FaceValueDto(2000000, Currency.VND));

        mFaceValueAdapter.setItems(items);
    }
}
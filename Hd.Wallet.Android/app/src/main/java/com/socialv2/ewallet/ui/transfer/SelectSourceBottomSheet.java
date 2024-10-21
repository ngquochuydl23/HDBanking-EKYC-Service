package com.socialv2.ewallet.ui.transfer;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.ui.facialRecognition.VerificationFailBottomSheet;

public class SelectSourceBottomSheet extends BaseBottomSheetDialog {

    private static final String TAG = SelectSourceBottomSheet.class.getName();

    private SelectSourceBottomSheet.OnSelectSourceBank mOnSelectSourceBank;
    private View mBottomSheetLayout;
    private RecyclerView mSourceBankRecyclerView;
    private SelectSourceBankAdapter mSelectSourceBankAdapter;
    private TextView mHeaderTextView;


    private IAccountService mAccountService;

    public SelectSourceBottomSheet() {
        super(R.layout.bottomsheet_select_source);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAccountService = new AccountHttpImpl(getContext());
        mSelectSourceBankAdapter = new SelectSourceBankAdapter();
        mSelectSourceBankAdapter.setOnSelectSourceBankListener(account -> {
            if (mOnSelectSourceBank != null) {
                mOnSelectSourceBank.onSourceSelected(account);
            }
        });

        mHeaderTextView = view.findViewById(R.id.headerTextView);
        mBottomSheetLayout = view.findViewById(R.id.bottomSheetLayout);
        mSourceBankRecyclerView = view.findViewById(R.id.sourceBankRecyclerView);
        mSourceBankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSourceBankRecyclerView.setAdapter(mSelectSourceBankAdapter);


        mHeaderTextView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Drawable drawableStart = mHeaderTextView.getCompoundDrawables()[0];
                if (drawableStart != null) {
                    int drawableWidth = drawableStart.getBounds().width();
                    if (event.getX() <= (drawableWidth + mHeaderTextView.getPaddingStart())) {
                        dismiss();
                        return true;
                    }
                }
            }
            return false;
        });

        setupBottomSheetHeight();
        getAccountBanks();
    }

    @FunctionalInterface
    public interface OnSelectSourceBank {
        void onSourceSelected(AccountDto account);
    }

    public void setOnSelectSourceBank(OnSelectSourceBank mOnSelectSourceBank) {
        this.mOnSelectSourceBank = mOnSelectSourceBank;
    }

    private void setupBottomSheetHeight() {
        View toolbar = requireActivity().findViewById(R.id.toolbar);
        int toolbarHeight = toolbar.getHeight();


        int screenHeight = Resources
                .getSystem()
                .getDisplayMetrics()
                .heightPixels;

        int bottomSheetHeight = screenHeight - toolbarHeight;

        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) mBottomSheetLayout.getLayoutParams();


        layoutParams.height = bottomSheetHeight;
        mBottomSheetLayout.setLayoutParams(layoutParams);

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) mBottomSheetLayout.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @SuppressLint("CheckResult")
    private void getAccountBanks() {
        mAccountService
                .getAccounts()
                .subscribe(response -> {
                    Log.i(TAG, response.toString());
                    mSelectSourceBankAdapter.setItems(response.getResult());
                },throwable -> {
                    throwable.printStackTrace();
                },() -> {

                });
    }
}

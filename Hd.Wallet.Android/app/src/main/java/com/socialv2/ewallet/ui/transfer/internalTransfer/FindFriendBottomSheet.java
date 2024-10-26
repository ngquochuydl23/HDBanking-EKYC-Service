package com.socialv2.ewallet.ui.transfer.internalTransfer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.ui.transfer.SelectSourceBottomSheet;

import java.util.ArrayList;
import java.util.List;

public class FindFriendBottomSheet extends BaseBottomSheetDialog {

    private static final String TAG = SelectSourceBottomSheet.class.getName();

    private SelectSourceBottomSheet.OnSelectSourceBank mOnSelectSourceBank;
    private View mBottomSheetLayout;
    private RecyclerView mContactRecyclerView;
    private TextView mHeaderTextView;

    private ITransferService mAccountService;
    private ContactAdapter mContactAdapter;

    public FindFriendBottomSheet() {
        super(R.layout.bottomsheet_find_friend);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactAdapter = new ContactAdapter();
        mAccountService = new TransferHttpImpl(getContext());
//        mSelectSourceBankAdapter = new SelectSourceBankAdapter();
//        mSelectSourceBankAdapter.setOnSelectSourceBankListener(account -> {
//            if (mOnSelectSourceBank != null) {
//                mOnSelectSourceBank.onSourceSelected(account);
//            }
//        });

        mHeaderTextView = view.findViewById(R.id.headerTextView);
        mBottomSheetLayout = view.findViewById(R.id.bottomSheetLayout);
        mContactRecyclerView = view.findViewById(R.id.contactRecyclerView);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactRecyclerView.setAdapter(mContactAdapter);


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
        List<Object> contacts = new ArrayList<>();
        contacts.add(String.valueOf("A"));
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(String.valueOf("B"));
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(new Object());
        contacts.add(String.valueOf("C"));


//        Collections.sort(contacts, new Comparator<Contact>() {
//            @Override
//            public int compare(Contact c1, Contact c2) {
//                return c1.getName().compareToIgnoreCase(c2.getName());
//            }
//        });
//
//        List<Object> items = new ArrayList<>();
//        char lastHeader = '\0';
//
//        // Thêm header cho từng nhóm ký tự
//        for (Contact contact : contacts) {
//            char header = contact.getName().charAt(0);
//            if (header != lastHeader) {
//                items.add(String.valueOf(header));
//                lastHeader = header;
//            }
//            items.add(contact);
//        }
        mContactAdapter.setItems(contacts);

    }
}
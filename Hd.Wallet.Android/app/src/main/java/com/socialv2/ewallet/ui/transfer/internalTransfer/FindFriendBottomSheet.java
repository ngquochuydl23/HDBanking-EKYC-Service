package com.socialv2.ewallet.ui.transfer.internalTransfer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.socialv2.ewallet.BaseBottomSheetDialog;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.users.PublicUserDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.https.api.accountHttp.AccountHttpImpl;
import com.socialv2.ewallet.https.api.accountHttp.IAccountService;
import com.socialv2.ewallet.https.api.transferHttp.TransferHttpImpl;
import com.socialv2.ewallet.https.api.transferHttp.ITransferService;
import com.socialv2.ewallet.https.api.userHttp.IUserService;
import com.socialv2.ewallet.https.api.userHttp.UserHttpImpl;
import com.socialv2.ewallet.ui.transfer.SelectSourceBottomSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindFriendBottomSheet extends BaseBottomSheetDialog {

    private static final String TAG = FindFriendBottomSheet.class.getName();

    private SelectSourceBottomSheet.OnSelectSourceBank mOnSelectSourceBank;
    private View mBottomSheetLayout;
    private RecyclerView mContactRecyclerView;
    private TextView mHeaderTextView;
    private EditText mSearchEditText;
    private IAccountService mAccountService;
    private IUserService mUserService;
    private ContactAdapter mContactAdapter;


    public FindFriendBottomSheet() {
        super(R.layout.bottomsheet_find_friend);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContactAdapter = new ContactAdapter();
        mAccountService = new AccountHttpImpl(getContext());
        mUserService = new UserHttpImpl(getContext());
//        mSelectSourceBankAdapter = new SelectSourceBankAdapter();
//        mSelectSourceBankAdapter.setOnSelectSourceBankListener(account -> {
//            if (mOnSelectSourceBank != null) {
//                mOnSelectSourceBank.onSourceSelected(account);
//            }
//        });

        mHeaderTextView = view.findViewById(R.id.headerTextView);
        mBottomSheetLayout = view.findViewById(R.id.bottomSheetLayout);
        mContactRecyclerView = view.findViewById(R.id.contactRecyclerView);
        mSearchEditText = view.findViewById(R.id.searchEditText);

        initView();

        getAccountBanks();
    }

    private void initView() {
        setupBottomSheetHeight();
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


        mSearchEditText.addTextChangedListener(new TextWatcher() {

            private Handler handler = new Handler();
            private Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    String search = mSearchEditText
                            .getText()
                            .toString()
                            .trim();

                    String regex = "^(0|\\+84)(\\s|\\.)?((3[2-9]|5[689]|7[06-9]|8[1-689]|9[0-46-9])(\\s|\\.)?([0-9]{3})(\\s|\\.)?([0-9]{4})|(1[2689]|87|91)(\\s|\\.)?([0-9]{3})(\\s|\\.)?([0-9]{3})(\\s|\\.)?([0-9]{3}))$";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(search);
                    if (matcher.matches()) {
                        Log.d(TAG, "Match phoneNumber -> Find User by phone (Anonymously): " + search);
                        findUserAnonymously(search);
                    } else {
                        Log.d(TAG, "Find in contact: " + search);
                    }

                }
            };

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.postDelayed(runnable, 500);
            }
        });
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
//        List<Object> contacts = new ArrayList<>();
//        contacts.add(String.valueOf("A"));
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(String.valueOf("B"));
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(new Object());
//        contacts.add(String.valueOf("C"));
//
//
////        Collections.sort(contacts, new Comparator<Contact>() {
////            @Override
////            public int compare(Contact c1, Contact c2) {
////                return c1.getName().compareToIgnoreCase(c2.getName());
////            }
////        });
////
////        List<Object> items = new ArrayList<>();
////        char lastHeader = '\0';
////
////        // Thêm header cho từng nhóm ký tự
////        for (Contact contact : contacts) {
////            char header = contact.getName().charAt(0);
////            if (header != lastHeader) {
////                items.add(String.valueOf(header));
////                lastHeader = header;
////            }
////            items.add(contact);
////        }
//        mContactAdapter.setItems(contacts);

    }

    @SuppressLint("CheckResult")
    private void findUserAnonymously(String phone) {
        List<Object> items = new ArrayList<>();
        items.add(String.valueOf("Kết quả tìm kiếm"));

        mAccountService.getWalletAccountByPhone(phone)
                .subscribe(response -> {
                    AccountDto account = response.getResult();
                    items.add(account);
                    mContactAdapter.setItems(items);
                }, throwable -> {

                });
    }
}
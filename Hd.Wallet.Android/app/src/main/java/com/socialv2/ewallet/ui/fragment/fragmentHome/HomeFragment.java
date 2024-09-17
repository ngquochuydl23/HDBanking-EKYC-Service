package com.socialv2.ewallet.ui.fragment.fragmentHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.ui.WelcomeActivity;
import com.socialv2.ewallet.ui.fragment.fragmentQR.ScanQRFragment;
import com.socialv2.ewallet.ui.mainHome.DepositMoneyActivity;
import com.socialv2.ewallet.ui.mainHome.QRPaymentActivity;
import com.socialv2.ewallet.ui.mainHome.TransactionNotificationActivity;
import com.socialv2.ewallet.ui.mainHome.TransferMoneyActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class HomeFragment extends Fragment {

    private View mView;
    private ImageView ivNotification, ivAvataUser;
    private TextView tvAvailableBalance;
    private LinearLayout viewDepositMoney, viewTransferMoney, viewScanQR, viewTransactionHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        ivNotification = mView.findViewById(R.id.ivNotification);
        ivAvataUser = mView.findViewById(R.id.ivAvataUser);
        tvAvailableBalance = mView.findViewById(R.id.tvAvailableBalance);
        viewDepositMoney = mView.findViewById(R.id.viewDepositMoney);
        viewTransferMoney = mView.findViewById(R.id.viewTransferMoney);
        viewScanQR = mView.findViewById(R.id.viewScanQR);
        viewTransactionHistory = mView.findViewById(R.id.viewTransactionHistory); // Fixed ID

        ivNotification.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getActivity(), TransactionNotificationActivity.class);
        });

        ivAvataUser.setOnClickListener(view -> {

        });

        viewDepositMoney.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getActivity(), DepositMoneyActivity.class);
        });

        viewTransferMoney.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getActivity(), TransferMoneyActivity.class);
        });

        viewScanQR.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getActivity(), QRPaymentActivity.class);
        });

        viewTransactionHistory.setOnClickListener(view -> {

        });

        return mView;
    }
}
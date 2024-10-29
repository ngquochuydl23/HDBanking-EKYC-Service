package com.socialv2.ewallet.ui.main.transactionTab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.transactions.TransactionAccountBankDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.ui.main.homeTab.RecentlyTransferDestAdapter;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.DateFormatter;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class TransactionAdapter extends BaseAdapter<TransactionDto> {

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionAdapter.TransactionViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, TransactionDto transaction) {
        TransactionAdapter.TransactionViewHolder itemView = (TransactionAdapter.TransactionViewHolder) viewHolder;

        itemView.mTransactionDateTextView.setText(DateFormatter.formatToVietnameseDateTime(transaction.getTransactionDate()));

        if (transaction.getTransactionStatus().equals("Completed")) {
            itemView.mTransactionStatusTextView.setText("Thành công");
        }

        if (transaction.getTransactionType().equals("Transfer")) {
            TransactionAccountBankDto destAccountBank = transaction.getDestAccount();

            if (transaction.isBankingTransfer()) {
                itemView.mTitleTextView.setText(String.format(
                        "Chuyển tiền/Thanh toán đến %s (%s)",
                        destAccountBank.getOwnerName(),
                        destAccountBank.getShortName()
                ));


            } else {
                itemView.mTitleTextView.setText(String.format("Chuyển tiền (%s)", destAccountBank.getOwnerName()));
            }
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private AvatarView mAvatarView;
        private TextView mTransactionDateTextView;
        private TextView mTransactionStatusTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            mTransactionDateTextView = itemView.findViewById(R.id.transactionDateTextView);
            mTransactionStatusTextView = itemView.findViewById(R.id.transactionStatusTextView);
            mAvatarView = itemView.findViewById(R.id.avatarView);
        }
    }
}
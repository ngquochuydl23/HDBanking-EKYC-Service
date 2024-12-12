package com.socialv2.ewallet.ui.main.transactionTab;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.transactions.TransactionAccountBankDto;
import com.socialv2.ewallet.dtos.transactions.TransactionDto;
import com.socialv2.ewallet.dtos.transactions.TransactionMonthSectionDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.singleton.UserSingleton;
import com.socialv2.ewallet.ui.transfer.internalTransfer.ContactAdapter;
import com.socialv2.ewallet.utils.DateFormatter;
import com.socialv2.ewallet.utils.NavigateUtil;
import com.socialv2.ewallet.utils.VietnameseConcurrency;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<TransactionDto> transactions;

    private boolean isContact;

    public TransactionAdapter() {
        transactions = new ArrayList<>();
        this.isContact = false;
        notifyDataSetChanged();
    }

    public TransactionAdapter(boolean isContact) {
        transactions = new ArrayList<>();
        this.isContact = isContact;
        notifyDataSetChanged();
    }

    public void setItems(List<TransactionDto> data)  {
        this.transactions = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction_month_section, parent, false);
            return new ContactAdapter.SectionAlphaViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction, parent, false);
            if (isContact) {

                return new ContactAdapter.ContactViewHolder(view);
            }

            return new TransactionAdapter.TransactionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserDto loggingUser = UserSingleton
                .getInstance()
                .getData()
                .getValue();
        Context context = holder.itemView.getContext();
        if (holder instanceof TransactionAdapter.TransactionMonthSectionViewHolder) {

            TransactionMonthSectionViewHolder itemViewHolder = (TransactionMonthSectionViewHolder) holder;


        } else {
            TransactionDto transaction = (TransactionDto) transactions.get(position);
            TransactionAdapter.TransactionViewHolder itemView = (TransactionAdapter.TransactionViewHolder) holder;

            itemView.mTransactionDateTextView.setText(DateFormatter.formatToVietnameseDateTime(transaction.getTransactionDate()));
            itemView.mTransactionAmountTextView.setText(VietnameseConcurrency.format(transaction.getAmount()));


            if (loggingUser.getId().equals(transaction.getSenderUserId())) {
                itemView.mTransactionAmountTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
                itemView.mTransactionAmountTextView.setText("-" + VietnameseConcurrency.format(transaction.getAmount()));
            } else if (loggingUser.getId().equals(transaction.getReceiverUserId())) {

                itemView.mTransactionAmountTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                itemView.mTransactionAmountTextView.setText("+" + VietnameseConcurrency.format(transaction.getAmount()));
            }

            if (transaction.getTransactionStatus().equals("Completed")) {
                itemView.mTransactionStatusTextView.setText("Thành công");
            }

            if (transaction.getTransactionType().equals("Transfer")) {
                TransactionAccountBankDto destAccountBank = transaction.getDestAccount();

                if (transaction.isBankingTransfer()) {
                    itemView.mTitleTextView.setText(String.format(
                            "Chuyển tiền/Thanh toán đến %s (%s)",
                            destAccountBank.getOwnerName(),
                            destAccountBank.getBankName()
                    ));


                } else {
                    itemView.mTitleTextView.setText(String.format("Chuyển tiền (%s)", destAccountBank.getOwnerName()));
                }
            }
            itemView.mContainerLayout.setOnClickListener(view -> {
                NavigateUtil.navigateTo(itemView.itemView.getContext(), TransactionDetailActivity.class);
            });
        }
    }
    @Override
    public int getItemCount() {
        return transactions.size();
    }


    public class TransactionMonthSectionViewHolder extends RecyclerView.ViewHolder {
        private TextView mDateTimeTextView;
        private TextView mTotalInOutTextView;

        public TransactionMonthSectionViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            mTotalInOutTextView = itemView.findViewById(R.id.totalInOutTextView);
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private AvatarView mAvatarView;
        private TextView mTransactionDateTextView;
        private TextView mTransactionStatusTextView;
        private TextView mTransactionAmountTextView;
        private View mContainerLayout;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            mTransactionDateTextView = itemView.findViewById(R.id.transactionDateTextView);
            mTransactionStatusTextView = itemView.findViewById(R.id.transactionStatusTextView);
            mAvatarView = itemView.findViewById(R.id.avatarView);
            mTransactionAmountTextView = itemView.findViewById(R.id.transactionAmountTextView);
        }
    }
}
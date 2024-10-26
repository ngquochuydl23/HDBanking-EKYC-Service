package com.socialv2.ewallet.ui.transfer.bankTransfer;

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
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class SelectBankAdapter extends BaseAdapter<BankDto> {
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_select_bank, parent, false);
        return new SelectBankAdapter.SelectBankViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, BankDto bank) {
        SelectBankAdapter.SelectBankViewHolder itemView = (SelectBankAdapter.SelectBankViewHolder) viewHolder;

        FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(bank.getLogoApp()));
        itemView.mShortNameTextView.setText(bank.getShortName());
        itemView.mNameTextView.setText(bank.getName());

        itemView.mContainerLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), FindDestBankAccountActivity.class);
            intent.putExtra("Bin", bank.getBin());
            intent.putExtra("ShortName", bank.getShortName());
            getContext().startActivity(intent);
        });
    }

    public class SelectBankViewHolder extends RecyclerView.ViewHolder {

        private View mContainerLayout;
        private AvatarView mAvatarView;
        private TextView mNameTextView;
        private TextView mShortNameTextView;

        public SelectBankViewHolder(@NonNull View itemView) {
            super(itemView);

            mContainerLayout = itemView.findViewById(R.id.containerLayout);
            mAvatarView = itemView.findViewById(R.id.avatarView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            mShortNameTextView = itemView.findViewById(R.id.shortNameTextView);
        }
    }
}
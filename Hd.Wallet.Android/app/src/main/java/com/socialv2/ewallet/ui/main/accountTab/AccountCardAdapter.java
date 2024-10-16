package com.socialv2.ewallet.ui.main.accountTab;

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
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class AccountCardAdapter extends BaseAdapter<AccountDto> {


    public AccountCardAdapter() {
        super();
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_account_card, parent, false);
        return new AccountCardAdapter.AccountCardViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, AccountDto account) {
        AccountCardViewHolder itemView = (AccountCardViewHolder) viewHolder;

        String logo = account
                .getAccountBank()
                .getLogoUrl();

        if (!logo.isEmpty()) {
            FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(logo));
        }

        itemView.mBankFullNameTextView.setText(account.getAccountBank().getBankFullName());
    }

    public class AccountCardViewHolder extends RecyclerView.ViewHolder {

        private AvatarView mAvatarView;
        private TextView mBankFullNameTextView;

        public AccountCardViewHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarView = itemView.findViewById(R.id.avatarView);
            mBankFullNameTextView = itemView.findViewById(R.id.bankFullNameTextView);
        }
    }
}

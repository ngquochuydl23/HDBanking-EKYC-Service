package com.socialv2.ewallet.ui.main.accountTab;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.ui.accountBank.DetailAccountBankActivity;
import com.socialv2.ewallet.ui.addCardOrAccount.AddLinkingBankActivity;
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

        itemView.mAvatarView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.foregroundColor)));

        if (!account.getBankLinking()) {
            itemView.mAvatarView.setImageDrawable(getContext().getDrawable(R.drawable.image_hd_logo));
        } else {
            FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(logo));
        }

        itemView.mContainerLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), DetailAccountBankActivity.class);
            intent.putExtra("AccountId", account.getId());
            getContext().startActivity(intent);
        });

        itemView.mBankFullNameTextView.setText(account.getAccountBank().getBankFullName());
    }

    public class AccountCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAvatarView;
        private TextView mBankFullNameTextView;
        private View mContainerLayout;

        public AccountCardViewHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarView = itemView.findViewById(R.id.avatarView);
            mBankFullNameTextView = itemView.findViewById(R.id.bankFullNameTextView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}

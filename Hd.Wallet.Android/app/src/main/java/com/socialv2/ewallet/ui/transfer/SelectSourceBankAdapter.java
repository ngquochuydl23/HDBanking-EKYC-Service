package com.socialv2.ewallet.ui.transfer;

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
import com.socialv2.ewallet.ui.accountBank.DetailAccountBankActivity;
import com.socialv2.ewallet.ui.main.accountTab.AccountCardAdapter;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class SelectSourceBankAdapter  extends BaseAdapter<AccountDto> {

    private int selectedPosition;
    private OnSelectSourceBank onSelectSourceBankListener;

    public SelectSourceBankAdapter() {
        super();
        selectedPosition = 0;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_select_source_account, parent, false);
        return new SelectSourceBankAdapter.SelectSourceBankViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, AccountDto account, int position) {
        SelectSourceBankAdapter.SelectSourceBankViewHolder itemView = (SelectSourceBankAdapter.SelectSourceBankViewHolder) viewHolder;

        String logo = account
                .getAccountBank()
                .getLogoUrl();

        if (!logo.isEmpty()) {
            FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(logo));
        }

        itemView.mContainerLayout.setOnClickListener(view -> {
            if (onSelectSourceBankListener != null) {
                onSelectSourceBankListener.onSelect(account);
            }
        });

        itemView.mBankFullNameTextView.setText(account.getAccountBank().getBankFullName());
    }

    private void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public interface OnSelectSourceBank {
        void onSelect(AccountDto account);
    }

    public void setOnSelectSourceBankListener(OnSelectSourceBank onSelectSourceBankListener) {
        this.onSelectSourceBankListener = onSelectSourceBankListener;
    }

    public class SelectSourceBankViewHolder extends RecyclerView.ViewHolder {

        private AvatarView mAvatarView;
        private TextView mBankFullNameTextView;
        private View mContainerLayout;

        public SelectSourceBankViewHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarView = itemView.findViewById(R.id.avatarView);
            mBankFullNameTextView = itemView.findViewById(R.id.bankFullNameTextView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}
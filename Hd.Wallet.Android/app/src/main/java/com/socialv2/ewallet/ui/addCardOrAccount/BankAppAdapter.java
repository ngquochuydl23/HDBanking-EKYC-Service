package com.socialv2.ewallet.ui.addCardOrAccount;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.NavigateUtil;

public class BankAppAdapter extends BaseAdapter<BankDto> {
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_bank_app, parent, false);
        return new BankAppAdapter.BankAppViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, BankDto bank) {
        BankAppViewHolder itemView = (BankAppViewHolder) viewHolder;

        FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(bank.getLogoApp()));
        itemView.mBankNameTextView.setText(bank.getShortName());
        itemView.mLinkAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddLinkingBankActivity.class);
            intent.putExtra("Bin", bank.getBin());

            getContext().startActivity(intent);
        });
    }

    public class BankAppViewHolder extends RecyclerView.ViewHolder {

        private AvatarView mAvatarView;
        private TextView mBankNameTextView;
        private Button mLinkAccountButton;

        public BankAppViewHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarView = itemView.findViewById(R.id.avatarView);
            mBankNameTextView = itemView.findViewById(R.id.bankNameTextView);
            mLinkAccountButton = itemView.findViewById(R.id.linkAccountButton);
        }
    }
}

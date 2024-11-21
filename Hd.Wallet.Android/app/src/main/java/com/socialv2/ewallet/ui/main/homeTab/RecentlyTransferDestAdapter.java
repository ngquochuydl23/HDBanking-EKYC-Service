package com.socialv2.ewallet.ui.main.homeTab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.CitizenAccountBankDto;
import com.socialv2.ewallet.dtos.accounts.AccountBankDto;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.banks.BankDto;
import com.socialv2.ewallet.dtos.transactions.RecentlyDestinationDto;
import com.socialv2.ewallet.https.api.bankHttp.BankingResourceHttpImpl;
import com.socialv2.ewallet.https.api.bankHttp.IBankingResourceService;
import com.socialv2.ewallet.s3.S3Service;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class RecentlyTransferDestAdapter extends BaseAdapter<RecentlyDestinationDto> {

    private boolean isHorizontalView;
    private IBankingResourceService mBankingResourceService;

    public RecentlyTransferDestAdapter() {
        super();

        isHorizontalView = false;
    }

    public RecentlyTransferDestAdapter(boolean isHorizontalView) {
        super();
        this.isHorizontalView = isHorizontalView;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mBankingResourceService = new BankingResourceHttpImpl(getContext());
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(isHorizontalView ? R.layout.item_recently_transfer_dest_horizontal : R.layout.item_recently_transfer_dest, parent, false);

        return new ContactRecentlyViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, RecentlyDestinationDto destination) {
        ContactRecentlyViewHolder itemView = (ContactRecentlyViewHolder) viewHolder;

        String bin = destination.getBin();
        String accountBankNo = destination.getAccountNo();
        String ownerName = destination.getOwnerName();
        String bankName = destination.getBankName();

        boolean isTransferBanking = !destination
                .getBin()
                .equals("999999.0");

        itemView.mContainerLayout.setOnClickListener(view -> {
            if (isTransferBanking) {

                CitizenAccountBankDto citizenAccountBank = new CitizenAccountBankDto();
                citizenAccountBank.setBin(bin);
                citizenAccountBank.setAccountNo(accountBankNo);
                citizenAccountBank.setOwnerName(ownerName);
                citizenAccountBank.setBankName(bankName);
                citizenAccountBank.setBank(new BankDto(destination.getLogoUrl()));
                String json = new Gson()
                        .toJson(citizenAccountBank);

                Intent intent = new Intent(getContext(), TransferMoneyActivity.class);

                intent.putExtra("Type", "BankTransfer");
                intent.putExtra("CitizenAccount", json);

                getContext().startActivity(intent);
            } else {


                AccountDto account = new AccountDto();
                AccountBankDto accountBank = new AccountBankDto();
                accountBank.setBankOwnerName(ownerName);
                accountBank.setBankAccountId(accountBankNo);
                accountBank.setBankFullName(destination.getBankFullName());
                account.setAccountBank(accountBank);

                String json = new Gson().toJson(account);

                Intent intent = new Intent(getContext(), TransferMoneyActivity.class);
                intent.putExtra("Type", "InternalTransfer");
                intent.putExtra("TransferTo", json);
                getContext().startActivity(intent);
            }
        });


        if (isHorizontalView) {
            FetchImageUrl.read(itemView.mAvatarView, S3Service.getUrl(destination.getUserAvatar()));
            if (isTransferBanking) {

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
                int margin10dp = DpToPx.convert(getContext(), 10);
                layoutParams.setMargins(margin10dp, margin10dp, margin10dp, margin10dp);
                itemView.mAvatarView.setLayoutParams(layoutParams);
                itemView.mAvatarView.setLayoutParams(layoutParams);

                itemView.mOwnerNameTextView.setText(destination.getOwnerName());

                FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(destination.getLogoUrl()));
                itemView.mIsBankLinkingView.setVisibility(View.VISIBLE);

            } else {

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                itemView.mAvatarView.setLayoutParams(layoutParams);

                itemView.mIsBankLinkingView.setVisibility(View.GONE);
                itemView.mOwnerNameTextView.setText(destination.getOwnerName());
                FetchImageUrl.read(itemView.mAvatarView, S3Service.getUrl(destination.getUserAvatar()));
            }

        } else {

            if (isTransferBanking) {

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
                int margin10dp = DpToPx.convert(getContext(), 10);
                layoutParams.setMargins(margin10dp, margin10dp, margin10dp, margin10dp);
                itemView.mAvatarView.setLayoutParams(layoutParams);


                itemView.mAvatarView.setLayoutParams(layoutParams);
                itemView.mSrcAccountNoBankTextView.setText(destination.getAccountNo());
                itemView.mOwnerNameTextView.setText(destination.getOwnerName());

                FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo(destination.getLogoUrl()));
                itemView.mIsBankLinkingView.setVisibility(View.VISIBLE);

            } else {

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                itemView.mAvatarView.setLayoutParams(layoutParams);

                itemView.mIsBankLinkingView.setVisibility(View.GONE);
                itemView.mSrcAccountNoBankTextView.setText(destination.getAccountNo());
                itemView.mOwnerNameTextView.setText(destination.getOwnerName());
                FetchImageUrl.read(itemView.mAvatarView, S3Service.getUrl(destination.getUserAvatar()));
            }
        }
    }

    public class ContactRecentlyViewHolder extends RecyclerView.ViewHolder {

        private View mContainerLayout;
        private AvatarView mAvatarView;
        private TextView mSrcAccountNoBankTextView;
        private TextView mOwnerNameTextView;
        private View mIsBankLinkingView;

        public ContactRecentlyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSrcAccountNoBankTextView = itemView.findViewById(R.id.srcAccountNoBankTextView);
            mAvatarView = itemView.findViewById(R.id.avatarView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
            mOwnerNameTextView = itemView.findViewById(R.id.ownerNameTextView);
            mIsBankLinkingView = itemView.findViewById(R.id.isBankLinkingView);
        }
    }
}

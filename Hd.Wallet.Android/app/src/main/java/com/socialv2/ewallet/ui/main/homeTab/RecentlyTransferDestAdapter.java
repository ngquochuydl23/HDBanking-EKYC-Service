package com.socialv2.ewallet.ui.main.homeTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.transactions.RecentlyDestinationDto;
import com.socialv2.ewallet.s3.S3Service;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.FetchImageUrl;

public class RecentlyTransferDestAdapter extends BaseAdapter<RecentlyDestinationDto> {

    private boolean isHorizontalView;

    public RecentlyTransferDestAdapter() {
        super();

        isHorizontalView = false;
    }

    public RecentlyTransferDestAdapter(boolean isHorizontalView) {
        super();
        this.isHorizontalView = isHorizontalView;
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



        if (isHorizontalView) {
            ContactRecentlyViewHolder itemView = (ContactRecentlyViewHolder) viewHolder;

            FetchImageUrl.read(itemView.mAvatarView, S3Service.getUrl(destination.getUserAvatar()));

            if (!destination.getBin().equals("999999.0")) {

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
            ContactRecentlyViewHolder itemView = (ContactRecentlyViewHolder) viewHolder;
            if (!destination.getBin().equals("999999.0")) {

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

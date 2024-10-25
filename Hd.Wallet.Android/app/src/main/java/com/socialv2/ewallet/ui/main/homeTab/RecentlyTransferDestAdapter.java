package com.socialv2.ewallet.ui.main.homeTab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.BankingResourceLogo;
import com.socialv2.ewallet.utils.DpToPx;
import com.socialv2.ewallet.utils.FetchImageUrl;
import com.socialv2.ewallet.utils.NavigateUtil;

public class RecentlyTransferDestAdapter extends BaseAdapter<AccountDto> {

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
                .inflate(isHorizontalView ? R.layout.item_recently_transfer_dest : R.layout.item_contact_recently, parent, false);
        return new ContactRecentlyViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, AccountDto account) {
        ContactRecentlyViewHolder itemView = (ContactRecentlyViewHolder) viewHolder;

        itemView.mContainerLayout.setOnClickListener(view -> {
            String json = "";
            Intent intent = new Intent(getContext(), TransferMoneyActivity.class);
            intent.putExtra("AccountJsonResult", json);
            getContext().startActivity(intent);
        });

        if (account.getBankLinking()) {

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
            int margin10dp = DpToPx.convert(getContext(), 10);
            layoutParams.setMargins(margin10dp, margin10dp, margin10dp, margin10dp);
            itemView.mAvatarView.setLayoutParams(layoutParams);


            itemView.mAvatarView.setLayoutParams(layoutParams);
            itemView.mSrcAccountNoBankTextView.setText("190 319 0512 3456");
            itemView.mOwnerNameTextView.setText("NGUYEN QUOC HUY");
            FetchImageUrl.read(itemView.mAvatarView, BankingResourceLogo.getLogo("Uploads/vcb.png"));
        } else {

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.mAvatarView.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            itemView.mAvatarView.setLayoutParams(layoutParams);
            itemView.mSrcAccountNoBankTextView.setText("0328532043");
            itemView.mOwnerNameTextView.setText("Hồ Thị Thu Trầm");
            FetchImageUrl.read(itemView.mAvatarView, "https://avatars.githubusercontent.com/u/36536025?v=4");
        }
    }

    public class ContactRecentlyViewHolder extends RecyclerView.ViewHolder {

        private View mContainerLayout;
        private AvatarView mAvatarView;
        private TextView mSrcAccountNoBankTextView;
        private TextView mOwnerNameTextView;

        public ContactRecentlyViewHolder(@NonNull View itemView) {
            super(itemView);
            mSrcAccountNoBankTextView = itemView.findViewById(R.id.srcAccountNoBankTextView);
            mAvatarView = itemView.findViewById(R.id.avatarView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
            mOwnerNameTextView = itemView.findViewById(R.id.ownerNameTextView);
        }
    }
}

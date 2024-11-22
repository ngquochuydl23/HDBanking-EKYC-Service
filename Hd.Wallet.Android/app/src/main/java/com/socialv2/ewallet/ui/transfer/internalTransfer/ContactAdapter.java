package com.socialv2.ewallet.ui.transfer.internalTransfer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.users.PublicUserDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.s3.S3Service;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> list;
    private Context mContext;
    public ContactAdapter() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setItems(List<Object> list)  {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mContext = recyclerView.getContext();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof String) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact_sort_alpha, parent, false);
            return new SectionAlphaViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false);
            return new ContactViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectionAlphaViewHolder) {
            ((SectionAlphaViewHolder) holder).mSectionAlphaTextView.setText((String) list.get(position));
        } else {

            if (list.get(position) instanceof AccountDto) {
                AccountDto account = (AccountDto) list.get(position);
                PublicUserDto user = account.getUser();


                ContactViewHolder itemHolder = (ContactViewHolder) holder;

                itemHolder.mImgMore.setVisibility(View.GONE);
                itemHolder.mFullNameTextView.setText(user.getFullName());
                itemHolder.mPhoneNumberTextView.setText(user.getPhoneNumber());
                itemHolder.mAvatarImageView.setSrc(S3Service.getUrl(user.getAvatar()));

                itemHolder.mContainerLayout.setOnClickListener(view -> {
                    String json = new Gson().toJson(account);

                    Intent intent = new Intent(mContext, TransferMoneyActivity.class);
                    intent.putExtra("Type", "InternalTransfer");
                    intent.putExtra("TransferTo", json);
                    mContext.startActivity(intent);
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SectionAlphaViewHolder extends RecyclerView.ViewHolder {
        private TextView mSectionAlphaTextView;

        public SectionAlphaViewHolder(@NonNull View itemView) {
            super(itemView);
            mSectionAlphaTextView = itemView.findViewById(R.id.sectionAlphaTextView);
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView mFullNameTextView, mPhoneNumberTextView;
        private AvatarView mAvatarImageView;
        private View mContainerLayout;
        private View mImgMore;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatarImageView);
            mFullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            mPhoneNumberTextView = itemView.findViewById(R.id.phoneNumberTextView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
            mImgMore = itemView.findViewById(R.id.imgMore);
        }
    }

    public interface OnItemContactClick {
        void onClick(String accountId);
    }
}

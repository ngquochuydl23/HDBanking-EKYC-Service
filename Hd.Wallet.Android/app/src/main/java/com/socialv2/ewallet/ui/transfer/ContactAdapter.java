package com.socialv2.ewallet.ui.transfer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.R;
import com.socialv2.ewallet.components.AvatarView;
import com.socialv2.ewallet.dtos.accounts.AccountDto;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> contactList;

    public ContactAdapter() {
        this.contactList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setItems(List<Object> contactList)  {
        this.contactList = contactList;
    }

    @Override
    public int getItemViewType(int position) {
        if (contactList.get(position) instanceof String) {
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
            ((SectionAlphaViewHolder) holder).mSectionAlphaTextView.setText((String) contactList.get(position));
        } else {
            //Contact contact = (Contact) contactList.get(position);
            ContactViewHolder itemHolder = (ContactViewHolder) holder;
//            itemHolder.mPhoneNumberTextView.setText("Như Ý");
//            itemHolder.txtPhone.setText(contact.getPhone());
//            itemHolder.imgAvatar.setImageResource(contact.getAvatarResource());
//
            itemHolder.mContainerLayout.setOnClickListener(view -> {

            });

        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
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

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarImageView = itemView.findViewById(R.id.avatarImageView);
            mFullNameTextView = itemView.findViewById(R.id.fullNameTextView);
            mPhoneNumberTextView = itemView.findViewById(R.id.phoneNumberTextView);
            mContainerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }

    public interface OnItemContactClick {
        void onClick(String accountId);
    }
}

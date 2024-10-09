package com.socialv2.ewallet.ui.main.homeTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.ui.contacts.ContactActivity;
import com.socialv2.ewallet.ui.transfer.TransferMoneyActivity;
import com.socialv2.ewallet.utils.NavigateUtil;

public class ContactRecentlyAdapter extends BaseAdapter<UserDto> {

    public ContactRecentlyAdapter() {
        super();
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_contact_recently, parent, false);
        return new ContactRecentlyViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, UserDto userDto) {
        ContactRecentlyViewHolder itemView = (ContactRecentlyViewHolder) viewHolder;

        itemView.mContainerLayout.setOnClickListener(view -> {
            NavigateUtil.navigateTo(getContext(), TransferMoneyActivity.class);
        });
    }

    public class ContactRecentlyViewHolder extends RecyclerView.ViewHolder {

        private View mContainerLayout;

        public ContactRecentlyViewHolder(@NonNull View itemView) {
            super(itemView);


            mContainerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}

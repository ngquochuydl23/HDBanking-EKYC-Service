package com.socialv2.ewallet.ui.main.accountTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.accounts.AccountDto;
import com.socialv2.ewallet.dtos.users.UserDto;
import com.socialv2.ewallet.ui.main.homeTab.ContactRecentlyAdapter;

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
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, AccountDto accountDto) {

    }

    public class AccountCardViewHolder extends RecyclerView.ViewHolder {


        public AccountCardViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}

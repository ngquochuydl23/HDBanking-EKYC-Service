package com.socialv2.ewallet.ui.main.moreTab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.MenuAppDto;
import com.socialv2.ewallet.utils.NavigateUtil;


public class MenuAppAdapter extends BaseAdapter<MenuAppDto> {


    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_menu_app, parent, false);
        return new MenuAppViewHolder(view);
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, MenuAppDto menuApp) {
        MenuAppViewHolder menuAppViewHolder = (MenuAppViewHolder) viewHolder;

        menuAppViewHolder.mTitleTextView.setText(menuApp.getTitle());
        menuAppViewHolder.mDescriptionTextView.setVisibility(menuApp.getDescription() == null ? View.GONE : View.VISIBLE);
        menuAppViewHolder.mDescriptionTextView.setText(menuApp.getDescription());
        menuAppViewHolder.mIconImageView.setImageResource(menuApp.getIconId());

        menuAppViewHolder.mMenuAppItemView.setOnClickListener(view -> {

            if (menuApp.getDestActivity() != null) {
                NavigateUtil.navigateTo(getContext(), menuApp.getDestActivity());
            }
        });
    }

    public class MenuAppViewHolder extends RecyclerView.ViewHolder {

        private View mMenuAppItemView;
        private ImageView mIconImageView;
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;

        public MenuAppViewHolder(@NonNull View itemView) {
            super(itemView);

            mMenuAppItemView = itemView.findViewById(R.id.menuAppItemView);
            mIconImageView = itemView.findViewById(R.id.iconImageView);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            mDescriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
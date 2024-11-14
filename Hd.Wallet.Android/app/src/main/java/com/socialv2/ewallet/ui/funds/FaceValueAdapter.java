package com.socialv2.ewallet.ui.funds;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.socialv2.ewallet.BaseAdapter;
import com.socialv2.ewallet.R;
import com.socialv2.ewallet.dtos.FaceValueDto;
import com.socialv2.ewallet.utils.VietnameseConcurrency;

public class FaceValueAdapter extends BaseAdapter<FaceValueDto> {

    private FaceValueViewHolder selectedItem;
    private FaceValueItemClickListener faceValueItemClickListener;

    public FaceValueAdapter() {

    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.item_face_value, parent, false);
        return new FaceValueAdapter.FaceValueViewHolder(view);
    }

    public void setOnChooseFaceValueClick(FaceValueItemClickListener listener) {
        this.faceValueItemClickListener = listener;
    }

    @Override
    protected void bind(@NonNull RecyclerView.ViewHolder viewHolder, FaceValueDto faceValue) {
        FaceValueAdapter.FaceValueViewHolder itemView = (FaceValueAdapter.FaceValueViewHolder) viewHolder;

        itemView.mValueTextView.setText(VietnameseConcurrency.formatWithoutSymbol(faceValue.getValue()));
        itemView.mCurrencyTextView.setText(faceValue.getCurrency());

        itemView.mContainerView.setOnClickListener(view -> {

            if (selectedItem != null) {
                selectedItem.mContainerView.setBackground(getContext()
                        .getDrawable(R.drawable.drawable_outline_r10));
            }

            itemView.mContainerView.setBackground(getContext()
                    .getDrawable(R.drawable.drawable_face_value_selected));

            selectedItem = itemView;

            if (faceValueItemClickListener != null) {
                faceValueItemClickListener.onChooseFaceValue(faceValue);
            }
        });
    }

    public class FaceValueViewHolder extends RecyclerView.ViewHolder {

        private TextView mValueTextView;
        private TextView mCurrencyTextView;
        private View mContainerView;


        public FaceValueViewHolder(@NonNull View itemView) {
            super(itemView);

            mContainerView = itemView.findViewById(R.id.containerView);
            mValueTextView = itemView.findViewById(R.id.valueTextView);
            mCurrencyTextView = itemView.findViewById(R.id.currencyTextView);
        }
    }

    @FunctionalInterface
    public interface FaceValueItemClickListener {
        void onChooseFaceValue(FaceValueDto faceValue);
    }
}

package com.socialv2.ewallet.ui.main.transactionTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.socialv2.ewallet.R;

public class ChatFragment extends Fragment {

    private View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview =inflater.inflate(R.layout.fragment_chat , container, false);
        return mview;
    }
}

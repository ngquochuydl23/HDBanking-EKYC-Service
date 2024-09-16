package com.socialv2.ewallet.ui.fragment.fragmentHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.socialv2.ewallet.R;

public class ProfileFragment extends Fragment {


    private View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview =inflater.inflate(R.layout.fragment_profile , container, false);
        return mview;
    }
}

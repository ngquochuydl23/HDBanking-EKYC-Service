package com.socialv2.ewallet.ui.home.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


import android.view.View;

import com.socialv2.ewallet.BaseFragment;
import com.socialv2.ewallet.R;


public class HomeFragment extends BaseFragment {

    private CardView cardviewTransfer , cardviewTransfer1 , cardviewScanQr , cardviewHistory ;



  public HomeFragment() {
      super(R.layout.fragment_home);
  }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
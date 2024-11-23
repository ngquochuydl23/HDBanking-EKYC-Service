package com.socialv2.ewallet.ui.privacyAndSecurity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.socialv2.ewallet.BaseActivity
import com.socialv2.ewallet.R
import com.socialv2.ewallet.utils.WindowUtils

class PrivacyAndSecurityActivity : BaseActivity(R.layout.activity_privacy_and_security) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView();
    }


    private fun initView() {
        WindowUtils.applyPadding(findViewById(R.id.main))
    }
}
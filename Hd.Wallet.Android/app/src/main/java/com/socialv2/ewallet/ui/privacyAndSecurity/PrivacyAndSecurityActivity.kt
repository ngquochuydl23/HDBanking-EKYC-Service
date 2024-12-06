package com.socialv2.ewallet.ui.privacyAndSecurity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.socialv2.ewallet.BaseActivity
import com.socialv2.ewallet.R
import com.socialv2.ewallet.utils.NavigateUtil
import com.socialv2.ewallet.utils.WindowUtils

class PrivacyAndSecurityActivity : BaseActivity(R.layout.activity_privacy_and_security) {

    private lateinit var mUpdatePasswordButton: View
    private lateinit var mUpdatePinButton: View
    private lateinit var mDeactiveButton: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUpdatePasswordButton = findViewById(R.id.updatePasswordButton)
        mUpdatePinButton = findViewById(R.id.updatePinButton)
        mDeactiveButton = findViewById(R.id.deactiveAccountButton)
        initView();
    }


    private fun initView() {
        WindowUtils.applyPadding(findViewById(R.id.main))


        mUpdatePinButton.setOnClickListener {
            NavigateUtil.navigateTo(this, UpdatePinActivity::class.java)
        }

        mUpdatePasswordButton.setOnClickListener {
            NavigateUtil.navigateTo(this, UpdatePasswordActivity::class.java)
        }

        mDeactiveButton.setOnClickListener {

        }
    }
}
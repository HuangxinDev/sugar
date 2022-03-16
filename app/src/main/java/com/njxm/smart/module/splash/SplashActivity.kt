/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.module.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.njxm.smart.global.KeyConstant
import com.njxm.smart.module.login.LoginFragment
import com.njxm.smart.ui.activities.BaseActivity
import com.njxm.smart.ui.activities.main.MainActivity
import com.njxm.smart.utils.StatusBarUtil
import com.ntxm.smart.R
import com.ntxm.smart.databinding.ActivitySplashLayoutBinding
import com.sugar.android.common.utils.ActivityUtils
import com.sugar.android.common.utils.FragmentUtils
import com.sugar.android.common.utils.SPUtils
import com.sugar.android.common.utils.StringUtils

/**
 * 引导页
 */
class SplashActivity : BaseActivity() {
    private lateinit var layoutBinding: ActivitySplashLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucentStatus(this)
        super.onCreate(savedInstanceState)
        layoutBinding = ActivitySplashLayoutBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)

        val splashFragment = SplashFragment()
        onBackPressedDispatcher.addCallback(splashFragment, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                layoutBinding.splashContainer.visibility = View.GONE;
                FragmentUtils.removeFragment(supportFragmentManager, splashFragment)
                initLoginFragment()
            }
        })
        FragmentUtils.showFragment(supportFragmentManager, R.id.splash_container, splashFragment)
    }

    private fun initLoginFragment() {
        if (isTokenPresent()) {
            startMainActivityBeforeFinishSelf()
            return
        }
        val loginFragment = LoginFragment()
        onBackPressedDispatcher.addCallback(loginFragment, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startMainActivityBeforeFinishSelf()
            }
        })
        FragmentUtils.showFragment(supportFragmentManager, R.id.login_container, loginFragment)
    }

    private fun startMainActivityBeforeFinishSelf() {
        val intent = Intent(this, MainActivity::class.java)
        ActivityUtils.startActivity(this, intent)
        finish()
    }

    private fun isTokenPresent(): Boolean {
        return StringUtils.isNotEmpty(SPUtils.getStringValue(KeyConstant.KEY_USER_TOKEN))
    }
}
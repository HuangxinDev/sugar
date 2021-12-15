/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.njxm.smart.module.splash

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.njxm.smart.base.BaseActivity
import com.njxm.smart.module.login.LoginFragment
import com.ntxm.smart.R
import com.ntxm.smart.databinding.ActivitySplashLayoutBinding

/**
 * 引导页
 */
class SplashActivity : BaseActivity() {

    private lateinit var viewBinding: ActivitySplashLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashLayoutBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val splashFragment = SplashFragment()
        onBackPressedDispatcher.addCallback(splashFragment, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                viewBinding.root.removeView(viewBinding.splashContainer)
                viewBinding.splashContainer.visibility = View.GONE;
                supportFragmentManager.beginTransaction().remove(splashFragment).commitAllowingStateLoss()
                initLoginFragment()
            }
        })
        supportFragmentManager.beginTransaction().replace(R.id.splash_container, splashFragment)
                .commitAllowingStateLoss()
    }

    private val loginFragment = LoginFragment()
    private fun initLoginFragment() {

        supportFragmentManager.beginTransaction().replace(R.id.login_container, loginFragment).commitAllowingStateLoss()
    }
}
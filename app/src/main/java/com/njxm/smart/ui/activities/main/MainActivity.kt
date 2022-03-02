/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.njxm.smart.ui.activities.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.widget.RadioGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.njxm.smart.ui.activities.BaseActivity
import com.njxm.smart.ui.activities.fragments.AttendanceFragment
import com.njxm.smart.ui.activities.fragments.MessagesFragment
import com.njxm.smart.ui.activities.fragments.PersonalFragment
import com.njxm.smart.ui.activities.fragments.WorkCenterFragment
import com.njxm.smart.utils.StatusBarUtil
import com.ntxm.smart.R
import com.ntxm.smart.databinding.ActivityMainBinding
import com.sugar.android.common.utils.FragmentUtils
import com.sugar.android.common.utils.Logger

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
class MainActivity : BaseActivity() {
    private lateinit var layoutBinding: ActivityMainBinding

    private val fragmentMap: HashMap<Int, Fragment> = java.util.HashMap()

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        StatusBarUtil.setTranslucentStatus(this)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)

        layoutBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)
        initFragments()
        initViews()
    }

    private fun initFragments() {
        layoutBinding.radioGroup.forEach {
            when (it.id) {
                R.id.first_btn -> {
                    fragmentMap[it.id] = AttendanceFragment()
                }
                R.id.btn2 -> {
                    fragmentMap[it.id] = WorkCenterFragment()
                }
                R.id.btn3 -> {
                    fragmentMap[it.id] = MessagesFragment()
                }
                R.id.btn4 -> {
                    fragmentMap[it.id] = PersonalFragment()
                }
                else -> {
                    Logger.e(TAG, "not impl main tab.")
                }
            }
        }
        currentFragment = fragmentMap[R.id.first_btn]
        FragmentUtils.showFragment(supportFragmentManager, R.id.fragment_container, currentFragment)
    }

    @SuppressLint("NonConstantResourceId")
    private fun initViews() {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
            Logger.i(TAG, "radio group select checkId: $checkedId")
            val fragment = fragmentMap[checkedId]
            if (fragment != null) {
                FragmentUtils.showFragment(supportFragmentManager, R.id.fragment_container, currentFragment, fragment)
                currentFragment = fragment
            } else {
                Logger.e(TAG, "undefine fragment, can not jump it.")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
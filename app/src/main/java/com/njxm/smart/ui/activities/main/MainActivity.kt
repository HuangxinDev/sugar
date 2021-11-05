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
import android.widget.RadioGroup
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.njxm.smart.ui.activities.AppBaseActivity
import com.ntxm.smart.R
import java.util.*

/**
 * 主页 RadioGroup + Fragment 实现不可滑动的切换效果
 */
@Route(path = "/app/main")
class MainActivity : AppBaseActivity<MainView?, MainPresenter?>(), MainView {
    private val navTabs: MutableList<NavTab> = ArrayList()
    private var lastSelected = R.id.first_btn
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initNavTabs()
    }

    @SuppressLint("NonConstantResourceId")
    private fun initViews() {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            val actionId = getNavActionId(lastSelected, checkedId)
            lastSelected = checkedId
            if (actionId != INVALID_ACTION) {
                Navigation.findNavController(this, R.id.fragment_container).navigate(actionId)
            }
        }
    }

    /**
     * 添加页面和导航action的映射关系
     */
    private fun initNavTabs() {
        navTabs.add(NavTab(R.id.first_btn, R.id.btn2, R.id.action_attendanceFragment_to_workCenterFragment))
        navTabs.add(NavTab(R.id.first_btn, R.id.btn3, R.id.action_attendanceFragment_to_messageFragment))
        navTabs.add(NavTab(R.id.first_btn, R.id.btn4, R.id.action_attendanceFragment_to_personalFragment))
        navTabs.add(NavTab(R.id.btn2, R.id.first_btn, R.id.action_workCenterFragment_to_attendanceFragment))
        navTabs.add(NavTab(R.id.btn2, R.id.btn3, R.id.action_workCenterFragment_to_messageFragment))
        navTabs.add(NavTab(R.id.btn2, R.id.btn4, R.id.action_workCenterFragment_to_personalFragment))
        navTabs.add(NavTab(R.id.btn3, R.id.first_btn, R.id.action_messageFragment_to_attendanceFragment))
        navTabs.add(NavTab(R.id.btn3, R.id.btn2, R.id.action_messageFragment_to_workCenterFragment))
        navTabs.add(NavTab(R.id.btn3, R.id.btn4, R.id.action_messageFragment_to_personalFragment))
        navTabs.add(NavTab(R.id.btn4, R.id.first_btn, R.id.action_personalFragment_to_attendanceFragment))
        navTabs.add(NavTab(R.id.btn4, R.id.btn2, R.id.action_personalFragment_to_workCenterFragment))
        navTabs.add(NavTab(R.id.btn4, R.id.btn3, R.id.action_personalFragment_to_messageFragment))
    }

    override fun setContentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onResume() {
        super.onResume()
        MainPresenter.fetchCountryAddresses()
    }

    private fun getNavActionId(from: Int, to: Int): Int {
        for (navTab in navTabs) {
            if (navTab == NavTab(from, to, -1)) {
                return navTab.action
            }
        }
        return INVALID_ACTION
    }

    override fun newPresenter(): MainPresenter {
        return MainPresenter()
    }

    private class NavTab(var from: Int, var to: Int, var action: Int) {
        override fun equals(o: Any?): Boolean {
            if (this === o) {
                return true
            }
            if (o == null || this.javaClass != o.javaClass) {
                return false
            }
            val navTab = o as NavTab
            return from == navTab.from &&
                    to == navTab.to
        }

        override fun hashCode(): Int {
            return Objects.hash(from, to)
        }
    }

    companion object {
        private const val INVALID_ACTION = -1
    }
}
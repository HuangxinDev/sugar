/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.njxm.smart.activities.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.njxm.smart.activities.login.LoginActivity
import com.njxm.smart.ui.activities.BaseActivity
import com.njxm.smart.ui.fragments.BaseFragmentAdapter
import com.njxm.smart.ui.fragments.HomeFragment
import com.njxm.smart.view.DotsView
import com.ntxm.smart.R
import java.util.*

/**
 * 引导页
 */
class SplashActivity : BaseActivity(), SplashView {
    // mdskds
    private val res = intArrayOf(R.mipmap.startup_one, R.mipmap.startup_three, R.mipmap.startup_two)

    /**
     * SP
     */
    private var mSharedPreferences: SharedPreferences? = null

    /**
     * 页面管理器
     */
    private var viewPager: ViewPager? = null

    /**
     * 体验按钮
     */
    private var expressBtn: TextView? = null

    /**
     * 页面指示器
     */
    private var pageIndicator: DotsView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashViewModel = SplashViewModel()
        splashViewModel.urls.observe(this, object : Observer<String?> {
            override fun onChanged(s: String?) {
                splashViewModel.urls.removeObserver(this)
            }
        })
        mSharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE)
        val isFirstLaunch = mSharedPreferences?.getBoolean(SP_COLUMN_FIRST_LAUNCH, true) ?: true
        if (!isFirstLaunch) {
            startLoginActivity()
            return
        }
        initView()
        pageIndicator?.setDotSize(res.size)
        pageIndicator?.setSelected(0)
        val mFragmentManager = this.supportFragmentManager
        val fragments: MutableList<Fragment> = ArrayList()
        for (resId in res) {
            fragments.add(HomeFragment(resId))
        }
        viewPager?.adapter = BaseFragmentAdapter(mFragmentManager, fragments)
        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                pageIndicator?.setSelected(position)
                expressBtn?.visibility = if (position == res.size - 1) View.VISIBLE else View.INVISIBLE
            }

            override fun onPageSelected(position: Int) {
                //
            }

            override fun onPageScrollStateChanged(state: Int) {
//
            }
        })
        expressBtn?.setOnClickListener { _: View? ->
            mSharedPreferences?.edit()?.putBoolean(SP_COLUMN_FIRST_LAUNCH, false)?.apply()
            startLoginActivity()
        }
    }

    private fun initView() {
        pageIndicator = super.findViewById(R.id.dots_view)
        expressBtn = super.findViewById(R.id.experience)
        viewPager = super.findViewById(R.id.home_view_pager)

        val arrayList = ArrayList<String>()
        arrayList.trimToSize()
    }

    override fun setContentLayoutId(): Int {
        return R.layout.activity_home
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    companion object {
        private const val SP_NAME = "user"
        private const val SP_COLUMN_FIRST_LAUNCH = "isFirst"
    }

    override fun onCallBack(images: List<String>) {
        // 拿到图片地址，则初始化ViewPager的Fragment
        viewPager?.apply {
            val myadapter = SplashPageAdapter(supportFragmentManager)
            myadapter.imageUrls = images
            this.adapter = myadapter
        }
    }
}
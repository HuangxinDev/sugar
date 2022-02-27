package com.njxm.smart.module.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.njxm.smart.base.BaseFragment
import com.njxm.smart.ui.fragments.BaseFragmentAdapter
import com.njxm.smart.ui.fragments.HomeFragment
import com.ntxm.smart.R
import com.ntxm.smart.databinding.FragmentSplashLayoutBinding
import com.sugar.android.common.utils.Logger
import com.sugar.android.common.utils.SPUtils
import com.sugar.android.common.utils.ViewUtils
import com.sugar.android.common.view.SafeOnClickListener

/**
 * 闪屏页处理
 * @author huangxin
 * @date 2021/11/5
 */
class SplashFragment : BaseFragment() {
    private var splashImages = listOf(R.mipmap.startup_one, R.mipmap.startup_three, R.mipmap.startup_two)

    private lateinit var layoutBinding: FragmentSplashLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding = FragmentSplashLayoutBinding.inflate(inflater)
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFirstLaunch = SPUtils.getBoolean(SP_COLUMN_FIRST_LAUNCH, true) ?: true
        Logger.i(TAG, "is first launch: $isFirstLaunch")
        if (!isFirstLaunch) {
            onBackPress()
            return
        }
        layoutBinding.dotsView.setDotSize(splashImages.size)
        layoutBinding.dotsView.setSelected(0)
        layoutBinding.homeViewPager.adapter = BaseFragmentAdapter(parentFragmentManager, getFragments())
        layoutBinding.homeViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                layoutBinding.dotsView.setSelected(position)
                ViewUtils.setVisibility(layoutBinding.experience, position == splashImages.size - 1)
            }

            override fun onPageSelected(position: Int) {
                Logger.i(TAG, "current select $position page.")
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        ViewUtils.setOnClickListener(layoutBinding.experience, object : SafeOnClickListener() {
            override fun onSafeClick(view: View?) {
                SPUtils.putBoolean(SP_COLUMN_FIRST_LAUNCH, false)
                onBackPress()
            }
        })
    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private fun getFragments(): MutableList<Fragment> {
        val fragments: MutableList<Fragment> = ArrayList()
        for (resId in splashImages) {
            fragments.add(HomeFragment(resId))
        }
        return fragments
    }

    companion object {
        private const val TAG = "SplashFragment"
        private const val SP_COLUMN_FIRST_LAUNCH = "isFirst"
    }
}
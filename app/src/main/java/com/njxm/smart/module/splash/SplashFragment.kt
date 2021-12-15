package com.njxm.smart.module.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.njxm.smart.base.BaseFragment
import com.njxm.smart.ui.fragments.BaseFragmentAdapter
import com.njxm.smart.ui.fragments.HomeFragment
import com.ntxm.smart.R
import com.ntxm.smart.databinding.FragmentSplashLayoutBinding
import java.util.*

/**
 * 闪屏页处理
 * @author huangxin
 * @date 2021/11/5
 */
class SplashFragment : BaseFragment() {
    private var splashImages = listOf(R.mipmap.startup_one, R.mipmap.startup_three, R.mipmap.startup_two)

    private lateinit var viewBinding: FragmentSplashLayoutBinding

    /**
     * SP
     */
    private var mSharedPreferences: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentSplashLayoutBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSharedPreferences = activity?.getSharedPreferences(SP_NAME, FragmentActivity.MODE_PRIVATE)
        val isFirstLaunch = mSharedPreferences?.getBoolean(SP_COLUMN_FIRST_LAUNCH, true) ?: true
//        if (!isFirstLaunch) {
//            startLoginActivity()
//            return
//        }
        initView()
        viewBinding.dotsView.setDotSize(splashImages.size)
        viewBinding.dotsView.setSelected(0)
        val mFragmentManager = this.parentFragmentManager
        val fragments: MutableList<Fragment> = ArrayList()
        for (resId in splashImages) {
            fragments.add(HomeFragment(resId))
        }
        viewBinding.homeViewPager.adapter = BaseFragmentAdapter(mFragmentManager, fragments)
        viewBinding.homeViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                viewBinding.dotsView.setSelected(position)
                viewBinding.experience.visibility =
                    if (position == splashImages.size - 1) View.VISIBLE else View.INVISIBLE
            }

            override fun onPageSelected(position: Int) {
                //
            }

            override fun onPageScrollStateChanged(state: Int) {
//
            }
        })
        viewBinding.experience.setOnClickListener { _: View? ->
            mSharedPreferences?.edit()?.putBoolean(SP_COLUMN_FIRST_LAUNCH, false)?.apply()
            startLoginActivity()
        }
    }

    private fun initView() {
        val arrayList = ArrayList<String>()
        arrayList.trimToSize()
    }

    private fun startLoginActivity() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {
        private const val SP_NAME = "user"
        private const val SP_COLUMN_FIRST_LAUNCH = "isFirst"
    }
}
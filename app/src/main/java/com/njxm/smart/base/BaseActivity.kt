package com.njxm.smart.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.FragmentActivity

/**
 * Activity基类
 * @author huangxin
 * @date 2021/11/5
 */
abstract class BaseActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.requestFeature(Window.FEATURE_NO_TITLE)
    }
}
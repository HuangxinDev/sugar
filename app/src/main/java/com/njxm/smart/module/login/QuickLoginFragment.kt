package com.njxm.smart.module.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.njxm.smart.utils.StringUtils
import com.ntxm.smart.databinding.FragmentQuickLoginBinding

/**
 * 快速登录碎片
 * @author huangxin
 * @date 2022/2/22
 */
class QuickLoginFragment : Fragment() {
    private lateinit var layoutBinding: FragmentQuickLoginBinding

    private lateinit var loginNum: String
    private lateinit var pictureCode: String
    private lateinit var dynamicCode: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutBinding = FragmentQuickLoginBinding.inflate(inflater)
        return layoutBinding.root
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s == null || StringUtils.isEmpty(s.toString())) {
                // TODO: 设置前景色
            }
        }

        override fun afterTextChanged(s: Editable?) {
            TODO("Not yet implemented")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            val viewModel = ViewModelProvider(it).get(LoginViewModel::class.java)
            viewModel.code?.observe(this) { TODO("Not yet implemented") }
        }
        layoutBinding.quickLoginUserNameEt.addTextChangedListener(textWatcher)
    }

    private fun login() {
        loginNum = layoutBinding.quickLoginUserNameEt.text.toString()
        if (StringUtils.isEmpty(loginNum)) {
            layoutBinding.quickLoginUserNameEt.error = "用户名不可为空"
            return
        }
        if (StringUtils.isEmpty(pictureCode)) {
            layoutBinding.quickLoginUserNameEt.error = "请填写图形验证码"
            return
        }
        if (StringUtils.isEmpty(dynamicCode)) {
            layoutBinding.quickLoginUserNameEt.error = "请填写动态验证码"
            return
        }
        
    }
}
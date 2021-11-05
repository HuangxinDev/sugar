//package com.njxm.smart.mvvm
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Observer
//import androidx.lifecycle.SavedStateViewModelFactory
//import com.ntxm.smart.R
//
///**
// *
// * @author huangxin
// * @date 2021/8/16
// */
//class UserProfileFragment : Fragment() {
//    private val viewModel: UserProfileViewModel by viewModels(factoryProducer = { SavedStateViewModelFactory(this) })
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.user_profile_layout, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.user.observe(viewLifecycleOwner, Observer {
//            // update UI
//        })
//    }
//}
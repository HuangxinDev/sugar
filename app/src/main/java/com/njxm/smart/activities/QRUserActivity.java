/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.njxm.smart.global.KeyConstant;
import com.njxm.smart.model.jsonbean.UserBean;
import com.njxm.smart.utils.BitmapUtils;
import com.njxm.smart.utils.ResolutionUtil;
import com.njxm.smart.utils.SPUtils;
import com.njxm.smart.view.CircleImageView;
import com.ntxm.smart.R;

import butterknife.BindView;

public class QRUserActivity extends BaseActivity {

    @BindView(R.id.qr_btn)
    protected AppCompatImageView mQRImageView;

    @BindView(R.id.user_head)
    protected CircleImageView ivUserHead;

    @BindView(R.id.user_name)
    protected AppCompatTextView tvUserName;

    @BindView(R.id.user_work)
    protected AppCompatTextView tvUserWorkType;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_qr_card;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setActionBarTitle("二维码名片");

        Glide.with(this)
                .load(BitmapUtils.createQRCodeBitmap("http://119.3.136.127:7775/#/personal?id="
                                + SPUtils.getStringValue(KeyConstant.KEY_USER_ID),
                        ResolutionUtil.dp2Px(244),
                        ResolutionUtil.dp2Px(244),
                        "UTF-8",
                        "L",
                        null,
                        Color.BLACK, Color.WHITE))
                .into(this.mQRImageView);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void updateUserInfo(UserBean bean) {
        Glide.with(this)
                .load(bean.getIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.mine_icon_user_head))
                .into(this.ivUserHead);
        this.tvUserName.setText(bean.getUserName());
        this.tvUserWorkType.setText(bean.getWorkType());
    }
}

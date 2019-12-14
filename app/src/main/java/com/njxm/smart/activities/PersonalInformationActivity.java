package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ns.demo.R;

/**
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity {

    private View mUserBaseNews;
    private ImageView mUserNewsImage;
    private View mUserBaseDetailNews;

    private boolean showDetails = false;


    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("个人信息");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mUserBaseNews = findViewById(R.id.news_user_base_new);
        mUserNewsImage = findViewById(R.id.news_user_base_new_detail);
        mUserBaseDetailNews = findViewById(R.id.news_user_base_new_sub);
        mUserBaseNews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mUserBaseNews) {
            showDetails = !showDetails;
            mUserBaseDetailNews.setVisibility(showDetails ? View.VISIBLE : View.GONE);
            mUserNewsImage.setImageResource(showDetails ? R.mipmap.arrow_down : R.mipmap.arrow_detail);
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }
}

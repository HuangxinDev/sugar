package com.njxm.smart.activities;

import com.alibaba.android.arouter.launcher.ARouter;
import com.njxm.smart.constant.UrlPath;
import com.njxm.smart.eventbus.RequestEvent;
import com.njxm.smart.eventbus.ResponseEvent;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.model.jsonbean.UrlBean;
import com.njxm.smart.tools.network.HttpMethod;
import com.njxm.smart.tools.network.HttpUtils;
import com.njxm.smart.utils.JsonUtils;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {

    private static final int REQUEST_WEBVIEW_URL = 316;
    private final List<UrlBean> mUrls = new ArrayList<>();
    // 服务协议
    @BindView(R.id.about_us_service)
    protected View mAppServiceBtn;
    // 版权信息
    @BindView(R.id.about_us_version)
    protected View mAppCopyRightBtn;
    // 隐私政策
    @BindView(R.id.about_us_secret)
    protected View mAppPrivacyBtn;
    // 新功能介绍
    @BindView(R.id.about_us_feature)
    protected View mAppFeaturesBtn;
    private AppCompatImageView mAppIcon;
    private AppCompatTextView mAppName;
    private AppCompatTextView mAppVersion;

    public static List<UrlBean> getLocalBean() {
        List<UrlBean> urlBeans = new ArrayList<>();
        urlBeans.add(new UrlBean("服务协议", "file:///android_asset/www/service.html"));
        urlBeans.add(new UrlBean("新功能介绍", "file:///android_asset/www/features.html"));
        urlBeans.add(new UrlBean("隐私政策", "file:///android_asset/www/privacy.html"));
        urlBeans.add(new UrlBean("版本信息", "file:///android_asset/www/service.html"));
        return urlBeans;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_about_us;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("关于我们");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);

        HttpUtils.getInstance().request(RequestEvent.newBuilder()
                .url(UrlPath.PATH_ABOUT_US.getUrl())
                .method(HttpMethod.GET)
                .build());

//        Request request = new Request.Builder().url(HttpUrlGlobal.HTTP_ABOUT_US).build();
//
//        HttpUtils.getInstance().postData(0, request, null);
    }

    @OnClick({R.id.about_us_feature, R.id.about_us_secret, R.id.about_us_service, R.id.about_us_version})
    public void onViewClicked(View view) {

        if (this.mUrls.size() < 4) {
            this.mUrls.clear();
            this.mUrls.addAll(com.njxm.smart.activities.AboutUsActivity.getLocalBean());
        }

        UrlBean urlBean = null;

        switch (view.getId()) {
            case R.id.about_us_feature:
                urlBean = this.getRequestUrlBean("新功能介绍");
                break;
            case R.id.about_us_secret:
                urlBean = this.getRequestUrlBean("隐私政策");
                break;
            case R.id.about_us_version:
                urlBean = this.getRequestUrlBean("版本信息");
                break;
            case R.id.about_us_service:
                urlBean = this.getRequestUrlBean("服务协议");
                break;
        }
        if (urlBean != null) {
            ARouter.getInstance().build("/app/about_us")
                    .withString("title_name", urlBean.getName())
                    .withString("resUrl", urlBean.getUrl())
                    .navigation();
        } else {
            EventBus.getDefault().post(new ToastEvent("没有找到相应h5页面"));
        }
    }

    public UrlBean getRequestUrlBean(String name) {
        for (UrlBean bean : this.mUrls) {
            if (bean.getName().equals(name)) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public void onResponse(ResponseEvent event) {
        if (UrlPath.PATH_ABOUT_US.getUrl().equals(event.getUrl())) {
            if (this.mUrls.size() > 0) {
                this.mUrls.clear();
            }
            this.mUrls.addAll(JsonUtils.getJsonArray(event.getData(), UrlBean.class));
        } else {
            super.onResponse(event);
        }
    }
}

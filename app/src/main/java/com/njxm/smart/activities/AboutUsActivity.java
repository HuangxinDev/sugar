package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.SmartCloudApplication;
import com.njxm.smart.activities.adapter.StringRecyclerAdapter;
import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.model.component.ListItem;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {


    // 服务协议
    private AppCompatTextView mAppServiceProtolBtn;
    // 版权信息
    private AppCompatTextView mAppCopyRightBtn;
    // 隐私政策
    private AppCompatTextView mAppPrivacyBtn;
    // 新功能介绍
    private AppCompatTextView mAppNewFeaturesBtn;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_about_us;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("关于我们");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        List<ListItem> mListItems = new ArrayList<>();
        Drawable back = SmartCloudApplication.getApplication().getDrawable(R.mipmap.arrow_detail);
        mListItems.add(new ListItem("服务协议"));
        mListItems.add(new ListItem("版权信息"));
        mListItems.add(new ListItem("隐私政策"));
        mListItems.add(new ListItem("新功能介绍"));
        StringRecyclerAdapter adapter = new StringRecyclerAdapter(R.layout.item_list, mListItems);
        MyRecyclerViewItemDecoration itemDecoration = new MyRecyclerViewItemDecoration();
        itemDecoration.setPositions(10, 3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startWebView(HTMLS[position],
                        ((ListItem) adapter.getItem(position)).getTitle());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }

    private static final String[] HTMLS = {"service.html", "service.html", "privacy.html", "features.html"};

    private void startWebView(String assetResource, String title) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("title_name", title);
        intent.putExtra("asset_name", assetResource);
        startActivity(intent);
    }
}

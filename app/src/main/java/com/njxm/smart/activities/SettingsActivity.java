package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.StringRecyclerAdapter;
import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.model.component.ListItem;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面 主页我的-设置
 */
public class SettingsActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_settings;
    }

    private List<ListItem> mListItems = new ArrayList<>();

    private AppCompatTextView mExitLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mExitLoginBtn = findViewById(R.id.login_exit);
        mExitLoginBtn.setOnClickListener(this);

        mListItems.add(new ListItem("重置密码"));
        mListItems.add(new ListItem("更换手机"));
        mListItems.add(new ListItem("检查更新", "当前版本1.0  "));
        mListItems.add(new ListItem("清除缓存"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        BaseQuickAdapter baseQuickAdapter = new StringRecyclerAdapter(R.layout.item_list, mListItems);
        MyRecyclerViewItemDecoration itemDecoration = new MyRecyclerViewItemDecoration();
        itemDecoration.setPositions(10, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(SettingsActivity.this, "click " + mListItems.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();
                startActivityOfIndex(position);
            }
        });
        recyclerView.setAdapter(baseQuickAdapter);


        setActionBarTitle("设置");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (mExitLoginBtn == v) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void startActivityOfIndex(int index) {
        Intent intent = null;
        switch (index) {
            case 0:
                intent = new Intent(this, ResetPasswordActivity.class);
                intent.putExtra("action", "2");

                break;
            case 1:
                intent = new Intent(this, UpdateTelPhoneActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }

    public static class Item {
        private String title = "";
        private String subTitle = " ";

        public Item(String title) {
            this.title = title;
        }

        public Item(String title, String subTitle) {
            this(title);
            this.subTitle = subTitle.trim() + "  ";
        }

        public String getTitle() {
            return title;
        }

        public String getSubTitle() {
            return subTitle;
        }
    }
}

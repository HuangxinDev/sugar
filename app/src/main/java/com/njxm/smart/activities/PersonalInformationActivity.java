package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
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
 * 个人信息页面（由主页 我的-姓名跳转）
 */
public class PersonalInformationActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("个人信息");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        final List<ListItem> mListItems = new ArrayList<>();
        mListItems.add(new ListItem("头像", "", R.mipmap.user_head));
        mListItems.add(new ListItem("姓名", "赵建国", 0));
        mListItems.add(new ListItem("手机", "188xxxxxxx  "));
        ListItem person = new ListItem("基本信息");
        List<ListItem> subList = new ArrayList<>();
        subList.add(new ListItem("单位名称", "领筑集团有限公司", 0));
        subList.add(new ListItem("部门名称", "下部构造施工三队", 0));
        subList.add(new ListItem("班组", "施工二组", 0));
        subList.add(new ListItem("岗位名称", "普工", 0));
        subList.add(new ListItem("人员编号", "xc123458", 0));
        person.setPaddingTop(10);
        person.addSubListItem(subList);
        mListItems.add(person);

        ListItem aa = new ListItem("录入人脸", "请录入人脸用于考勤  ");
        aa.setPaddingTop(10);
        mListItems.add(aa);
        mListItems.add(new ListItem("学历"));
        mListItems.add(new ListItem("现居地址", "南通市开发区星湖大厦16座..."));
        mListItems.add(new ListItem("持有证书"));
        mListItems.add(new ListItem("体检报告", "* 请上传体检报告  "));
        mListItems.add(new ListItem("实名认证", "* 请实名认证  "));
        mListItems.add(new ListItem("紧急联系人"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StringRecyclerAdapter adapter = new StringRecyclerAdapter(R.layout.item_list, mListItems);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ListItem item = (ListItem) adapter.getItem(position);
                if (item != null && item.subData != null) {
                    List list = adapter.getData();
                    if (item.isOpen) {
                        item.setSubTitleRes(R.mipmap.arrow_detail);
                        for (int i = 0; i < item.subData.size(); i++) {
                            list.remove(position + 1);
                        }
                    } else {
                        item.setSubTitleRes(R.mipmap.arrow_down);
                        list.addAll(position + 1, item.subData);
                    }
                    item.isOpen = !item.isOpen;
                    adapter.notifyDataSetChanged();
                }

            }
        });
        MyRecyclerViewItemDecoration decoration = new MyRecyclerViewItemDecoration();
        decoration.setDatas(mListItems);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }
}

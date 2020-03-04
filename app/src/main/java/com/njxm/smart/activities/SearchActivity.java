package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查找联系人列表
 */
public class SearchActivity extends BaseActivity {

    private MMAdapter<ContactsBean> mAdapter;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.layout_search_page;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MMAdapter<>(R.layout.layout_contacts_item, getContactsBeans());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ContactsBean bean = ((ContactsBean) adapter.getItem(position));
                bean.setSelected(!bean.isSelected());
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<ContactsBean> getContactsBeans() {
        List<ContactsBean> contactsBeans = new ArrayList<>();
        contactsBeans.add(new ContactsBean("联系人1", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人2", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人3", "", "特工", true));
        contactsBeans.add(new ContactsBean("联系人4", "", "普工", false));
        contactsBeans.add(new ContactsBean("联系人5", "", "特工", true));
        contactsBeans.add(new ContactsBean("联系人6", "", "普工", false));
        return contactsBeans;
    }
}

class ContactsBean {
    private String name;
    private String headUrl;
    private String job;
    private boolean superWorker;
    private boolean selected = false;

    public ContactsBean(String name, String headUrl, String job, boolean superWorker) {
        this.name = name;
        this.headUrl = headUrl;
        this.job = job;
        this.superWorker = superWorker;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isSuperWorker() {
        return superWorker;
    }

    public void setSuperWorker(boolean superWorker) {
        this.superWorker = superWorker;
    }
}

class MMAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public MMAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public MMAdapter(@Nullable List<T> data) {
        super(data);
    }

    public MMAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (item instanceof ContactsBean) {
            helper.setText(R.id.user_name, ((ContactsBean) item).getName());
            helper.setText(R.id.job, ((ContactsBean) item).getJob());
            helper.setVisible(R.id.select_state, ((ContactsBean) item).isSelected());
            helper.setVisible(R.id.job_state, ((ContactsBean) item).isSuperWorker());
        }
    }
}

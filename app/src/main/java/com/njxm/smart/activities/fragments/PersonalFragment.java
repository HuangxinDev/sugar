package com.njxm.smart.activities.fragments;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.fragments.adapter.PersonFragmentListAdapter;
import com.njxm.smart.model.component.PersonListItem;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * "我的" Fragment
 */
public class PersonalFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mPersonFragmentListAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_personal;
    }

    @Override
    protected void init() {
        super.init();
        mRecyclerView = getContentView().findViewById(R.id.personal_recycler_view);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        List<PersonListItem> datas = new ArrayList<>();
        datas.add(new PersonListItem(R.mipmap.real_name_auth, "实名认证", "* 请实名认证", true));
        datas.add(new PersonListItem(R.mipmap.medical_report, "体检报告", "* 请上传体检报告", true));
        datas.add(new PersonListItem(R.mipmap.upload_certificate, "证书上传", "", false));
        datas.add(new PersonListItem(R.mipmap.abount_us, "关于我们", "", false));
        datas.add(new PersonListItem(R.mipmap.settings, "设置", "", false));
        mRecyclerView.setLayoutManager(layoutManager);
        mPersonFragmentListAdapter = new PersonFragmentListAdapter(R.layout.item_fragment_personal_list, datas);
        mPersonFragmentListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(),
                        "data： " + ((PersonListItem) adapter.getData().get(position)).getTitleRes(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mPersonFragmentListAdapter);
    }
}

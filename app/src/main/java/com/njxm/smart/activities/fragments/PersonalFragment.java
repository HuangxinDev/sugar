package com.njxm.smart.activities.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.AboutUsActivity;
import com.njxm.smart.activities.PersonalInformationActivity;
import com.njxm.smart.activities.RealNameAuthenticationActivity;
import com.njxm.smart.activities.SettingsActivity;
import com.njxm.smart.activities.fragments.adapter.PersonFragmentListAdapter;
import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.model.component.PersonListItem;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * "我的" Fragment
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mPersonFragmentListAdapter;

    // 个人信息页面按钮
    private AppCompatTextView mUserNewsBtn;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_personal;
    }

    @Override
    protected void init() {
        super.init();
        mRecyclerView = getContentView().findViewById(R.id.personal_recycler_view);
        mUserNewsBtn = getContentView().findViewById(R.id.user_name);
        mUserNewsBtn.setOnClickListener(this);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        List<PersonListItem> datas = new ArrayList<>();
        datas.add(new PersonListItem(R.mipmap.real_name_auth, "实名认证", "* 请实名认证", true, 0));
        datas.add(new PersonListItem(R.mipmap.medical_report, "体检报告", "* 请上传体检报告", true,
                R.dimen.dp_1));
        datas.add(new PersonListItem(R.mipmap.upload_certificate, "证书上传", "", false, R.dimen.dp_1));
        datas.add(new PersonListItem(R.mipmap.abount_us, "关于我们", "", false, R.dimen.dp_10));
        datas.add(new PersonListItem(R.mipmap.settings, "设置", "", false, R.dimen.dp_1));
        mRecyclerView.setLayoutManager(layoutManager);

        MyRecyclerViewItemDecoration itemDecoration = new MyRecyclerViewItemDecoration();
        itemDecoration.setPositions(10, 3);
        mRecyclerView.addItemDecoration(itemDecoration);
        mPersonFragmentListAdapter = new PersonFragmentListAdapter(R.layout.item_fragment_personal_list, datas);
        mPersonFragmentListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 3) {
                    Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), RealNameAuthenticationActivity.class);
                    intent.putExtra("title", ((PersonListItem) adapter.getItem(position)).getTitleRes());
                    startActivity(intent);
                }

                Toast.makeText(getActivity(),
                        "data： " + ((PersonListItem) adapter.getData().get(position)).getTitleRes(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mPersonFragmentListAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mUserNewsBtn) {
            Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
            startActivity(intent);
        }
    }
}

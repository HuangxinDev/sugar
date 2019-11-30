package com.njxm.smart.activities.fragments.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.njxm.smart.model.component.PersonListItem;
import com.ns.demo.R;

import java.util.List;

public class PersonFragmentListAdapter extends BaseQuickAdapter<PersonListItem, BaseViewHolder> {

    public PersonFragmentListAdapter(int layoutResId, List<PersonListItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonListItem item) {
        ((TextView) helper.getView(R.id.item_title)).setText(item.getTitleRes());
        ((ImageView) helper.getView(R.id.item_header)).setImageResource(item.getIconRes());
        ((TextView) helper.getView(R.id.item_sub_title)).setText(item.getSubTitle());
        helper.getView(R.id.item_star_icon).setVisibility(item.isShowStar() ? View.VISIBLE :
                View.INVISIBLE);
    }
}

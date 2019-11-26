package com.njxm.smart.activities.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njxm.smart.model.component.PersonListItem;
import com.njxm.smart.view.adapter.BaseRecyclerViewAdapter;
import com.njxm.smart.view.holder.BaseRecyclerViewHolder;
import com.ns.demo.R;

public class PersonFragmentListAdapter extends BaseRecyclerViewAdapter<PersonListItem> {

    public PersonFragmentListAdapter(Context paramContext) {
        super(paramContext);
    }

    @Override
    public int setConvertId() {
        return R.layout.item_fragment_personal_list;
    }

    @Override
    public void convertView(BaseRecyclerViewHolder holder, int position) {
        PersonListItem item = getItem(position);
        ((TextView) holder.getItemView(R.id.item_title)).setText(item.getTitleRes());
        ((ImageView) holder.getItemView(R.id.item_header)).setImageResource(item.getIconRes());
        ((TextView) holder.getItemView(R.id.item_sub_title)).setText(item.getSubTitle());
        holder.getItemView(R.id.item_star_icon).setVisibility(item.isShowStar() ? View.VISIBLE :
                View.INVISIBLE);
    }
}

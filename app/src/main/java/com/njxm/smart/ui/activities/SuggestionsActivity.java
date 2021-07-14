/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.ui.activities.adapter.SuggestionDetailAdapter;
import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.model.component.SuggestionDetailItem;
import com.ntxm.smart.R;

/**
 * 意见箱 主页工作中心 - 意见箱
 */
public class SuggestionsActivity extends BaseActivity {

    Suggestion mSuggestion = Suggestion.NONE;
    private RecyclerView mRecyclerView;
    private View mSuggestionEdit;
    private AppCompatEditText mAppCompatEditText;
    private AppCompatTextView mAppCompatTextView;
    private AppCompatTextView mCommitBtnAppCompatTextView;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_suggestions_box;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showTitle(true, "意见箱");
        this.showLeftBtn(true, R.mipmap.arrow_back_blue);
        this.showRightBtn(true, R.mipmap.new_add);

        this.mSuggestionEdit = this.findViewById(R.id.suggestion_edit);
        this.mAppCompatEditText = this.findViewById(R.id.suggestion_edit_message);
        this.mAppCompatTextView = this.findViewById(R.id.void_suggestion);
        this.mCommitBtnAppCompatTextView = this.findViewById(R.id.suggestion_commit_btn);


        this.mRecyclerView = this.findViewById(R.id.recycler_view);
        List<SuggestionDetailItem> data = new ArrayList<>();
        data.add(new SuggestionDetailItem("员工1", "普工", "建议或意见1"));
        data.add(new SuggestionDetailItem("员工2", "管理", "建议或意见2"));
        data.add(new SuggestionDetailItem("员工3", "普工", "建议或意见3"));
        data.add(new SuggestionDetailItem("员工4", "管理", "建议或意见4"));
        data.add(new SuggestionDetailItem("员工5", "普工", "建议或意见5"));
        SuggestionDetailAdapter adapter = new SuggestionDetailAdapter(R.layout.suggestion_commit_item, data);

        MyRecyclerViewItemDecoration itemDecoration = new MyRecyclerViewItemDecoration();
        this.mRecyclerView.addItemDecoration(itemDecoration);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(adapter);

        if (this.mSuggestion == Suggestion.NONE) {
            this.mRecyclerView.setVisibility(View.GONE);
            this.mSuggestionEdit.setVisibility(View.GONE);
            this.mAppCompatTextView.setVisibility(View.VISIBLE);
        }

        this.mCommitBtnAppCompatTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == this.mCommitBtnAppCompatTextView) {
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mRecyclerView, View.VISIBLE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mActionBarRightBtn, View.VISIBLE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mSuggestionEdit, View.GONE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mAppCompatTextView, View.GONE);

            this.mAppCompatEditText.getText().clear();
            this.mSuggestion = Suggestion.COMMIT;
        }
    }

    @Override
    public void onClickLeftBtn() {
        if (this.mSuggestion == Suggestion.NONE || this.mSuggestion == Suggestion.COMMIT) {
            this.finish();
        } else if (this.mSuggestion == Suggestion.EDIT) {
            this.mRecyclerView.setVisibility(View.GONE);
            this.mSuggestionEdit.setVisibility(View.GONE);
            this.mAppCompatTextView.setVisibility(View.VISIBLE);
            this.showRightBtn(true, R.mipmap.new_add);
            this.mSuggestion = Suggestion.NONE;
        }
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        if (this.mSuggestion == Suggestion.NONE || this.mSuggestion == Suggestion.COMMIT) {
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mActionBarRightBtn, View.GONE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mSuggestionEdit, View.VISIBLE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mAppCompatTextView, View.GONE);
            com.njxm.smart.ui.activities.BaseActivity.setVisible(this.mRecyclerView, View.GONE);
            this.mSuggestion = Suggestion.EDIT;
        }
    }

    enum Suggestion {
        NONE,
        EDIT,
        COMMIT
    }
}

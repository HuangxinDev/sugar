/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.ui.activities.adapter.SuggestionDetailAdapter;
import com.ntxm.smart.R;
import com.sugar.android.common.utils.ViewUtils;
import com.sugar.android.common.view.SafeOnClickListener;

import java.util.Observable;

/**
 * 意见箱 主页工作中心 - 意见箱
 */
public class SuggestionsActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private View mSuggestionEdit;
    private AppCompatEditText mAppCompatEditText;
    private AppCompatTextView mAppCompatTextView;
    private AppCompatTextView mCommitBtnAppCompatTextView;

    private final SuggestionModel model = new SuggestionModel();
    private SuggestionDetailAdapter adapter;

    @Override
    protected int setContentLayoutId() {
        return R.layout.activity_suggestions_box;
    }

    @Override
    protected ActionBarItem getActionBarItem() {
        return new ActionBarItem(R.mipmap.arrow_back_blue, R.mipmap.new_add, getString(R.string.suggestion_title));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model.addObserver(this::update);
        this.mSuggestionEdit = this.findViewById(R.id.suggestion_edit);
        this.mAppCompatEditText = this.findViewById(R.id.suggestion_edit_message);
        this.mAppCompatTextView = this.findViewById(R.id.void_suggestion);
        this.mCommitBtnAppCompatTextView = this.findViewById(R.id.suggestion_commit_btn);
        this.mRecyclerView = this.findViewById(R.id.recycler_view);
        this.mRecyclerView.addItemDecoration(new MyRecyclerViewItemDecoration());
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuggestionDetailAdapter(R.layout.suggestion_commit_item, model.getMockData());
        this.mRecyclerView.setAdapter(adapter);

        ViewUtils.setVisibility(this.mRecyclerView, model.getState() != Suggestion.NONE);
        ViewUtils.setVisibility(this.mSuggestionEdit, model.getState() != Suggestion.NONE);
        ViewUtils.setVisibility(this.mAppCompatTextView, model.getState() == Suggestion.NONE);
        ViewUtils.setOnClickListener(mCommitBtnAppCompatTextView, new SafeOnClickListener(1000) {
            @Override
            public void onSafeClick(View view) {
                ViewUtils.setVisibility(mRecyclerView, true);
                ViewUtils.setVisibility(mActionBarRightBtn, true);
                ViewUtils.setVisibility(mSuggestionEdit, false);
                ViewUtils.setVisibility(mAppCompatTextView, false);
                mAppCompatEditText.getText().clear();
                model.updateSuggestionState(Suggestion.COMMIT);
            }
        });
    }

    @Override
    public void onClickLeftBtn() {
        if (isNotEdit()) {
            this.finish();
        } else if (this.model.getState() == Suggestion.EDIT) {
            this.mRecyclerView.setVisibility(View.GONE);
            this.mSuggestionEdit.setVisibility(View.GONE);
            this.mAppCompatTextView.setVisibility(View.VISIBLE);
            this.showRightBtn(true, R.mipmap.new_add);
            this.model.updateSuggestionState(Suggestion.NONE);
        }
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        if (isNotEdit()) {
            ViewUtils.setVisibility(this.mActionBarRightBtn, View.GONE);
            ViewUtils.setVisibility(this.mSuggestionEdit, View.VISIBLE);
            ViewUtils.setVisibility(this.mAppCompatTextView, View.GONE);
            ViewUtils.setVisibility(this.mRecyclerView, View.GONE);
            this.model.updateSuggestionState(Suggestion.EDIT);
        }
    }

    private boolean isNotEdit() {
        return this.model.getState() == Suggestion.NONE || this.model.getState() == Suggestion.COMMIT;
    }

    private void update(Observable o, Object arg) {
        adapter.notifyLoadMoreToLoading();
    }
}

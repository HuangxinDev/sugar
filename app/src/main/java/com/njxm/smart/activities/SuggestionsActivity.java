package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.njxm.smart.activities.adapter.SuggestionDetailAdapter;
import com.njxm.smart.divider.MyRecyclerViewItemDecoration;
import com.njxm.smart.model.component.SuggestionDetailItem;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 意见箱 主页工作中心 - 意见箱
 */
public class SuggestionsActivity extends BaseActivity {

    enum Suggestion {
        NONE,
        EDIT,
        COMMIT
    }

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
        showTitle(true, "意见箱");
        showLeftBtn(true, R.mipmap.arrow_back_blue);
        showRightBtn(true, R.mipmap.new_add);

        mSuggestionEdit = findViewById(R.id.suggestion_edit);
        mAppCompatEditText = findViewById(R.id.suggestion_edit_message);
        mAppCompatTextView = findViewById(R.id.void_suggestion);
        mCommitBtnAppCompatTextView = findViewById(R.id.suggestion_commit_btn);


        mRecyclerView = findViewById(R.id.recycler_view);
        List<SuggestionDetailItem> data = new ArrayList<>();
        data.add(new SuggestionDetailItem("员工1", "普工", "建议或意见1"));
        data.add(new SuggestionDetailItem("员工2", "管理", "建议或意见2"));
        data.add(new SuggestionDetailItem("员工3", "普工", "建议或意见3"));
        data.add(new SuggestionDetailItem("员工4", "管理", "建议或意见4"));
        data.add(new SuggestionDetailItem("员工5", "普工", "建议或意见5"));
        SuggestionDetailAdapter adapter = new SuggestionDetailAdapter(R.layout.suggestion_commit_item, data);

        MyRecyclerViewItemDecoration itemDecoration = new MyRecyclerViewItemDecoration();
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        if (mSuggestion == Suggestion.NONE) {
            mRecyclerView.setVisibility(View.GONE);
            mSuggestionEdit.setVisibility(View.GONE);
            mAppCompatTextView.setVisibility(View.VISIBLE);
        }

        mCommitBtnAppCompatTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mCommitBtnAppCompatTextView) {
            setVisiable(mRecyclerView, View.VISIBLE);
            setVisiable(mActionBarRightBtn, View.VISIBLE);
            setVisiable(mSuggestionEdit, View.GONE);
            setVisiable(mAppCompatTextView, View.GONE);

            mAppCompatEditText.getText().clear();
            mSuggestion = Suggestion.COMMIT;
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        if (mSuggestion == Suggestion.NONE || mSuggestion == Suggestion.COMMIT) {
            finish();
        } else if (mSuggestion == Suggestion.EDIT) {
            mRecyclerView.setVisibility(View.GONE);
            mSuggestionEdit.setVisibility(View.GONE);
            mAppCompatTextView.setVisibility(View.VISIBLE);
            showRightBtn(true, R.mipmap.new_add);
            mSuggestion = Suggestion.NONE;
        }
    }

    @Override
    public void onClickRightBtn() {
        super.onClickRightBtn();
        if (mSuggestion == Suggestion.NONE || mSuggestion == Suggestion.COMMIT) {
            setVisiable(mActionBarRightBtn, View.GONE);
            setVisiable(mSuggestionEdit, View.VISIBLE);
            setVisiable(mAppCompatTextView, View.GONE);
            setVisiable(mRecyclerView, View.GONE);
            mSuggestion = Suggestion.EDIT;
        }
    }
}

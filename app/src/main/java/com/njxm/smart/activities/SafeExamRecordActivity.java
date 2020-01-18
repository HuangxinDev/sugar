package com.njxm.smart.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.utils.CharUtils;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 考试记录
 */
public class SafeExamRecordActivity extends BaseActivity {
    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_record_activity;
    }

    // 指示条
    @BindView(R.id.indicator)
    protected View mIndicator;

    TranslateAnimation animation;

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private List<ViewData> mData1 = new ArrayList<>();
    private List<ViewData> mData2 = new ArrayList<>();

    private MutiAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("考试记录");

        mData1.addAll(listData(MutiAdapter.TYPE_CONTACTS));
        mData2.addAll(listData(MutiAdapter.TYPE_EXAM_RECORD));

        mAdapter = new MutiAdapter(mData2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        initIndicator(tvMyExam);
    }

    public void initIndicator(View clickView) {
        float startX = mIndicator.getLeft();
        float toX = clickView.getLeft() + (clickView.getWidth() - mIndicator.getWidth()) / 2.0f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIndicator, "translationX", toX - startX);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SpannableStringBuilder text = new SpannableStringBuilder();
                if (clickView == tvMyExam) {
                    text.append(CharUtils.color(getColor(R.color.color_007AFF), "我的考试"))
                    .append("\n")
                    .append(CharUtils.font(16,
                            CharUtils.bold(CharUtils.color(getColor(R.color.color_007AFF),
                                    mData1.size() + ""))));
                    tvMyExam.setText(text);
                    text.clear();
                    text.append(CharUtils.color(getColor(R.color.color_96A1AD), "组员考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(getColor(R.color.color_252525), mData2.size() + ""))));
                    tvMyMemberExam.setText(text);
                } else {
                    text.append(CharUtils.color(getColor(R.color.color_96A1AD), "我的考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(getColor(R.color.color_252525), mData1.size() + ""))));
                    tvMyExam.setText(text);
                    text.clear();
                    text.append(CharUtils.color(getColor(R.color.color_007AFF), "组员考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(getColor(R.color.color_007AFF), mData2.size() + ""))));
                    tvMyMemberExam.setText(text);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    @BindView(R.id.my_exam)
    protected TextView tvMyExam;

    @BindView(R.id.my_team_member_exam)
    protected TextView tvMyMemberExam;

    @OnClick({R.id.my_exam, R.id.my_team_member_exam})
    public void onViewClicked(View view) {
        mAdapter.setNewData(view.getId() == R.id.my_exam ? mData2 : mData1);
        initIndicator(view);
    }

    public List<ViewData> listData(int type) {
        List<ViewData> data = new ArrayList<>();
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        return data;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public static class ViewData implements MultiItemEntity {

        protected int type;

        public ViewData(int type) {
            this.type = type;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }


    public static final class MutiAdapter extends BaseMultiItemQuickAdapter<ViewData, BaseViewHolder> {

        public static final int TYPE_CONTACTS = 0;
        public static final int TYPE_EXAM_RECORD = 1;

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public MutiAdapter(List<ViewData> data) {
            super(data);
            addItemType(TYPE_CONTACTS, R.layout.item_contacts_layout);
            addItemType(TYPE_EXAM_RECORD, R.layout.item_exam_record_list);

        }

        @Override
        protected void convert(BaseViewHolder helper, ViewData item) {
            helper.setGone(R.id.divider1, helper.getAdapterPosition() != mData.size() - 1);
        }
    }
}

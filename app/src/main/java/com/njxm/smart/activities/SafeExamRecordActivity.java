/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.activities;

import java.util.ArrayList;
import java.util.List;

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
import com.ntxm.smart.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 考试记录
 */
public class SafeExamRecordActivity extends BaseActivity {
    private final List<ViewData> mData1 = new ArrayList<>();
    private final List<ViewData> mData2 = new ArrayList<>();
    // 指示条
    @BindView(R.id.indicator)
    protected View mIndicator;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.my_exam)
    protected TextView tvMyExam;
    @BindView(R.id.my_team_member_exam)
    protected TextView tvMyMemberExam;
    TranslateAnimation animation;
    private MutiAdapter mAdapter;

    public static List<ViewData> listData(int type) {
        List<ViewData> data = new ArrayList<>();
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        data.add(new ViewData(type));
        return data;
    }

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_record_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setActionBarTitle("考试记录");

        this.mData1.addAll(com.njxm.smart.activities.SafeExamRecordActivity.listData(MutiAdapter.TYPE_CONTACTS));
        this.mData2.addAll(com.njxm.smart.activities.SafeExamRecordActivity.listData(MutiAdapter.TYPE_EXAM_RECORD));

        this.mAdapter = new MutiAdapter(this.mData2);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(this.mAdapter);

        this.initIndicator(this.tvMyExam);
    }

    public void initIndicator(View clickView) {
        float startX = this.mIndicator.getLeft();
        float toX = clickView.getLeft() + (clickView.getWidth() - this.mIndicator.getWidth()) / 2.0f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(this.mIndicator, "translationX", toX - startX);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SpannableStringBuilder text = new SpannableStringBuilder();
                if (clickView == com.njxm.smart.activities.SafeExamRecordActivity.this.tvMyExam) {
                    text.append(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_007AFF), "我的考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_007AFF),
                                            com.njxm.smart.activities.SafeExamRecordActivity.this.mData1.size() + ""))));
                    com.njxm.smart.activities.SafeExamRecordActivity.this.tvMyExam.setText(text);
                    text.clear();
                    text.append(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_96A1AD), "组员考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_252525), com.njxm.smart.activities.SafeExamRecordActivity.this.mData2.size() + ""))));
                    com.njxm.smart.activities.SafeExamRecordActivity.this.tvMyMemberExam.setText(text);
                } else {
                    text.append(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_96A1AD), "我的考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_252525), com.njxm.smart.activities.SafeExamRecordActivity.this.mData1.size() + ""))));
                    com.njxm.smart.activities.SafeExamRecordActivity.this.tvMyExam.setText(text);
                    text.clear();
                    text.append(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_007AFF), "组员考试"))
                            .append("\n")
                            .append(CharUtils.font(16,
                                    CharUtils.bold(CharUtils.color(com.njxm.smart.activities.SafeExamRecordActivity.this.getColor(R.color.color_007AFF), com.njxm.smart.activities.SafeExamRecordActivity.this.mData2.size() + ""))));
                    com.njxm.smart.activities.SafeExamRecordActivity.this.tvMyMemberExam.setText(text);
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

    @OnClick({R.id.my_exam, R.id.my_team_member_exam})
    public void onViewClicked(View view) {
        this.mAdapter.setNewData(view.getId() == R.id.my_exam ? this.mData2 : this.mData1);
        this.initIndicator(view);
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
            return this.type;
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
            this.addItemType(com.njxm.smart.activities.SafeExamRecordActivity.MutiAdapter.TYPE_CONTACTS, R.layout.item_contacts_layout);
            this.addItemType(com.njxm.smart.activities.SafeExamRecordActivity.MutiAdapter.TYPE_EXAM_RECORD, R.layout.item_exam_record_list);

        }

        @Override
        protected void convert(BaseViewHolder helper, ViewData item) {
            helper.setGone(R.id.divider1, helper.getAdapterPosition() != this.mData.size() - 1);
        }
    }
}

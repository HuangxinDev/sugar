package com.njxm.smart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.eventbus.ToastEvent;
import com.njxm.smart.utils.LogTool;
import com.ns.demo.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeExamAnswerActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.next_step)
    protected TextView mNextStep;

    @Override
    protected int setContentLayoutId() {
        return R.layout.safe_exam_answer_activity;
    }

    SafeExamAnswerAdapter adapter;

    protected int currentPostion = 0;
    private List<ExamQuestionBean> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager.setSmoothScrollbarEnabled(false);
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setNestedScrollingEnabled(true);
//        mNextStep.setEnabled(false);
        data = loadData();
        adapter = new SafeExamAnswerAdapter(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogTool.printD("view is Group: %s : isTextView: %s", view instanceof RadioGroup,
                        view instanceof RadioButton);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LogTool.printD("onItemChildClick is Group: %s : isTextView: %s", view instanceof RadioGroup,
                        view instanceof RadioButton);
            }
        });

//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //设置什么布局管理器,就获取什么的布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition ,角标值
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    //所有条目,数量值
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        mNextStep.setText("提交");
                    } else {
                        mNextStep.setText("下一题");
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //dx>0:向右滑动,dx<0:向左滑动
                //dy>0:向下滑动,dy<0:向上滑动
                isSlidingToLast = dy > 0;
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.next_step)
    protected void nextStep() {
        if (currentPostion >= loadData().size() - 1) {
            EventBus.getDefault().post(new ToastEvent("下一步"));
            return;
        }
        currentPostion += 1;
        mRecyclerView.scrollToPosition(currentPostion);
    }

    @OnClick(R.id.cancel)
    protected void cancel() {
        finish();
    }

    @Override
    public void onBackPressed() {
        currentPostion -= 1;
        if (currentPostion >= 0) {
            mRecyclerView.scrollToPosition(currentPostion);
        } else {
            super.onBackPressed();
        }
    }

    public List<ExamQuestionBean> loadData() {
        List<ExamQuestionBean> data = new ArrayList<>();
        data.add(new ExamQuestionBean("考题一", null, "", ExamQuestionBean.SINGLE_SELECTION));
        data.add(new ExamQuestionBean("考题二", null, "", ExamQuestionBean.MULI_SELECTION));
        data.add(new ExamQuestionBean("考题三", null, "", ExamQuestionBean.JUDGE_PROBLEM));
        data.add(new ExamQuestionBean("考题四", null, "", ExamQuestionBean.SINGLE_SELECTION));
        data.add(new ExamQuestionBean("考题五", null, "", ExamQuestionBean.JUDGE_PROBLEM));
        return data;
    }

    private static final class ExamQuestionBean implements MultiItemEntity {

        public static final int SINGLE_SELECTION = 101;
        public static final int MULI_SELECTION = 102;
        public static final int JUDGE_PROBLEM = 103;

        public String topic; // 考试提供

        public List<String> topicAnswers;

        public String correctAnswerId;

        public int answerType;

        public ExamQuestionBean() {
        }

        public ExamQuestionBean(String topic, List<String> topicAnswers, String correctAnswerId, int answerType) {
            this.topic = topic;
            this.topicAnswers = topicAnswers;
            this.correctAnswerId = correctAnswerId;
            this.answerType = answerType;
        }

        @Override
        public int getItemType() {
            return answerType;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public List<String> getTopicAnswers() {
            return topicAnswers;
        }

        public void setTopicAnswers(List<String> topicAnswers) {
            this.topicAnswers = topicAnswers;
        }

        public String getCorrectAnswerId() {
            return correctAnswerId;
        }

        public void setCorrectAnswerId(String correctAnswerId) {
            this.correctAnswerId = correctAnswerId;
        }

        public int getAnswerType() {
            return answerType;
        }

        public void setAnswerType(int answerType) {
            this.answerType = answerType;
        }
    }

    private final class SafeExamAnswerAdapter extends BaseMultiItemQuickAdapter<ExamQuestionBean, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public SafeExamAnswerAdapter(List<ExamQuestionBean> data) {
            super(data);
            addItemType(ExamQuestionBean.SINGLE_SELECTION, R.layout.item_safe_exam_single_selection);
            addItemType(ExamQuestionBean.MULI_SELECTION, R.layout.item_safe_exam_mul_selection);
            addItemType(ExamQuestionBean.JUDGE_PROBLEM, R.layout.item_safe_exam_judge);
        }

        @Override
        protected void convert(BaseViewHolder helper, ExamQuestionBean item) {
            helper.setText(R.id.title, item.getTopic());
            switch (item.getItemType()) {
                case ExamQuestionBean.SINGLE_SELECTION:
                    helper.setText(R.id.title_type, "单选题");
                    break;
                case ExamQuestionBean.JUDGE_PROBLEM:
                    helper.setText(R.id.title_type, "判断题");
                    break;
                case ExamQuestionBean.MULI_SELECTION:
                    helper.setText(R.id.title_type, "多选题");
                    break;
            }
        }
    }

}

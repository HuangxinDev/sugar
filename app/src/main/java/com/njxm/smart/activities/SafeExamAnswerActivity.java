package com.njxm.smart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.njxm.smart.utils.LogTool;
import com.ntxm.smart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SafeExamAnswerActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

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
                LogTool.printD("view is Group: %s : isTextView: %s",
                        view.getParent() instanceof RadioGroup,
                        view instanceof RadioButton);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExamQuestionBean bean = (ExamQuestionBean) adapter.getItem(position);

                // 此题打错直接返回失败

                if (bean != null) {
                }

                if (position < adapter.getData().size() - 1) {
                    if (view.getId() == R.id.next_step) {
                        mRecyclerView.scrollToPosition(position + 1);
                        view.setEnabled(false);
                    }
                } else {
                    if (view.getId() == R.id.next_step) {
                        Intent intent = new Intent(SafeExamAnswerActivity.this,
                                SafeExamResultActivity.class);
                        intent.putExtra("result", true);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(adapter);

//        mNextStep.setEnabled(false);
    }

//    @OnClick(R.id.next_step)
//    protected void nextStep() {
//
//        if (currentPostion >= loadData().size() - 1) {
//
//            return;
//        }
//
//        // TODO 判断当前题目答案是否正确
//
//
//
//        currentPostion += 1;
//
//
////        mNextStep.setEnabled(false);
//
////        if (currentPostion == data.size() - 1) {
////            mNextStep.setText("提交");
////        }
//    }

    @OnClick(R.id.cancel)
    protected void cancel() {
        finish();
    }

    public List<ExamQuestionBean> loadData() {
        List<ExamQuestionBean> data = new ArrayList<>();
        data.add(new ExamQuestionBean("考题一", getTopicAnsers("单选", "一"), "",
                ExamQuestionBean.SINGLE_SELECTION));
        data.add(new ExamQuestionBean("考题二", getTopicAnsers("多选", "二"), "",
                ExamQuestionBean.MULI_SELECTION));
        data.add(new ExamQuestionBean("考题三", null, "",
                ExamQuestionBean.JUDGE_PROBLEM));
        data.add(new ExamQuestionBean("考题四", getTopicAnsers("多选", "四"), "",
                ExamQuestionBean.MULI_SELECTION));
        data.add(new ExamQuestionBean("考题五", null, "", ExamQuestionBean.JUDGE_PROBLEM));
        data.add(new ExamQuestionBean("考题六", getTopicAnsers("多选", "六"), "",
                ExamQuestionBean.SINGLE_SELECTION));
        return data;
    }

    public static List<String> getTopicAnsers(String type, String xu) {
        List<String> data = new ArrayList<>();
        if (type.equals("单选")) {
            data.add("考题" + xu + "单选A");
            data.add("考题" + xu + "单选B");
            data.add("考题" + xu + "单选C");
            data.add("考题" + xu + "单选D");
        } else if (type.equals("多选")) {
            data.add("考题" + xu + "多选A");
            data.add("考题" + xu + "多选B");
            data.add("考题" + xu + "多选C");
            data.add("考题" + xu + "多选D");
        }
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

        public String[] userAnswer;

        public void setUserAnswer(String... answer) {
            this.userAnswer = answer;
        }

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

        private String singleResult;
        private Boolean[] muls = {false, false, false, false};
        private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.mul_id_a:
                        muls[0] = isChecked;
                        break;
                    case R.id.mul_id_b:
                        muls[1] = isChecked;
                        break;
                    case R.id.mul_id_c:
                        muls[2] = isChecked;
                        break;
                    case R.id.mul_id_d:
                        muls[3] = isChecked;
                        break;
                }

//                for (boolean flag : muls) {
//                    if (flag) {
//                        mNextStep.setEnabled(true);
//                        return;
//                    }
//                }
//                mNextStep.setEnabled(false);
            }
        };

        @Override
        protected void convert(BaseViewHolder helper, ExamQuestionBean item) {
            helper.setText(R.id.title, item.getTopic());
            helper.setNestView(R.id.next_step);
            if (helper.getAdapterPosition() == mData.size() - 1) {
                helper.setText(R.id.next_step, "提交");
            }
            View view = helper.getView(R.id.next_step);
            switch (item.getItemType()) {
                case ExamQuestionBean.SINGLE_SELECTION:
                    helper.setText(R.id.title_type, "单选题");
                    if (((RadioGroup) helper.getView(R.id.select_group)).getCheckedRadioButtonId() == -1) {
                        view.setEnabled(false);
                    }
                    ((RadioGroup) helper.getView(R.id.select_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            view.setEnabled(true);
                        }
                    });
                    break;
                case ExamQuestionBean.JUDGE_PROBLEM:
                    helper.setText(R.id.title_type, "判断题");
                    if (((RadioGroup) helper.getView(R.id.select_group)).getCheckedRadioButtonId() == -1) {
                        view.setEnabled(false);
                    }
                    ((RadioGroup) helper.getView(R.id.select_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            view.setEnabled(true);
                        }
                    });
                    break;
                case ExamQuestionBean.MULI_SELECTION:
                    helper.setText(R.id.title_type, "多选题");
                    helper.setText(R.id.mul_id_a, item.topicAnswers.get(0));
                    helper.setText(R.id.mul_id_b, item.topicAnswers.get(1));
                    helper.setText(R.id.mul_id_c, item.topicAnswers.get(2));
                    helper.setText(R.id.mul_id_d, item.topicAnswers.get(3));


                    for (boolean flag : muls) {
                        if (flag) {
                            view.setEnabled(true);
                            break;
                        }
                    }
                    ((CheckBox) helper.getView(R.id.mul_id_a)).setOnCheckedChangeListener(listener);
                    ((CheckBox) helper.getView(R.id.mul_id_b)).setOnCheckedChangeListener(listener);
                    ((CheckBox) helper.getView(R.id.mul_id_c)).setOnCheckedChangeListener(listener);
                    ((CheckBox) helper.getView(R.id.mul_id_d)).setOnCheckedChangeListener(listener);
            }
        }
    }


}

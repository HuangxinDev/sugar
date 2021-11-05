package com.njxm.smart.ui.activities;

import androidx.annotation.NonNull;

import com.njxm.smart.model.component.SuggestionDetailItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * @author huangxin
 * @date 2021/8/16
 */
public class SuggestionModel extends Observable {
    private final List<SuggestionDetailItem> data = new ArrayList<>();
    private final MySuggestionApi suggestionApi = new MySuggestionApi();

    private Suggestion mSuggestion = Suggestion.NONE;

    public boolean isCommitted() {
        return mSuggestion == Suggestion.COMMIT;
    }

    public Suggestion getState() {
        return mSuggestion;
    }

    public void updateSuggestionState(Suggestion state) {
        if (mSuggestion != state) {
            mSuggestion = state;
        }
    }


    @NonNull
    List<SuggestionDetailItem> getMockData() {
        data.clear();
        data.add(new SuggestionDetailItem("员工1", "普工", "建议或意见1"));
        data.add(new SuggestionDetailItem("员工2", "管理", "建议或意见2"));
        data.add(new SuggestionDetailItem("员工3", "普工", "建议或意见3"));
        data.add(new SuggestionDetailItem("员工4", "管理", "建议或意见4"));
        data.add(new SuggestionDetailItem("员工5", "普工", "建议或意见5"));
        return Collections.unmodifiableList(data);
    }

    public void getDataFromServer(String employeeId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // request
                // update
                // notify view
            }
        }).start();
    }

    private static class MySuggestionApi implements SuggestionApi {
    }

    @Override
    protected synchronized void clearChanged() {
        super.clearChanged();
        data.clear();
    }
}

package com.njxm.smart.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

/**
 * 标题栏
 *
 * @author huangxin
 * @date 2021/8/6
 */
public class TitleBarFragment extends Fragment {

    private TextView titleTv;
    private TitleViewModel titleViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleViewModel = ViewModelUtils.INSTANCE.findViewModel(this, TitleViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle();
    }

    /**
     * 标题赋值
     */
    private void setTitle() {
        titleViewModel.title.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String title) {
                titleViewModel.title.removeObserver(this);
                titleTv.setText(title);
            }
        });
    }
}

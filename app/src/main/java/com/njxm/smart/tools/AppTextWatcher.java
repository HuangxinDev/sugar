/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.tools;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class AppTextWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeTextChanged(s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextChanged(s.toString());
    }

    public abstract void afterTextChanged(String s);

    public void beforeTextChanged(CharSequence s) {

    }
}

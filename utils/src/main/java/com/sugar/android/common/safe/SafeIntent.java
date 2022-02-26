package com.sugar.android.common.safe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 安全的 Intent
 *
 * @author huangxin
 * @date 2022/2/26
 */
public final class SafeIntent extends Intent {
    private Intent intent;

    public SafeIntent(Intent intent) {
        if (intent == null) {
            this.intent = new Intent();
        }
    }

    @NonNull
    public Intent getIntent() {
        return intent;
    }

    @NonNull
    @Override
    public Bundle getBundleExtra(String name) {
        Bundle bundle = intent.getBundleExtra(name);
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @NonNull
    @Override
    public Bundle getExtras() {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @Nullable
    @Override
    public String getStringExtra(String name) {
        return intent.getStringExtra(name);
    }

    @Override
    public boolean getBooleanExtra(String name, boolean defaultValue) {
        return intent.getBooleanExtra(name, defaultValue);
    }
}

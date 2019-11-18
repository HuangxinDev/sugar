package com.njxm.smart.activities

import android.app.Activity

abstract class BaseActivity : Activity {

    protected var TAG: String? = null

    constructor() {
        TAG = this@BaseActivity.localClassName
    }

}
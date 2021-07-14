package com.njxm.smart.startup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import java.util.*

class LocationInitialization : Initializer<Context> {
    companion object {
        private val TAG = "LocationInitialization"
    }

    override fun create(context: Context): Context {
        Log.d(TAG, "create Context")
        return context;
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return Collections.emptyList()
    }
}
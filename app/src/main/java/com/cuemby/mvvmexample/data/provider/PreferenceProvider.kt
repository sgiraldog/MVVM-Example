package com.cuemby.mvvmexample.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


abstract class PreferenceProvider(context: Context) {

    val appContext = context.applicationContext


    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

}
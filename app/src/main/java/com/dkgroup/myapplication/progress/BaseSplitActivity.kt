package com.dkgroup.myapplication.progress

import android.content.Context
import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseSplitActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {


    override fun attachBaseContext(ctx: Context?) {
        super.attachBaseContext(ctx)
        SplitCompat.install(this)
    }

}
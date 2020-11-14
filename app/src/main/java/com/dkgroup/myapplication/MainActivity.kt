package com.dkgroup.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dkgroup.myapplication.BuildConfig
import com.dkgroup.myapplication.progress.DefaultProgressActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.dkgroup.myapplication.progress.BaseSplitActivity

class MainActivity : BaseSplitActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.dynamic1).setOnClickListener {
            val intent = Intent()
            intent.setClassName(
                BuildConfig.APPLICATION_ID,
                "com.dkgroup.dynamicfeature1.DynamicFeatureActivity"
            );
            startActivity(intent)
        }
        findViewById<View>(R.id.dynamic2).setOnClickListener {
            val intent = Intent(this, NavigationDynamicActivity::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.dynamic3).setOnClickListener {
            val intent = Intent()
            intent.setClassName(
                BuildConfig.APPLICATION_ID,
                "com.dkgroup.dynamicfeatureondemand.DynamicFeatureActivity"
            )
            if (SplitInstallManagerFactory.create(this).installedModules.contains("dynamicfeatureondemand")) {
                startActivity(intent)
            } else {
                val progressIntent = DefaultProgressActivity.getIntent(
                    this,
                    "dynamicfeatureondemand",
                    "com.dkgroup.dynamicfeatureondemand.DynamicFeatureActivity"
                )
                startActivity(progressIntent)
            }
        }
    }
}
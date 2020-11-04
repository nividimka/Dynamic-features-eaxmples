package com.dkgroup.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity(R.layout.activity_main) {
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
    }
}
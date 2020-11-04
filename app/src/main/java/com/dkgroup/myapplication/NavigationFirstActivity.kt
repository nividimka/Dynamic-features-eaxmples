package com.dkgroup.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class NavigationFirstActivity : AppCompatActivity(R.layout.activity_navigation_first){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.button).setOnClickListener {view->
//            val action =
//                SpecifyAmountFragmentDirections
//                    .actionSpecifyAmountFragmentToConfirmationFragment()
//            view.findNavController().navigate(action)
        }
    }
}
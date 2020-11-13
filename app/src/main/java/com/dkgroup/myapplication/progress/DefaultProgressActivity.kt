package com.dkgroup.myapplication.progress

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkgroup.myapplication.BuildConfig
import com.dkgroup.myapplication.R
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class DefaultProgressActivity : AppCompatActivity(R.layout.activity_progress) {
    companion object {
        const val MODULE_NAME = "MODULE_NAME"
        private const val PROGRESS_MAX = 100
        const val CLASS_NAME = "CLASS_NAME"

        fun getIntent(context: Context, moduleName: String, className: String): Intent {
            val i = Intent(context, DefaultProgressActivity::class.java)
            i.putExtra(MODULE_NAME, moduleName)
            i.putExtra(CLASS_NAME, className)
            return i
        }
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var progressTitle: TextView
    private lateinit var action: Button
    private lateinit var manager: SplitInstallManager

    lateinit var moduleName: String
    lateinit var className: String

    private val listener =
        SplitInstallStateUpdatedListener { state ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    //  In order to see this, the application has to be uploaded to the Play Store.
                    showProgress(state.bytesDownloaded(), state.totalBytesToDownload())
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    /*
                      This may occur when attempting to download a sufficiently large module.
                      In order to see this, the application has to be uploaded to the Play Store.
                      Then features can be requested until the confirmation path is triggered.
                     */
                    startIntentSender(
                        state.resolutionIntent()?.intentSender,
                        null, 0, 0, 0
                    )
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    action.visibility = View.VISIBLE
                    progressTitle.text = "Модуль загружен"
                }
                SplitInstallSessionStatus.INSTALLING -> showProgress(
                    state.bytesDownloaded(),
                    state.totalBytesToDownload()
                )
                SplitInstallSessionStatus.FAILED -> {
                    Toast.makeText(
                        this,
                        "Error: ${state.errorCode()} for module ${state.moduleNames()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    private fun showProgress(bytesDownloaded: Long, totalBytesToDownload: Long) {
        progressBar.run {
            visibility = View.VISIBLE
            if (totalBytesToDownload == 0L) {
                isIndeterminate = true
            } else {
                progress = (PROGRESS_MAX * bytesDownloaded / totalBytesToDownload).toInt()
                isIndeterminate = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = findViewById(R.id.installation_progress)
        action = findViewById(R.id.progress_action)
        progressTitle = findViewById(R.id.progress_title)
        className = intent.getStringExtra(CLASS_NAME).orEmpty()
        moduleName = intent.getStringExtra(MODULE_NAME).orEmpty()
        manager = SplitInstallManagerFactory.create(this)
        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()
        manager.startInstall(request)
        action.setOnClickListener {
            val intent = Intent()
            intent.setClassName(
                BuildConfig.APPLICATION_ID,
                CLASS_NAME
            );
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(listener)
    }
}
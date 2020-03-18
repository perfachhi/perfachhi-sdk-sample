package com.droidfeed.ui.common

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.appachhi.sdk.Appachhi
import com.droidfeed.util.isMarshmallow
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        //Appachhi.getInstance().featureConfigManager.setScreenShotEnable(this, true)

        super.onCreate(savedInstanceState)
    }

    /**
     * Makes code bar transparent.
     */
    protected fun setupTransparentStatusBar() {
        if (isMarshmallow()) {
            window.run {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
    }

    protected fun setupFullScreenWindow() {
        if (isMarshmallow()) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    protected fun darkStatusBarTheme() {
        if (isMarshmallow()) {
            window.decorView.systemUiVisibility = 0
        }
    }

    protected fun lightStatusBarTheme() {
        if (isMarshmallow()) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }
}

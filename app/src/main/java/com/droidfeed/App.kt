package com.droidfeed

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import com.appachhi.sdk.Appachhi
import com.droidfeed.di.DaggerAppComponent
import com.droidfeed.util.appOpenCount
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    @Inject lateinit var sharedPrefs: SharedPreferences

    @Suppress("unused")
    @Inject lateinit var remoteConfig: FirebaseRemoteConfig

    public lateinit var appachhi: Appachhi

    override fun onCreate() {
        super.onCreate()
        initDagger()

        sharedPrefs.appOpenCount += 1
    }

    private fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}

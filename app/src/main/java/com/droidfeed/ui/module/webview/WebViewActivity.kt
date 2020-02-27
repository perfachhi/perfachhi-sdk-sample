package com.droidfeed.ui.module.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.appachhi.sdk.Appachhi
import com.appachhi.sdk.instrument.trace.MethodTrace
import com.appachhi.sdk.instrument.transition.ScreenTransitionManager
import com.droidfeed.R
import com.droidfeed.databinding.ActivityWebviewBinding
import com.droidfeed.ui.common.BaseActivity

class WebViewActivity : BaseActivity() {

    public lateinit var methodTrace: MethodTrace

    override fun onCreate(savedInstanceState: Bundle?) {
        methodTrace = Appachhi.newTrace("WebView Method Trace Droid")

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWebviewBinding>(
            this,
            R.layout.activity_webview
        )


        init(binding)
        methodTrace.stop()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(binding: ActivityWebviewBinding) {
        val webSiteUrl = intent.getStringExtra(EXTRA_URL)

        binding.apply {
            webView.settings.javaScriptEnabled = true

            url = webSiteUrl
            toolbarTitle = webSiteUrl
            setToolbarHomeNavClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        const val EXTRA_URL = "url"
    }
}
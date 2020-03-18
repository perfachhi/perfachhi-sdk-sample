package com.droidfeed.ui.module.about

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.appachhi.sdk.instrument.transition.ScreenTransitionManager
import com.droidfeed.BuildConfig
import com.droidfeed.R
import com.droidfeed.databinding.FragmentAboutBinding
import com.droidfeed.ui.common.BaseFragment
import com.droidfeed.ui.module.about.licence.LicencesActivity
import com.droidfeed.util.AnimUtils.Companion.MEDIUM_ANIM_DURATION
import com.droidfeed.util.CustomTab
import com.droidfeed.util.event.EventObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("ValidFragment")

class AboutFragment : BaseFragment("about") {

    @Inject lateinit var customTab: CustomTab

    private lateinit var binding: FragmentAboutBinding
    private val aboutViewModel: AboutViewModel by viewModels { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = aboutViewModel
            appVersion = getString(R.string.app_version, BuildConfig.VERSION_NAME)
            lifecycleOwner = this@AboutFragment
        }

        //Screen Transition for the About Fragment
       ScreenTransitionManager.getInstance().beginTransition(context as Activity?, "AboutFragment.kt NEW")


        subscribeStartIntentEvent()
        subscribeOpenUrlEvent()
        subscribeOpenLicenceEvent()

        initAnimations()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        //Screen Transition for the AboutFragment
        ScreenTransitionManager.getInstance().endTransition(context as Activity?, "AboutFragment.kt NEW")
    }
    private fun subscribeOpenLicenceEvent() {
        aboutViewModel.openLicences.observe(viewLifecycleOwner, EventObserver {
            Intent(context, LicencesActivity::class.java)
                .also { intent ->
                    startActivity(intent)
                }
        })
    }

    private fun subscribeOpenUrlEvent() {
        aboutViewModel.openUrl.observe(viewLifecycleOwner, EventObserver { url ->
            customTab.showTab(url)
        })
    }

    private fun subscribeStartIntentEvent() {
        aboutViewModel.startIntent.observe(viewLifecycleOwner, EventObserver { intent ->
            startActivity(intent)
        })
    }

    private fun initAnimations() {
        binding.animView.setOnClickListener { view ->
            if (!(view as LottieAnimationView).isAnimating) {
                view.speed *= -1f
                view.resumeAnimation()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            binding.animView.frame = 0
            delay(MEDIUM_ANIM_DURATION)
            binding.animView.resumeAnimation()
        }
    }
}
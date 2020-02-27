package com.droidfeed.ui.module.about.licence

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.appachhi.sdk.instrument.transition.ScreenTransitionManager
import com.droidfeed.R
import com.droidfeed.databinding.ActivityLicenceBinding
import com.droidfeed.ui.adapter.BaseUIModelAlias
import com.droidfeed.ui.adapter.UIModelAdapter
import com.droidfeed.ui.common.BaseActivity
import com.droidfeed.util.CustomTab
import com.droidfeed.util.event.EventObserver

class LicencesActivity : BaseActivity() {

    private val linearLayoutManager = LinearLayoutManager(this)
    private val licenceAdapter: UIModelAdapter by lazy {
        UIModelAdapter(
            lifecycleScope,
            linearLayoutManager
        )
    }

    private val licencesViewModel: LicencesViewModel by viewModels { viewModelFactory }

    private val customTab: CustomTab by lazy { CustomTab(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.apply {
            val pinkColor = ContextCompat.getColor(
                this@LicencesActivity,
                R.color.pink
            )
            statusBarColor = pinkColor
            navigationBarColor = pinkColor
        }

        super.onCreate(savedInstanceState)

        //Screen Transition for the About Fragment
        ScreenTransitionManager.getInstance().beginTransition(this, "LicencesActivity.kt 0.4.7")



        DataBindingUtil.setContentView<ActivityLicenceBinding>(
            this,
            R.layout.activity_licence
        ).apply {
            toolbarTitle = getString(R.string.licences)
            toolbarHomeNavClickListener = View.OnClickListener {
                licencesViewModel.onBackNavigation()
            }

            recyclerView.apply {
                layoutManager = linearLayoutManager
                overScrollMode = View.OVER_SCROLL_NEVER
                adapter = licenceAdapter
            }
        }

        subscribeOpenUrl()
        subscribeLicenceUIModels()
        subscribeOnBackNavigation()
    }


    override fun onResume() {
        super.onResume()

        //Screen Transition for the Licences Activity
        ScreenTransitionManager.getInstance().endTransition(this, "LicencesActivity.kt 0.4.7")
    }

    private fun subscribeOpenUrl() {
        licencesViewModel.openUrl.observe(this, EventObserver { url ->
            customTab.showTab(url)
        })
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeLicenceUIModels() {
        licencesViewModel.licenceUIModels.observe(this, Observer { uiModels ->
            licenceAdapter.addUIModels(uiModels as List<BaseUIModelAlias>)
        })
    }

    private fun subscribeOnBackNavigation() {
        licencesViewModel.onBackNavigation.observe(this, Observer {
            onBackPressed()
        })
    }
}
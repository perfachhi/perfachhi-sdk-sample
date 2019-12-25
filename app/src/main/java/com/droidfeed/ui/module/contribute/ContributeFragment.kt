package com.droidfeed.ui.module.contribute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.droidfeed.databinding.FragmentContributeBinding
import com.droidfeed.ui.common.BaseFragment
import com.droidfeed.util.AnimUtils.Companion.MEDIUM_ANIM_DURATION
import com.droidfeed.util.CustomTab
import com.droidfeed.util.event.EventObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContributeFragment : BaseFragment("contribute") {

    private lateinit var binding: FragmentContributeBinding
    private val contributeViewModel: ContributeViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var customTab: CustomTab

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContributeBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = contributeViewModel
            lifecycleOwner = this@ContributeFragment
        }

        subscribeOpenRepositoryEvent()
        initAnimations()

        return binding.root
    }

    private fun subscribeOpenRepositoryEvent() {
        contributeViewModel.openRepositoryEvent.observe(viewLifecycleOwner, EventObserver { url ->
            customTab.showTab(url)
        })
    }

    private fun initAnimations() {
        binding.animView.setOnClickListener {
            if (!binding.animView.isAnimating) {
                binding.animView.speed *= -1f
                binding.animView.resumeAnimation()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            binding.animView.frame = 0
            delay(MEDIUM_ANIM_DURATION)
            binding.animView.resumeAnimation()
        }
    }
}
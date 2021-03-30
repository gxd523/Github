package com.github.common.fragment

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.github.ui.main.MainActivity
import com.gxd.viewbindingwrapper.ViewBindingFragment

abstract class CommonSinglePageFragment<T : ViewBinding> : ViewBindingFragment<T>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(null)
    }
}
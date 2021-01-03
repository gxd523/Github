package com.github.ui.view.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.ui.main.MainActivity

abstract class CommonSinglePageFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).actionBarController.setupWithViewPager(null)
    }
}
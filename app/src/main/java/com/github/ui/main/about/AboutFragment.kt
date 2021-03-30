package com.github.ui.main.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.github.common.fragment.CommonSinglePageFragment
import com.github.databinding.FragmentAboutBinding
import com.github.util.dp
import com.github.util.markdownText

class AboutFragment : CommonSinglePageFragment<FragmentAboutBinding>() {
    override fun FragmentAboutBinding.onViewCreated(view: View, savedInstanceState: Bundle?) {
        licensesTextView.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .create()
                .apply {
                    setView(TextView(requireContext()).apply {
                        setPadding(10f.dp.toInt())
                        markdownText = context.assets.open("licenses.md").bufferedReader().readText()
                    })
                }
                .show()
        }
    }
}
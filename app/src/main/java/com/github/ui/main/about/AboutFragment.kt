package com.github.ui.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import com.github.R
import com.github.common.fragment.CommonSinglePageFragment
import com.github.util.dp
import com.github.util.markdownText
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : CommonSinglePageFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
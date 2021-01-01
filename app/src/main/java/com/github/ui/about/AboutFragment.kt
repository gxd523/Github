package com.github.ui.about

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.R
import com.github.util.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.AnkoContext.Companion
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by benny on 7/9/17.
 */
class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return AboutFragmentUI().createView(Companion.create(requireContext(), this))
    }
}

class AboutFragmentUI : AnkoComponent<AboutFragment> {
    override fun createView(ui: AnkoContext<AboutFragment>) = ui.apply {
        nestedScrollView {
            verticalLayout {
                imageView {
                    imageResource = R.mipmap.ic_launcher
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView("GitHub", R.style.detail_title) {
                    textColorResource = R.color.colorPrimary
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView("By Bennyhuo", R.style.detail_description) {
                    textColor = R.color.colorPrimary
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                themedTextView(R.string.open_source_licenses, R.style.detail_description) {
                    textColor = R.color.colorPrimary

                    onClick {
                        alert {
                            customView {
                                scrollView {
                                    textView {
                                        padding = dip(10)
                                        markdownText = context.assets.open("licenses.md").bufferedReader().readText()
                                    }
                                }
                            }
                        }.show()
                    }
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.CENTER
            }
        }
    }.view
}
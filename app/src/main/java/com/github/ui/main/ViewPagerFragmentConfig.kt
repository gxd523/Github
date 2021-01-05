package com.github.ui.main

import androidx.fragment.app.Fragment

interface ViewPagerFragmentConfig {
    fun getFragmentPagesLoggedIn(): List<PagerData>
    fun getFragmentPagesNotLoggedIn(): List<PagerData>
}

data class PagerData(val fragment: Fragment, val title: String)

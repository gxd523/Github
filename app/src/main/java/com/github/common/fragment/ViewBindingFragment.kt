package com.github.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Created by guoxiaodong on 3/12/21 11:18
 */
abstract class ViewBindingFragment<T : ViewBinding> : Fragment() {// TODO 写的很好
    private var _binding: T? = null

    val binding: T
        get() = _binding!!

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): T

    abstract fun T.onViewCreated(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return onCreateBinding(inflater, container, savedInstanceState).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
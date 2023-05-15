package com.space.chatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.space.chatapp.common.type.Inflater


open class BaseFragment<VB : ViewBinding>(private val inflate: Inflater<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    //init name
    open fun init() {}
    open fun observe() {}
    open fun listener() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        init()
        listener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
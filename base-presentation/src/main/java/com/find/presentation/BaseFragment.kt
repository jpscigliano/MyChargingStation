package com.find.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<ViewBindingType : ViewBinding>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBindingType
    private var _binding: ViewBindingType? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

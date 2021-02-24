package com.sunwise.practicaltest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sunwise.practicaltest.R
import com.sunwise.practicaltest.databinding.FragmentLoginBinding
import com.sunwise.practicaltest.view.base.BaseFragment
import com.sunwise.practicaltest.view.vm.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment() {

    private val _viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.theme?.applyStyle(R.style.LoginTheme, true)

    }

    override fun setContentView(container: ViewGroup?): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_login, container, false)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
    }

    private fun setListeners(){
        btnLogin.setOnClickListener {
            val args = bundleOf(BaseFragment.HAS_TOOLBAR_KEY to true)
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment, args)
        }
    }
}
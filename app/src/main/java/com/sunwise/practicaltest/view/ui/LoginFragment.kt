package com.sunwise.practicaltest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sunwise.practicaltest.R
import com.sunwise.practicaltest.databinding.FragmentLoginBinding
import com.sunwise.practicaltest.domain.models.User
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
        setObservers()
    }

    private fun setObservers(){
        _viewModel.onErrorOccurred = { property, _, newValue ->
            _viewModel.loadingLiveData.value = false
            if(property.name == "emailError"){
                tilEmail.error = newValue
            }else {
                tilPassword.error = newValue
            }
        }
    }

    private fun setListeners(){
        btnLogin.setOnClickListener {
            val user = User(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
            _viewModel.loadingLiveData.value = true
            _viewModel.login(user){ isLoginCorrect ->
                _viewModel.loadingLiveData.value = false
                if(isLoginCorrect) goToMain()
                else showBadCredentialsDialog()
            }
        }
    }

    private fun showBadCredentialsDialog() {
        AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.login_error_bad_credentials_title))
                .setMessage(getString(R.string.login_error_bad_credentials_message))
                .setPositiveButton(getString(R.string.login_ok)) { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
    }


    private fun goToMain(){
        val args = bundleOf(BaseFragment.HAS_TOOLBAR_KEY to true)
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment, args)
    }
}
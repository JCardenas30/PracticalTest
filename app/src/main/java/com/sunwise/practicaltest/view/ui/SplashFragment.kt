package com.sunwise.practicaltest.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sunwise.practicaltest.R
import com.sunwise.practicaltest.databinding.FragmentSplashBinding
import com.sunwise.practicaltest.view.base.BaseFragment
import com.sunwise.practicaltest.view.vm.SplashViewModel

class SplashFragment: BaseFragment() {

    private val TAG = SplashFragment::class.java.simpleName
    private var _navController: NavController? = null

    private val _viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun setContentView(container: ViewGroup?): View {
        val binding: FragmentSplashBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_splash, container, false)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        fullScreen()
        _navController = Navigation.findNavController(view)
        startTimer()
    }

    private fun startTimer() {
        object: Thread() {
            override fun run() {
                try {
                    sleep(2500)
                } catch (e: Exception) {
                    Log.e(TAG, "Something went wrong.")
                } finally {
                    _navController?.navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }.start()
    }
}

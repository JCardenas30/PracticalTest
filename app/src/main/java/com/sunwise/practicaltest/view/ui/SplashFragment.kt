package com.sunwise.practicaltest.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
        loadData()
    }

    private fun loadData(){
        _viewModel.test {
            _viewModel.active { isLoggedIn ->
                val action = if(isLoggedIn)
                                R.id.action_splashFragment_to_mainFragment
                            else
                                R.id.action_splashFragment_to_loginFragment
                goTo(action)
            }
        }
    }

    private fun goTo(action: Int) {
        object: Thread() {
            override fun run() {
                try {
                    sleep(500)
                } catch (e: Exception) {
                    Log.e(TAG, "Something went wrong.")
                } finally {
                    val args = if(action == R.id.action_splashFragment_to_mainFragment)
                        bundleOf(BaseFragment.HAS_TOOLBAR_KEY to true)
                    else Bundle.EMPTY
                    _navController?.navigate(action, args)
                }
            }
        }.start()
    }
}

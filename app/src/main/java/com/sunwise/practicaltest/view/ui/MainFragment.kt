package com.sunwise.practicaltest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.sunwise.practicaltest.R
import com.sunwise.practicaltest.databinding.FragmentMainBinding
import com.sunwise.practicaltest.view.base.BaseFragment
import com.sunwise.practicaltest.view.vm.MainViewModel

class MainFragment: BaseFragment() {
    private val _viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun setContentView(container: ViewGroup?): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_main, container, false)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        showBackIconOnToolbar()
        setTitle(getString(R.string.main_toolbar_title))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
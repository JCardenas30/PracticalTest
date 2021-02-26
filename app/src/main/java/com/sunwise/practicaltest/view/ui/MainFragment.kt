package com.sunwise.practicaltest.view.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sunwise.practicaltest.R
import com.sunwise.practicaltest.databinding.FragmentMainBinding
import com.sunwise.practicaltest.domain.models.Pokemon
import com.sunwise.practicaltest.view.base.BaseFragment
import com.sunwise.practicaltest.view.utils.GenericAdapter
import com.sunwise.practicaltest.view.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment: BaseFragment() {
    private val _viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val pokemonAdapter = GenericAdapter<Pokemon>(R.layout.item_pokemon)

    override fun setContentView(container: ViewGroup?): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                requireContext()
            ), R.layout.fragment_main, container, false
        )
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(getString(R.string.main_toolbar_title))
        initComponents()
    }

    private fun initComponents(){
        setListeners()
        rvPokemons.adapter = pokemonAdapter
        loadPokemonData()
    }

    private fun setListeners(){
        pokemonAdapter.setOnListItemViewClickListener(object :
            GenericAdapter.OnListItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                pokemonAdapter.getItem(position)?.let { pokemon ->
                    Toast.makeText(requireContext(), "${pokemon.name}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun loadPokemonData(){
        _viewModel.loadingLiveData.value = true
        _viewModel.getPokemon { list ->
            try {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        _viewModel.loadingLiveData.value = false
                        _viewModel.errorLiveData.value = if (list.isEmpty()) getString(R.string.main_load_pokemon_error) else ""
                        pokemonAdapter.addItems(list)
                    }
                }
            }catch (e: Exception){e.printStackTrace()}
        }
    }

    private fun logout(){
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.main_logout_title_dialog)
            .setMessage(R.string.main_logout_msg_dialog)
            .setPositiveButton(R.string.main_logout_yes_dialog) { dialog, _ ->
                dialog.dismiss()
                _viewModel.logout {
                    findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                }
            }
            .setNegativeButton(R.string.main_logout_no_dialog, null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.optLogout) {
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

}
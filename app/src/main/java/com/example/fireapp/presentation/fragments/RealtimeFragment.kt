package com.example.fireapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fireapp.databinding.FragmentRealtimeBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.PokemonListAdapter
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import com.example.fireapp.util.ResultWrapper
import org.koin.androidx.viewmodel.ext.android.viewModel

class RealtimeFragment : Fragment() {
    private var _binding : FragmentRealtimeBinding? = null
    val binding get() = _binding!!

    private val realtimeViewModel by viewModel<RealtimeViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRealtimeBinding.inflate(layoutInflater)

        pokemonListAdapter = PokemonListAdapter()

        val recyclerView = binding.recyclerviewRealtimePokemon
        recyclerView.adapter = pokemonListAdapter

        realtimeViewModel.pokemonList.observe(viewLifecycleOwner, ::updateUI)

        return binding.root
    }

    private fun updateUI(result: ResultWrapper<List<Pokemon>>?) {
        when (result){
            is ResultWrapper.Failure -> Unit
            is ResultWrapper.Success -> {
                pokemonListAdapter.updateDataset(result.data)
            }
        }
    }
}
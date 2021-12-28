package com.example.fireapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.fireapp.databinding.FragmentRealtimeBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.MainActivity
import com.example.fireapp.presentation.PokemonListAdapter
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import com.example.fireapp.util.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class RealtimeFragment : Fragment() {

    private var _binding : FragmentRealtimeBinding? = null
    val binding get() = _binding!!

    private val realtimeViewModel by viewModels<RealtimeViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRealtimeBinding.inflate(layoutInflater)

        (activity as AppCompatActivity).supportActionBar?.let{
            it.title = "Firebase Realtime"
        }

        pokemonListAdapter = PokemonListAdapter{ pokemon ->
            onRecyclerViewItemClick(pokemon)
        }

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

    private fun onRecyclerViewItemClick(pokemon: Pokemon){
        (activity as MainActivity).updateCard(pokemon)
        realtimeViewModel.setCardPokemon(pokemon)
    }
}
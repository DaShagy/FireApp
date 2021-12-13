package com.example.fireapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fireapp.databinding.FragmentRoomBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.MainActivity
import com.example.fireapp.presentation.PokemonListAdapter
import com.example.fireapp.presentation.viewmodels.RoomViewModel
import com.example.fireapp.util.ResultWrapper
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomFragment : Fragment() {
    private var _binding : FragmentRoomBinding? = null
    val binding get() = _binding!!

    private val roomViewModel by viewModel<RoomViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRoomBinding.inflate(layoutInflater)

        (activity as AppCompatActivity).supportActionBar?.let{
            it.title = "Room"
        }

        pokemonListAdapter = PokemonListAdapter{ pokemon ->
            onRecyclerViewItemClick(pokemon)
        }

        val recyclerView = binding.recyclerviewRoomPokemon
        recyclerView.adapter = pokemonListAdapter

        roomViewModel.pokemonList.observe(viewLifecycleOwner, ::updateUI)

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
        roomViewModel.setCardPokemon(pokemon)
    }
}
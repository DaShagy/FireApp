package com.example.fireapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fireapp.databinding.FragmentRealmBinding
import com.example.fireapp.databinding.FragmentRoomBinding
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.presentation.MainActivity
import com.example.fireapp.presentation.PokemonListAdapter
import com.example.fireapp.presentation.viewmodels.RealmViewModel
import com.example.fireapp.util.ResultWrapper
import org.koin.androidx.viewmodel.ext.android.viewModel

class RealmFragment : Fragment() {
    private var _binding : FragmentRealmBinding? = null
    val binding get() = _binding!!

    private val realmViewModel by viewModel<RealmViewModel>()

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRealmBinding.inflate(layoutInflater)

        (activity as AppCompatActivity).supportActionBar?.let{
            it.title = "Realm"
        }

        pokemonListAdapter = PokemonListAdapter{ pokemon ->
            onRecyclerViewItemClick(pokemon)
        }

        val recyclerView = binding.recyclerviewRealmPokemon
        recyclerView.adapter = pokemonListAdapter

        realmViewModel.pokemonList.observe(viewLifecycleOwner, ::updateUI)

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
        realmViewModel.setCardPokemon(pokemon)
    }
}
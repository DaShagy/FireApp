package com.example.fireapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fireapp.databinding.RecyclerviewPokemonlistBinding
import com.example.fireapp.domain.entities.Pokemon

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(val binding: RecyclerviewPokemonlistBinding)
        : RecyclerView.ViewHolder(binding.root)

    private var dataset: MutableList<Pokemon> = mutableListOf()

    fun updateDataset(list: List<Pokemon>){
        dataset = list as MutableList<Pokemon>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = RecyclerviewPokemonlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        with(dataset[position]){
            holder.binding.textViewRecyclerViewPokemonName.text = this.name
            holder.binding.textViewRecyclerViewPokemonId.text = this.id.toString()
        }
    }

    override fun getItemCount(): Int = dataset.size
}
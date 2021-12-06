package com.example.fireapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fireapp.databinding.RecyclerviewPokemonlistBinding
import com.example.fireapp.domain.entities.Pokemon

class PokemonListAdapter(
    private val listener: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>(){

    private class DiffCallback(
        val oldList: List<Pokemon>,
        val newList: List<Pokemon>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id!! == newList[newItemPosition].id!!
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


    class PokemonViewHolder(
        val binding: RecyclerviewPokemonlistBinding,
        val itemListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemListener(adapterPosition)
            }
        }
    }

    private var dataset: List<Pokemon> = listOf()

    fun updateDataset(list: List<Pokemon>){
        val oldList = dataset
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            DiffCallback(
                oldList = oldList,
                newList = list
            )
        )
        dataset = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = RecyclerviewPokemonlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding){
            listener(dataset[it])
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        with(dataset[position]){
            holder.binding.textViewRecyclerViewPokemonName.text = this.name
            holder.binding.textViewRecyclerViewPokemonId.text = this.id.toString()
        }
    }

    override fun getItemCount(): Int = dataset.size
}
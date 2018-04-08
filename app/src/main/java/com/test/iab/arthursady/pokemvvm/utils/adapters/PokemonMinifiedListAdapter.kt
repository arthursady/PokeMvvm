package com.test.iab.arthursady.pokemvvm.utils.adapters

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.iab.arthursady.pokemvvm.R
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.NetworkState
import com.test.iab.arthursady.pokemvvm.utils.listeners.PokemonListListener
import com.test.iab.arthursady.pokemvvm.utils.viewholders.PokemonMinifiedViewHolder


class PokemonMinifiedListAdapter(val listener: PokemonListListener, private val retryCallback: () -> Unit): PagedListAdapter<PokemonMinified, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonMinifiedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonMinifiedViewHolder) {
            holder.pokemon = getItem(position)
            holder.listener = listener
        }
    }
    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<PokemonMinified>() {
            override fun areContentsTheSame(oldItem: PokemonMinified, newItem: PokemonMinified): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: PokemonMinified, newItem: PokemonMinified): Boolean =
                    oldItem.name == newItem.name
        }
    }
}
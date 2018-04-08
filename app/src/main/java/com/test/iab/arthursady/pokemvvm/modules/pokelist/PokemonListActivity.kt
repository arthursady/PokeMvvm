package com.test.iab.arthursady.pokemvvm.modules.pokelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.test.iab.arthursady.pokemvvm.R
import com.test.iab.arthursady.pokemvvm.model.PokemonMinified
import com.test.iab.arthursady.pokemvvm.modules.base.BaseActivity
import com.test.iab.arthursady.pokemvvm.utils.adapters.PokemonMinifiedListAdapter
import com.test.iab.arthursady.pokemvvm.utils.dataclasses.NetworkState
import com.test.iab.arthursady.pokemvvm.utils.listeners.PokemonListListener
import kotlinx.android.synthetic.main.activity_poke_list.*


class PokemonListActivity: BaseActivity(), PokemonListListener {

    private var viewModel: PokemonListViewModel? = null
    private var adapter: PokemonMinifiedListAdapter? = null

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poke_list)
        viewModel = ViewModelProviders.of(this).get(PokemonListViewModel::class.java)
        viewModel?.onActivityCreated()
        setupView()
    }

    //endregion

    //region Private

    private fun initAdapter() {
        pokemonRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonMinifiedListAdapter(this) {
            viewModel?.retry()
        }
        pokemonRecyclerView.adapter = adapter
        viewModel?.repoResult?.observe(this, Observer {
            adapter?.submitList(it)
        })
        viewModel?.networkState?.observe(this, Observer {
            adapter?.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        viewModel?.refreshState?.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })
        swipe_refresh.setOnRefreshListener {
            viewModel?.refresh()
        }
    }

    private fun setupView() {
        initAdapter()
        initSwipeToRefresh()
    }

    //endregion

    //region Listeners
    override fun onPokemonItemClicked(pokemon: PokemonMinified) {
        viewModel?.onPokemonItemClicked(this, pokemon)
    }
    //endregion
}

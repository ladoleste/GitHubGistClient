package br.com.ladoleste.githubgistclient.features.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.getErrorMessage
import br.com.ladoleste.githubgistclient.databinding.FragmentFavoritesBinding
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.ItemClick
import br.com.ladoleste.githubgistclient.features.detail.DetailsActivity

class FavoritesFragment : Fragment(), ItemClick {
    override fun onItemClick(gist: Gist) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", gist.id)
        startActivity(intent)
    }

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var model: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(FavoritesViewModel::class.java)
        binding.model = model

        model.gists.observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })

        model.gistsError.observe(this, Observer(this::handleError))
    }

    override fun onResume() {
        super.onResume()
        model.loadFavorites()
    }

    private fun handleError(it: Throwable?) {
        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    model.loadFavorites()
                }
                .show()
    }

    private fun showList(it: List<Gist>?) {

        it?.let {
            if (binding.rvListing.adapter == null) {
                favoritesAdapter = FavoritesAdapter(it, this)
                binding.rvListing.adapter = favoritesAdapter
            } else {
                favoritesAdapter.updateItems(it)
            }
        }
    }
}
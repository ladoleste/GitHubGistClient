package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.getErrorMessage
import br.com.ladoleste.githubgistclient.databinding.FragmentMainBinding
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.common.EndlessRecyclerViewScrollListener
import br.com.ladoleste.githubgistclient.features.common.ItemClick
import br.com.ladoleste.githubgistclient.features.detail.DetailsActivity

class MainFragment : Fragment(), ItemClick {
    override fun onItemClick(gist: Gist) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", gist.id)
        startActivity(intent)
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var gistAdapter: GistAdapter
    private lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
//        binding.rvListing.setHasFixedSize(true)
        binding.rvListing.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                model.loadGists(page)
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.model = model

        model.gists.observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })

        model.gistsError.observe(this, Observer(this::handleError))
    }

    override fun onResume() {
        super.onResume()
        model.loadGists()
    }

    private fun handleError(it: Throwable?) {
        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    model.loadGists()
                }
                .show()
    }

    private fun showList(it: List<Gist>?) {

        it?.let {
            if (binding.rvListing.adapter == null) {
                gistAdapter = GistAdapter(it, this)
                binding.rvListing.adapter = gistAdapter
            } else {
                gistAdapter.updateItems(it)
            }
        }
    }
}
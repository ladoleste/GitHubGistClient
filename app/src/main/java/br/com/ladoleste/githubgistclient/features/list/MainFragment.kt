package br.com.ladoleste.githubgistclient.features.list

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
import br.com.ladoleste.githubgistclient.databinding.FragmentMainBinding
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.detail.DetailsActivity

class MainFragment : Fragment(), ItemClick {
    override fun onItemClick(gist: Gist) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", gist.id)
        startActivity(intent)
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var gistAdapter: GistAdapter
    private lateinit var loadingScrollListener: LoadingScrollListener
    private var loadingEnabled = false
    private lateinit var model: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        var page = 1
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        binding.rvListing.setHasFixedSize(true)
        loadingScrollListener = LoadingScrollListener({
            model.loadGists(++page)
        }, linearLayoutManager)
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
        binding.rvListing.clearOnScrollListeners()
        if (loadingEnabled)
            binding.rvListing.addOnScrollListener(loadingScrollListener)

        it?.let {
            if (binding.rvListing.adapter == null) {
                gistAdapter = GistAdapter(it, this)
                binding.rvListing.adapter = gistAdapter
                loadingScrollListener.loading = false
            } else {
                loadingScrollListener.loading = false
                gistAdapter.updateItems(it)
            }
        }
    }
}
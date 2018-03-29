package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.ladoleste.githubgistclient.databinding.FragmentMainBinding
import br.com.ladoleste.githubgistclient.dto.Gist

class MainFragment : Fragment(), ItemClick {
    override fun onItemClick(id: String, image: ImageView?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var gistAdapter: GistAdapter
    private lateinit var loadingScrollListener: LoadingScrollListener
    private var loadingEnabled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvListing.layoutManager = linearLayoutManager
        loadingScrollListener = LoadingScrollListener({
            //presenter.loadGist(false)
        }, linearLayoutManager)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding.model = model

        model.gists.observe(this, Observer {
            binding.loading.visibility = View.GONE
            showList(it)
        })

        model.loadGists()
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
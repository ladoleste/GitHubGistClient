package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.getErrorMessage
import br.com.ladoleste.githubgistclient.common.toString
import br.com.ladoleste.githubgistclient.dto.GistResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.inc_toolbar.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navigation.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)

        viewModel.gists.observe(this, Observer {
            loading.visibility = View.GONE
            showList(it)
        })

        viewModel.gistsError.observe(this, Observer {
            loading.visibility = View.GONE
            handleError(it)
        })
    }

    private fun handleError(it: Throwable?) {
        Snackbar.make(root_view, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    loading.visibility = View.VISIBLE
                    viewModel.loadGists()
                }
                .show()
    }

    private fun showList(it: List<GistResponse>?) {
        it?.let {
            message.text = it.first().createdAt.toString("dd/MM/yyyy HH:mm")
            Timber.d(it.first().toString())
        } ?: Timber.d("Empty")
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGists()
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return true
            }
        }
        return false
    }
}

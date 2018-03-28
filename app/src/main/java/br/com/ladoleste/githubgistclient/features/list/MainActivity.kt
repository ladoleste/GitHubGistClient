package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.toString
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navigation.setOnNavigationItemSelectedListener(this)

        viewModel.gists.observe(this, Observer {
            it?.let {
                message.text = it.first().createdAt.toString("dd/MM/yyyy HH:mm")
                Timber.d(it.first().toString())
            } ?: Timber.d("Empty")
        })

        viewModel.gistsError.observe(this, Observer {
            Timber.e(it)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGists()
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

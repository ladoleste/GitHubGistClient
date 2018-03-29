package br.com.ladoleste.githubgistclient.features.list

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.addFragment
import br.com.ladoleste.githubgistclient.common.replaceFragment
import br.com.ladoleste.githubgistclient.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.inc_toolbar.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private val fragMain = MainFragment()
    private val fragFav = FavoritesFragment()
    private val fragAbout = AboutFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navigation.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)

        addFragment(fragMain, R.id.container)

        binding.setLifecycleOwner(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGists()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(fragMain, R.id.container)
                return true
            }
            R.id.navigation_favorites -> {
                replaceFragment(fragFav, R.id.container)
                return true
            }
            R.id.navigation_about -> {
                replaceFragment(fragAbout, R.id.container)
                return true
            }
        }
        return false
    }
}

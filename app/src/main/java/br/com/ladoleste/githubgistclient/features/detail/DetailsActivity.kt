package br.com.ladoleste.githubgistclient.features.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.Util.getBitmapFromVectorDrawable
import br.com.ladoleste.githubgistclient.common.getErrorMessage
import br.com.ladoleste.githubgistclient.databinding.ActivityDetailsBinding
import br.com.ladoleste.githubgistclient.features.detail.customtabs.CustomTabsHelper

class DetailsActivity : AppCompatActivity() {

    private lateinit var model: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        model = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.model = model
        binding.setLifecycleOwner(this)
        setSupportActionBar(binding.incToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val backArrow = getBitmapFromVectorDrawable(R.drawable.ic_arrow_back_white, this)

        val customTabsIntent = CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setShowTitle(true)
                .setCloseButtonIcon(backArrow)
                .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
                .build()

        model.files.observe(this, Observer {
            it?.let {

                CustomTabsHelper().mayLaunchUrl(Uri.parse(it[it.keys.first()]?.rawUrl))

                binding.llContainer.removeAllViews()

                it.forEach {
                    val file = it.value
                    val btFile = layoutInflater.inflate(R.layout.item_button, binding.llContainer, false) as Button
                    btFile.setOnClickListener {
                        CustomTabsHelper.openCustomTab(this, customTabsIntent, Uri.parse(file.rawUrl), null)
                    }
                    btFile.text = it.key
                    binding.llContainer.addView(btFile)
                }
            }

            binding.loading.visibility = View.GONE
        })

        binding.swFavorites.setOnClickListener {
            model.addToFavorites()
        }
        model.gistError.observe(this, Observer(this::handleError))

    }

    override fun onResume() {
        super.onResume()
        model.loadGist((intent.getStringExtra("id")))
//        model.loadGist("8fcccd4d54ec298e3b120cbc1230c02a")
    }

    private fun handleError(it: Throwable?) {
        Snackbar.make(binding.rootView, it.getErrorMessage(), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    model.loadGist((intent.getStringExtra("id")))
                }
                .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}
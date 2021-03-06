package br.com.ladoleste.githubgistclient.features.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.Util
import br.com.ladoleste.githubgistclient.databinding.FragmentAboutBinding


/**
 * A placeholder fragment containing a simple view.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.webView.loadData(Util.readRawFile(R.raw.about), "text/html; charset=UTF-8", null)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.loading.visibility = View.GONE
            }
        }

        return binding.root
    }
}

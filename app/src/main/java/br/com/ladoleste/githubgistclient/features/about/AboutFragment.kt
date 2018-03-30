package br.com.ladoleste.githubgistclient.features.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.databinding.FragmentAboutBinding


/**
 * A placeholder fragment containing a simple view.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)
        var html = ""
        try {
            val res = resources
            val ins = res.openRawResource(R.raw.about)
            val b = ByteArray(ins.available())
            ins.read(b)
            html = String(b)
        } catch (_: Exception) {

        }

        binding.webView.loadData(html, "text/html; charset=UTF-8", null)

        return binding.root
    }
}

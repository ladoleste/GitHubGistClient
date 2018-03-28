package br.com.ladoleste.githubgistclient.features.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ladoleste.githubgistclient.R
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainFragment : Fragment() {

    private lateinit var mainView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_main, container, false)
        return mainView
    }

    fun showMessage(msg: String) {
        mainView.message.text = msg
    }
}

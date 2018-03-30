package br.com.ladoleste.githubgistclient.features.common

import br.com.ladoleste.githubgistclient.dto.Gist

/**
 *Created by Anderson on 15/02/2018.
 */
interface ItemClick {
    fun onItemClick(gist: Gist)
}
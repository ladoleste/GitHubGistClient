package br.com.ladoleste.githubgistclient.repository.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.ladoleste.githubgistclient.dto.Gist

/**
 * Created by Anderson on 29/03/2018.
 */
@Dao
interface GistDao {
    @Query("SELECT * FROM gist")
    fun loadFavoriteGists(): List<Gist>

    @Insert
    fun insert(gist: Gist)
}
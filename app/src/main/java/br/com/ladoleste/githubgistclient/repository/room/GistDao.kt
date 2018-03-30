package br.com.ladoleste.githubgistclient.repository.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.ladoleste.githubgistclient.dto.Gist

/**
 * Created by Anderson on 29/03/2018.
 */
@Dao
interface GistDao {
    @Query("SELECT * FROM gist")
    fun loadFavoriteGists(): LiveData<List<Gist>>

    @Query("SELECT EXISTS(SELECT 1 FROM gist WHERE id = :id LIMIT 1)")
    fun isFavorite(id: String): Boolean

    @Insert(onConflict = REPLACE)
    fun insert(gist: Gist)

    @Delete
    fun delete(gist: Gist)
}
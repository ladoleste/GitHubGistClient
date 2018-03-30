package br.com.ladoleste.githubgistclient.repository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import br.com.ladoleste.githubgistclient.dto.Gist

/**
 * Created by Anderson on 29/03/2018.
 */
@Database(entities = [(Gist::class)], version = 2)
@TypeConverters(MyConverters::class)
abstract class MyDatabase : RoomDatabase() {
    internal abstract fun gistDao(): GistDao
}

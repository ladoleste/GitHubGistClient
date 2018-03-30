package br.com.ladoleste.githubgistclient.tests

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.ladoleste.githubgistclient.dto.Gist
import br.com.ladoleste.githubgistclient.features.list.MainActivity
import br.com.ladoleste.githubgistclient.repository.room.MyDatabase
import br.com.ladoleste.githubgistclient.utils.blockingObserver
import org.junit.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomTest {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private lateinit var db: MyDatabase

    @Before
    fun init() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), MyDatabase::class.java).build()
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun testAddAndRemoveToFavorite() {
        val gist = Gist()
        gist.id = "abc"

        db.gistDao().insert(gist)

        Assert.assertTrue(db.gistDao().isFavorite(gist.id))

        db.gistDao().delete(gist)

        Assert.assertFalse(db.gistDao().isFavorite(gist.id))
    }

    @Test
    fun testLoadFavorites() {

        val gist1 = Gist()
        gist1.id = "gist1"

        val gist2 = Gist()
        gist2.id = "another id"

        val gist3 = Gist()
        gist3.id = "this is just an item"

        db.gistDao().insert(gist1)
        db.gistDao().insert(gist2)
        db.gistDao().insert(gist3)

        val list = db.gistDao().loadFavoriteGists().blockingObserver()!!

        Assert.assertEquals(3, list.size)
    }
}

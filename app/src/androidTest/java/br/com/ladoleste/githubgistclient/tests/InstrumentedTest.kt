package br.com.ladoleste.githubgistclient.tests

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.ladoleste.githubgistclient.R
import br.com.ladoleste.githubgistclient.common.CustomApplication.Companion.apiUrl
import br.com.ladoleste.githubgistclient.features.list.MainActivity
import br.com.ladoleste.githubgistclient.utils.readFromAssets
import br.com.ladoleste.githubgistclient.utils.withRecyclerView
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun test_general_navigation() {

        val server = MockWebServer()
        val gists = readFromAssets(InstrumentationRegistry.getContext(), "gists.json")
        val gist = readFromAssets(InstrumentationRegistry.getContext(), "gist.json")

        server.enqueue(MockResponse().setBody(gists))
        server.enqueue(MockResponse().setBody(gists))
        server.enqueue(MockResponse().setBody(gist))
        server.start()

        apiUrl = server.url("/test/").toString()

        activityRule.launchActivity(Intent())

        onView(withText(R.string.app_name)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_favorites)).perform(click())
        onView(withText(R.string.no_favorites)).check(matches(isDisplayed()))

        onView(withId(R.id.navigation_about)).perform(click())

        onView(withId(R.id.navigation_home)).perform(click())
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.rv_listing).atPosition(1))
                .check(matches(hasDescendant(withText("Minimum Heap with Decrease Key In Javascript"))))

        onView(withRecyclerView(R.id.rv_listing).atPosition(0))
                .perform(click())

        onView(withId(R.id.sw_favorites)).perform(click())

        pressBack()

        onView(withId(R.id.navigation_favorites)).perform(click())

        onView(withRecyclerView(R.id.rv_listing).atPosition(0))
                .check(matches(hasDescendant(withText("RxJava"))))
    }
}

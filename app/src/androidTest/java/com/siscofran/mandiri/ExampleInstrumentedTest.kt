package com.siscofran.mandiri

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.siscofran.mandiri.ui.main.MainActivity
import com.siscofran.mandiri.ui.main.MainAdapter
import org.hamcrest.CoreMatchers.anything
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.siscofran.mandiri", appContext.packageName)
    }

    @Test
    fun genreList(){
        SystemClock.sleep(3000)
        onView(withId(R.id.rv_genre)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        SystemClock.sleep(1000)
        pressBack()
        SystemClock.sleep(1500)
        val recyclerView = mActivityTestRule.activity.findViewById<RecyclerView>(R.id.rv_genre)
        val itemCount = recyclerView.adapter!!.itemCount
        onView(withId(R.id.rv_genre)).perform(
            RecyclerViewActions.scrollToPosition<MainAdapter.GenreViewHolder>(
                itemCount - 5
            )
        )
    }
}
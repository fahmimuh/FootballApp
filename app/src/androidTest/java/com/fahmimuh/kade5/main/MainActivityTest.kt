package com.fahmimuh.kade5.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.fahmimuh.kade5.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(nav)).check(matches(isDisplayed()))
        onView(withId(matches)).perform(click())
        Thread.sleep(2000)
        onView(withId(rvLast)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(rvLast)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(rvLast)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

    }

}

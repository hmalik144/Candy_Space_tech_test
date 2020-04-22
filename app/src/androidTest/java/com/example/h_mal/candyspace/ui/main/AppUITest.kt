package com.example.h_mal.candyspace.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.h_mal.candyspace.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AppUITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun launchApp_SuccessfulLaunch() {
        onView(allOf(withId(R.id.action_bar), isDisplayed())).check(matches(withText("Candy Space")))
        onView(allOf(withId(R.id.submit), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.search_bar), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.recycler_view), isDisplayed())).check(matches(isDisplayed()))

    }

    @Test
    fun RunSearch_SuccessfulReturn() {
        onView(allOf(withId(R.id.submit), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.search_bar), isDisplayed())).perform(replaceText("kenny"),
            closeSoftKeyboard())
        onView(allOf(withId(R.id.submit), isDisplayed())).perform(click())
        //wait for load
        waitFor(2000)

        onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.recycler_view),
                        childAtPosition(
                            withId(R.id.main),
                            1
                        )
                    ),
                    0
                )
            )
        ).perform(click())

        onView(allOf(withId(R.id.action_bar), isDisplayed())).check(matches(withText("User")))
        onView(allOf(withId(R.id.username), isDisplayed())).check(matches(isDisplayed()))

    }

    @Test
    fun backButtonPressed_SuccessfulResponse() {
        onView(allOf(withId(R.id.submit), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.search_bar), isDisplayed())).perform(replaceText("kenny"),
            closeSoftKeyboard())
        onView(allOf(withId(R.id.submit), isDisplayed())).perform(click())
        //wait for load
        waitFor(2000)

        onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.recycler_view),
                        childAtPosition(
                            withId(R.id.main),
                            1
                        )
                    ),
                    0
                )
            )
        ).perform(click())

        onView(allOf(withId(R.id.action_bar), isDisplayed())).check(matches(withText("User")))

        onView(childAtPosition(withId(R.id.action_bar_container), 0)).perform(click())
        onView(allOf(withId(R.id.action_bar), isDisplayed())).check(matches(withText("Candy Space")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}

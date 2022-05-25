package com.example.careshipapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import com.example.careshipapp.gui.activities.StaffLoginActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StaffLoginActivityTest {

    @Rule
    public ActivityScenarioRule<StaffLoginActivity> activityRule =
            new ActivityScenarioRule<StaffLoginActivity>(StaffLoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void testElementsVisibility() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withText("LOG IN")).check(matches(isDisplayed()));
        onView(withId(R.id.staffEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.staffPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.staffPassword)).check(matches(isDisplayed()));
    }

    @Test
    public void testElementsPosition() {
        onView(withId(R.id.signIn)).check(isCompletelyAbove(withId(R.id.staffEmail)));
        onView(withId(R.id.staffEmail)).check(isCompletelyAbove(withId(R.id.staffPassword)));
        onView(withId(R.id.staffPassword)).check(isCompletelyAbove(withId(R.id.loginButton)));
        onView(withId(R.id.loginButton)).check(isCompletelyAbove(withId(R.id.forgetPassword)));

    }

    @Test
    public void testElementsFunctionality() {
        onView(allOf(withId(R.id.staffEmail), hasErrorText("Email field is empty")));
        onView(allOf(withId(R.id.staffPassword), hasErrorText("Password field is empty")));

        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginButton)).check(matches(isClickable()));


    }
    @Test
    public void testEmptyInput() {
        onView(withId(R.id.staffEmail)).perform(typeText(""));
        onView(withId(R.id.staffPassword)).perform(typeText(""));
        onView(withId(R.id.loginButton)).perform(click());
    }





}

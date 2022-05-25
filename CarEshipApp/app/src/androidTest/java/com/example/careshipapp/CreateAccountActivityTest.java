package com.example.careshipapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.careshipapp.gui.activities.CreateAccountActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


    @RunWith(AndroidJUnit4.class)


    public class CreateAccountActivityTest {

    @Rule
    ActivityScenarioRule <CreateAccountActivity> activityRule =
                new ActivityScenarioRule<>(CreateAccountActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void verifyLoadedElements() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.signIn)).check(matches(isDisplayed()));
        onView(withId(R.id.Username)).check(matches(isDisplayed()));
        onView(withId(R.id.CreatePassword)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordReentry)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateAccountButton)).check(matches(isDisplayed()));
        onView(withId(R.id.postAddress)).check(matches(isDisplayed()));
        onView(withId(R.id.zipCode)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView14)).check(matches(isDisplayed()));

    }

    @Test
    public void verifyElementsPositions() {
        //Using R.id.UserNAme as the relative position of comparison,
        onView(withId(R.id.imageView14)).check(isCompletelyAbove(withId(R.id.Username)));
        onView(withId(R.id.signIn)).check(isCompletelyAbove(withId(R.id.Username)));
        onView(withId(R.id.imageView)).check(isCompletelyAbove(withId(R.id.Username)));
        onView(withId(R.id.Username)).check(isCompletelyBelow(withId(R.id.signIn)));
        onView(withId(R.id.CreatePassword)).check(isCompletelyBelow(withId(R.id.Username)));
        onView(withId(R.id.passwordReentry)).check(isCompletelyBelow(withId(R.id.Username)));
        onView(withId(R.id.CreateAccountButton)).check(isCompletelyBelow(withId(R.id.Username)));
        onView(withId(R.id.postAddress)).check(isCompletelyBelow(withId(R.id.Username)));
        onView(withId(R.id.zipCode)).check(isCompletelyBelow(withId(R.id.Username)));
        onView(withId(R.id.postAddress)) .check(isCompletelyLeftOf(withId(R.id.zipCode)));

    }

    @Test
    public void testUserNAmeInput() {
        onView(withId(R.id.Username)).perform(typeText("Akuien@CarEship.com"));
        onView(withId(R.id.Username)).perform(clearText());
    }

    @Test
    public void testPasswordInput() {
        onView(withId(R.id.CreatePassword)).perform(typeText("Akuien@CarEship.com"));
        onView(withId(R.id.CreatePassword)).perform(clearText());

        onView(withId(R.id.passwordReentry)).perform(typeText("Akuien@CarEship.com"));
        onView(withId(R.id.passwordReentry)).perform(clearText());
    }

    @Test
    public void testAddressInput() {
        onView(withId(R.id.postAddress)).perform(typeText("Brunnsparken 12"));
        onView(withId(R.id.postAddress)).perform(clearText());
    }

    @Test
    public void testZipInput() {
        onView(withId(R.id.zipCode)).perform(typeText("152684"));
        onView(withId(R.id.zipCode)).perform(clearText());
    }

        @Test
        public void testButtonFunctionality() {
            onView(withId(R.id.CreateAccountButton)).perform(click());
            onView(withId(R.id.CreateAccountButton)).check(matches(isClickable()));

            onView(withId(R.id.ForgetPassword)).check(matches(isClickable()));












































































































































        }
}

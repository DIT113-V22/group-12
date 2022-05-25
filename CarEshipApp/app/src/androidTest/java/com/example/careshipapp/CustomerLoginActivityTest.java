package com.example.careshipapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.careshipapp.gui.activities.CustomerLoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class CustomerLoginActivityTest {

    @Rule
    ActivityScenarioRule <CustomerLoginActivity> activityRule =
            new ActivityScenarioRule<>(CustomerLoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void checkLoadedElements() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.signIn)).check(matches(isDisplayed()));
        onView(withId(R.id.Email)).check(matches(isDisplayed()));
        onView(withId(R.id.password2)).check(matches(isDisplayed()));
        onView(withId(R.id.LoginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateAccountButton2)).check(matches(isDisplayed()));
        onView(withId(R.id.ForgetPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView17)).check(matches(isDisplayed()));

    }

    @Test
    public void verifyPositionsOfElements() {
        onView(withId(R.id.imageView)).check(isCompletelyAbove(withId(R.id.password2)));
        onView(withId(R.id.signIn)).check(isCompletelyAbove(withId(R.id.password2)));
        onView(withId(R.id.Email)).check(isCompletelyAbove(withId(R.id.password2)));

        onView(withId(R.id.password2)).check(isCompletelyBelow(withId(R.id.Email)));

        onView(withId(R.id.LoginButton)).check(isCompletelyBelow(withId(R.id.password2)));
        onView(withId(R.id.CreateAccountButton2)).check(isCompletelyBelow(withId(R.id.password2)));
        onView(withId(R.id.ForgetPassword)).check(isCompletelyBelow(withId(R.id.password2)));
        onView(withId(R.id.imageView17)).check(isCompletelyBelow(withId(R.id.password2)));
    }


    @Test
    public void verifyEmailInput() {
        onView(withId(R.id.Email)).perform(typeText("Magnus@CarEship.com"));
        onView(withId(R.id.Email)).perform(clearText());
    }

    @Test
    public void verifyPasswordInput() {
        onView(withId(R.id.password2)).perform(typeText("Lasagne"));
        onView(withId(R.id.password2)).perform(clearText());
    }

    @Test
    public void testButtons() {
        onView(withId(R.id.LoginButton)).perform(click());
        onView(withId(R.id.LoginButton)).check(matches(isClickable()));

        onView(withId(R.id.CreateAccountButton2)).perform(click());

    }
}

package com.example.careshipapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;



import android.content.Context;

import androidx.test.espresso.intent.Intents;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.careshipapp.car_control.JoystickMainActivity;


import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class JoystickMainActivityTest {

    @Rule
    public ActivityScenarioRule<JoystickMainActivity> activityRule =
            new ActivityScenarioRule<>(JoystickMainActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void testElementsVisibility() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.orderid_joystick)).check(matches(isDisplayed()));
    }

    @Test
    public void checkJoystickMovement(){
        onView(withId(R.id.joystickView)).check(matches(isDisplayed()));
        onView(withId(R.id.joystickView)).perform(swipeUp());
        onView(withId(R.id.joystickView)).perform(swipeDown());
        onView(withId(R.id.joystickView)).perform(swipeLeft());
        onView(withId(R.id.joystickView)).perform(swipeRight());
    }

    @Test
    public void checkAutoPilotSwitch(){
        onView(withId(R.id.autopilot)).check(matches(isDisplayed()));
        onView(withId(R.id.autopilot)).check(matches(isClickable()));
    }

    @Test
    public void checkCallButton(){
        onView(withId(R.id.phone_call)).check(matches(isDisplayed()));
        onView(withId(R.id.phone_call)).check(matches(isClickable()));
    }

    @Test
    public void checkMapButton(){
        onView(withId(R.id.map_careship)).check(matches(isDisplayed()));
        onView(withId(R.id.map_careship)).check(matches(isClickable()));
    }

    @Test
    public void checkDeliverButton(){
        onView(withId(R.id.deliver_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.deliver_btn)).check(matches(isClickable()));
    }

    @After
    public void tearDown() {
        activityRule = null;
        Intents.release();
    }

}

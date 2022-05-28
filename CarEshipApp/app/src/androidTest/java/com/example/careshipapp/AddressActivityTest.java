package com.example.careshipapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;


import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.careshipapp.gui.activities.AddressActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class AddressActivityTest {
    @Rule
    public ActivityScenarioRule<AddressActivity> activityRule =
            new ActivityScenarioRule<>(AddressActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void testOtherElementsVisibility() {
        onView(withId(R.id.orderConfirmation)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView20)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView9)).check(matches(isDisplayed()));
        onView(withId(R.id.textView22)).check(matches(isDisplayed()));

    }


    @Test
    public void testPostAddressEntry(){
        onView(withId(R.id.order_id_address)).check(matches(isDisplayed()));
        onView(withId(R.id.order_id_address)).perform(typeText("Kuggen 45"));
        onView(withId(R.id.order_id_address)).perform(clearText());
    }

    @Test
    public void testPostCodeEntry(){
        onView(withId(R.id.postcode)).check(matches(isDisplayed()));
        onView(withId(R.id.postcode)).perform(typeText("48296"));
        onView(withId(R.id.postcode)).perform(clearText());
    }

    @Test
    public void testContactNumberEntry(){
        onView(withId(R.id.contact_number)).check(matches(isDisplayed()));
        onView(withId(R.id.contact_number)).perform(typeText("0700357212"));
        onView(withId(R.id.contact_number)).perform(clearText());
    }

    @Test
    public void testSaveButton(){
        onView(withId(R.id.save_address)).check(matches(isDisplayed()));
        onView(withId(R.id.save_address)).check(matches(isClickable()));
    }

}


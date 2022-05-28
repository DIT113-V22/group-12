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

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.careshipapp.gui.activities.CustomerPaymentActivity;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class CustomerPaymentActivityTest {

    @Rule
    public ActivityScenarioRule<CustomerPaymentActivity> activityRule =
            new ActivityScenarioRule<>(CustomerPaymentActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.careshipapp", appContext.getPackageName());
    }

    @Test
    public void testElementsVisibility() {
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView7)).check(matches(isDisplayed()));
        onView(withId(R.id.payment_orderid)).check(matches(isDisplayed()));
        onView(withId(R.id.CompleteButton)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView9)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView10)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView25)).check(matches(isDisplayed()));
        onView(withId(R.id.subTotal)).check(matches(isDisplayed()));

    }

    @Test
    public void verifyAccountNumberInput() {
        onView(withId(R.id.accountNumber)).check(matches(isDisplayed()));
        onView(withId(R.id.accountNumber)).perform(typeText("1985469256"));
        onView(withId(R.id.accountNumber)).perform(clearText());
    }

    @Test
    public void verifyCvvInput() {
        onView(withId(R.id.CCV)).check(matches(isDisplayed()));
        onView(withId(R.id.CCV)).perform(typeText("123"));
        onView(withId(R.id.CCV)).perform(clearText());
    }

    @Test
    public void verifyCardHolderInput() {
        onView(withId(R.id.personName)).check(matches(isDisplayed()));
        onView(withId(R.id.personName)).perform(typeText("Antonio"));
        onView(withId(R.id.personName)).perform(clearText());
    }

    @Test
    public void testExpiryDatePickerButton(){
        onView(withId(R.id.expiryDatePickerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.expiryDatePickerBtn)).check(matches(isClickable()));
    }

    @Test
    public void testCompleteButtonButton(){
        onView(withId(R.id.CompleteButton)).check(matches(isDisplayed()));
        onView(withId(R.id.CompleteButton)).check(matches(isClickable()));
    }

    @After
    public void tearDown() {
        activityRule = null;
        Intents.release();
    }
}

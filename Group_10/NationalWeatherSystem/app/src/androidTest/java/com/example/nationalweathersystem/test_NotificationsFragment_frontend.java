package com.example.nationalweathersystem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class test_NotificationsFragment_frontend {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNotificationsFragmentDisplayed() {
        // Launch MainActivity, which contains NotificationsFragment
        activityRule.launchActivity(new Intent());

        // Click on the notifications navigation item
        onView(withId(R.id.navigation_notifications)).perform(click());

        // Verify that NotificationsFragment is displayed with correct text
        onView(withId(R.id.text_notifications)).check(matches(withText("This is notifications fragment")));
    }
}

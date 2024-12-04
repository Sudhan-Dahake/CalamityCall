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
public class test_ReportFragment_frontend {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testReportFragmentDisplayed() {
        // Launch MainActivity, which contains ReportFragment
        activityRule.launchActivity(new Intent());

        // Click on the report navigation item
        onView(withId(R.id.navigation_report)).perform(click());

        // Verify that ReportFragment is displayed with correct text
        onView(withId(R.id.text_report)).check(matches(withText("This is report fragment")));
    }

    // Edge Test Cases

    @Test
    public void testReportFragmentWithInvalidInput() {
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.navigation_report)).perform(click());
        
        // Simulate entering invalid data in the report form
        onView(withId(R.id.edit_text_report_title)).perform(typeText(""));
        onView(withId(R.id.button_submit_report)).perform(click());
        
        // Check if an error message is displayed
        onView(withId(R.id.text_error_message)).check(matches(withText("Please enter a valid title")));
    }

    @Test
    public void testReportFragmentWithLargeDataInput() {
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.navigation_report)).perform(click());
        
        // Simulate entering a very large amount of data
        String largeText = new String(new char[10000]).replace("\0", "A");
        onView(withId(R.id.edit_text_report_description)).perform(typeText(largeText));
        onView(withId(R.id.button_submit_report)).perform(click());
        
        // Check if the large input is handled properly (e.g., truncated or error shown)
        onView(withId(R.id.text_error_message)).check(matches(withText("Description too long")));
    }
}

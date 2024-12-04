package com.example.nationalweathersystem;

import android.content.Intent;

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
public class test_MainActivity_frontend {

   @Rule
   public ActivityTestRule<MainActivity> activityRule =
       new ActivityTestRule<>(MainActivity.class, true, false);

   @Test
   public void testActivityLaunch() {
       Intent intent = new Intent();
       activityRule.launchActivity(intent);
       // Check if the main view is displayed
       onView(withId(R.id.nav_view)).check(matches(isDisplayed()));
   }

   @Test
   public void testNavigationToReportFragment() {
       Intent intent = new Intent();
       activityRule.launchActivity(intent);
       
       // Click on the report navigation item
       onView(withId(R.id.navigation_report)).perform(click());
       
       // Verify that the ReportFragment is displayed
       onView(withId(R.id.text_report)).check(matches(withText("This is report fragment")));
   }

   @Test
   public void testNavigationToBoardsFragment() {
       Intent intent = new Intent();
       activityRule.launchActivity(intent);
       
       // Click on the boards navigation item
       onView(withId(R.id.navigation_boards)).perform(click());
       
       // Verify that the BoardsFragment is displayed
       onView(withId(R.id.text_boards)).check(matches(withText("This is boards fragment")));
   }

   @Test
   public void testNavigationToNotificationsFragment() {
       Intent intent = new Intent();
       activityRule.launchActivity(intent);
       
       // Click on the notifications navigation item
       onView(withId(R.id.navigation_notifications)).perform(click());
       
       // Verify that the NotificationsFragment is displayed
       onView(withId(R.id.text_notifications)).check(matches(withText("This is notifications fragment")));
   }

    // Edge Test Cases

   @Test
    public void testRapidNavigationBetweenFragments() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        
        // Rapidly switch between fragments
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.navigation_report)).perform(click());
            onView(withId(R.id.navigation_boards)).perform(click());
            onView(withId(R.id.navigation_notifications)).perform(click());
        }
        
        // Verify that the last clicked fragment is displayed correctly
        onView(withId(R.id.text_notifications)).check(matches(withText("This is notifications fragment")));
    }

    @Test
    public void testBackStackBehavior() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        
        // Navigate through all fragments
        onView(withId(R.id.navigation_report)).perform(click());
        onView(withId(R.id.navigation_boards)).perform(click());
        onView(withId(R.id.navigation_notifications)).perform(click());
        
        // Press back button multiple times
        pressBack();
        pressBack();
        pressBack();
        
        // Verify that we're back at the initial fragment (assuming it's the report fragment)
        onView(withId(R.id.text_report)).check(matches(withText("This is report fragment")));
    }
}

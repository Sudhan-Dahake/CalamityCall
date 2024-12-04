package com.example.nationalweathersystem;

import android.content.Intent;

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
public class test_BoardsFragment_frontend {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBoardsFragmentDisplayed() {
        // Launch MainActivity, which contains BoardsFragment
        activityRule.launchActivity(new Intent());

        // Click on the boards navigation item
        onView(withId(R.id.navigation_boards)).perform(click());

        // Verify that BoardsFragment is displayed with correct text
        onView(withId(R.id.text_boards)).check(matches(withText("This is boards fragment")));
    }

    // Edge Test Cases

    @Test
    public void testBoardsFragmentWithLongText() {
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.navigation_boards)).perform(click());
        
        // Simulate setting a very long text
        String longText = new String(new char[1000]).replace("\0", "A");
        onView(withId(R.id.text_boards)).perform(replaceText(longText));
        
        // Check if the text is truncated or handled properly
        onView(withId(R.id.text_boards)).check(matches(isDisplayed()));
    }

    @Test
    public void testBoardsFragmentWithEmptyText() {
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.navigation_boards)).perform(click());
        
        // Simulate setting an empty text
        onView(withId(R.id.text_boards)).perform(replaceText(""));
        
        // Check if the empty text is handled properly
        onView(withId(R.id.text_boards)).check(matches(withText("")));
    }
}

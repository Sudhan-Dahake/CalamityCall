//package com.example.calamitycall;
//
//import android.content.Intent;
//import android.content.Context;
//
//import androidx.test.espresso.intent.Intents;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import com.example.calamitycall.models.login.LoginRequest;
//import com.example.calamitycall.models.login.LoginResponse;
//import com.example.calamitycall.network.auth.LoginService;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static org.mockito.Mockito.*;
//
//@RunWith(AndroidJUnit4.class)
//public class LoginActivityTest {
//
//    @Rule
//    public ActivityScenarioRule<LoginActivity> activityRule =
//            new ActivityScenarioRule<>(LoginActivity.class);
//
//    private LoginService mockLoginService;
//
//    @Before
//    public void setUp() {
//        // Initialize Intents for capturing navigation
//        Intents.init();
//
//        // Create a mock LoginService
//        mockLoginService = mock(LoginService.class);
//
//        // Inject the mock service into LoginActivity using ActivityScenario
//        activityRule.getScenario().onActivity(activity -> {
//            activity.loginService = mockLoginService;
//        });
//    }
//
//    @After
//    public void tearDown() {
//        // Release Intents
//        Intents.release();
//    }
//
//    @Test
//    public void testLoginScreenElementsDisplayed() {
//        // Verify that all UI elements are displayed
//        onView(withId(R.id.editTextUsername)).check(matches(isDisplayed()));
//        onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()));
//        onView(withId(R.id.registerNowText)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testSuccessfulLogin() {
//        // Arrange: Mock successful login response
//        doAnswer(invocation -> {
//            LoginService.LoginCallback callback = invocation.getArgument(1);
//            LoginResponse response = new LoginResponse();
//            response.setAccessToken("fakeAccessToken");
//            response.setRefreshToken("fakeRefreshToken");
//            callback.onSuccess(response);
//            return null;
//        }).when(mockLoginService).login(any(LoginRequest.class), any(LoginService.LoginCallback.class));
//
//        // Act: Enter valid credentials and click login
//        onView(withId(R.id.editTextUsername)).perform(typeText("validUser"), closeSoftKeyboard());
//        onView(withId(R.id.editTextPassword)).perform(typeText("validPass"), closeSoftKeyboard());
//        onView(withId(R.id.btnLogin)).perform(click());
//
//        // Assert: Verify that MainActivity is launched
//        intended(hasComponent(MainActivity.class.getName()));
//    }
//
//    @Test
//    public void testFailedLogin() {
//        // Arrange: Mock failed login response
//        doAnswer(invocation -> {
//            LoginService.LoginCallback callback = invocation.getArgument(1);
//            callback.onError(new Exception("Invalid credentials"));
//            return null;
//        }).when(mockLoginService).login(any(LoginRequest.class), any(LoginService.LoginCallback.class));
//
//        // Act: Enter invalid credentials and click login
//        onView(withId(R.id.editTextUsername)).perform(typeText("invalidUser"), closeSoftKeyboard());
//        onView(withId(R.id.editTextPassword)).perform(typeText("invalidPass"), closeSoftKeyboard());
//        onView(withId(R.id.btnLogin)).perform(click());
//
//        // Assert: Verify that MainActivity is not launched
//        Intents.assertNoUnverifiedIntents();
//    }
//
//    @Test
//    public void testRegisterNowLink() {
//        // Act: Click on the "Register now!" link
//        onView(withId(R.id.registerNowText)).perform(click());
//
//        // Assert: Verify that RegisterActivity is launched
//        intended(hasComponent(RegisterActivity.class.getName()));
//    }
//}

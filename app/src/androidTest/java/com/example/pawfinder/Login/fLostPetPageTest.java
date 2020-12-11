package com.example.pawfinder.Login;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.pawfinder.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class fLostPetPageTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void dLostPetPageTest() throws InterruptedException {

        Thread.sleep(2000);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_lost), withContentDescription("Lost"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        Thread.sleep(2000);

        ViewInteraction tabView = onView(
                allOf(withContentDescription("My Lost Pets"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        Thread.sleep(2000);

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.reportPetFloatingButton),
                        withParent(withParent(withId(R.id.main_activity_container))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        Thread.sleep(2000);

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.reportPetFloatingButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_activity_container),
                                        1),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        Thread.sleep(2000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.reportPetText), withText("Report Pet"),
                        withParent(withParent(withId(R.id.reportPetToolBar))),
                        isDisplayed()));
        textView2.check(matches(withText("Report Pet")));

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.backArrow_reportPet),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.reportPetToolBar),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

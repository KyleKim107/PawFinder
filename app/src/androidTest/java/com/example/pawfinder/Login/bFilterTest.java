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
import org.hamcrest.core.IsInstanceOf;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class bFilterTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void bFilterTest() throws InterruptedException {

        Thread.sleep(5000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.filter_icon),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_activity_container),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.sizeText), withText("Size"),
                        withParent(allOf(withId(R.id.relLayoutSize),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Size")));

        Thread.sleep(2000);

        ViewInteraction appCompatToggleButton = onView(
                allOf(withId(R.id.type_cat), withText("Cat"),
                        childAtPosition(
                                allOf(withId(R.id.relLayoutType),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatToggleButton.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatToggleButton2 = onView(
                allOf(withId(R.id.size_medium), withText("Medium"),
                        childAtPosition(
                                allOf(withId(R.id.relLayoutSize),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatToggleButton2.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatToggleButton3 = onView(
                allOf(withId(R.id.gender_female), withText("Female"),
                        childAtPosition(
                                allOf(withId(R.id.relLayoutGender),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatToggleButton3.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatToggleButton4 = onView(
                allOf(withId(R.id.age_adult), withText("Adult"),
                        childAtPosition(
                                allOf(withId(R.id.relLayoutAge),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                3),
                        isDisplayed()));
        appCompatToggleButton4.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.backArrow_filter),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.filterToolBar),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());

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

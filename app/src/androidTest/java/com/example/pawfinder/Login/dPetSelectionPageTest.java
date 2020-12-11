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
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
public class dPetSelectionPageTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void dPetSelectionPageTest() throws InterruptedException {

        Thread.sleep(2000);

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_pets), withContentDescription("Pets"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        Thread.sleep(2000);

        ViewInteraction cardView = onView(
                allOf(withId(R.id.item_layout),
                        childAtPosition(
                                allOf(withId(R.id.card_stack_view),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        cardView.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.backArrow_viewPet),
                        childAtPosition(
                                allOf(withId(R.id.relLayout),
                                        childAtPosition(
                                                withId(R.id.scrollView_view),
                                                0)),
                                1)));
        appCompatImageView.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction imageView = onView(
                allOf(withId(R.id.filter_icon),
                        withParent(withParent(withId(R.id.main_activity_container))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        Thread.sleep(2000);

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.item_layout),
                        childAtPosition(
                                allOf(withId(R.id.card_stack_view),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        cardView2.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.backArrow_viewPet),
                        childAtPosition(
                                allOf(withId(R.id.relLayout),
                                        childAtPosition(
                                                withId(R.id.scrollView_view),
                                                0)),
                                1)));
        appCompatImageView2.perform(scrollTo(), click());
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

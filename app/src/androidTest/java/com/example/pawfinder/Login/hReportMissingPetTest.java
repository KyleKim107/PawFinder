package com.example.pawfinder.Login;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
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
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class hReportMissingPetTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void hReportMissingPetTest() throws InterruptedException {

        Thread.sleep(2000);
        Thread.sleep(2000);
        Thread.sleep(2000);
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

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.reportMissingPetButton), withText("Report Missing Pet"),
                        childAtPosition(
                                allOf(withId(R.id.relLayout4),
                                        childAtPosition(
                                                withId(R.id.relLayout3),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.petname_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout1),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("Test Missing"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.pettypespinner_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout2),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                3)),
                                1)));
        appCompatSpinner.perform(scrollTo(), click());

        Thread.sleep(2000);

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView.perform(click());

        Thread.sleep(2000);

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.genderspinner_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout3),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                4)),
                                1)));
        appCompatSpinner2.perform(scrollTo(), click());

        Thread.sleep(2000);

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatTextView2.perform(click());

        Thread.sleep(2000);

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.relLayout4),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        relativeLayout.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.area_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout5),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                6)),
                                1)));
        appCompatEditText4.perform(scrollTo(), replaceText("Here"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.message_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout6),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                7)),
                                1)));
        appCompatEditText5.perform(scrollTo(), replaceText("Message"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.phonenumber_missing),
                        childAtPosition(
                                allOf(withId(R.id.relLayout8),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                10)),
                                1)));
        appCompatEditText6.perform(scrollTo(), replaceText("18008675309"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.phonenumber_missing), withText("18008675309"),
                        childAtPosition(
                                allOf(withId(R.id.relLayout8),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                10)),
                                1)));
        appCompatEditText7.perform(pressImeActionButton());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.reportMissingPetCheckmark),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.reportMissingPetToolBar),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView.perform(click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.changepetphoto_missing), withText("Change Pet's Photo"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(matches(withText("Change Pet's Photo")));

        Thread.sleep(2000);

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.backArrow_reportMissingPet),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.reportMissingPetToolBar),
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

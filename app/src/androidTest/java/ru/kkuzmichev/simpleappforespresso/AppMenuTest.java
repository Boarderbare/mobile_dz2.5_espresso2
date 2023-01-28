package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
//@RunWith(AllureAndroidJUnit4.class)
public class AppMenuTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCheckText() {
        ViewInteraction mainText = onView(withId(R.id.text_home));
        mainText.check(matches(isDisplayed()));
        mainText.check(matches(withText("This is home fragment")));
    }

    @Test
    public void testOpenSettings() {
        ViewInteraction elementMenu = onView(Matchers.allOf(withParent(withParent(withId(R.id.toolbar)))));
        elementMenu.check(matches(isDisplayed()));
        elementMenu.perform(click());
        ViewInteraction element = onView(withText("Settings"));
        element.check(matches(isDisplayed()));
        Intents.init();
        element.perform(click());
        intended(hasData("https://google.com"));
        intended(hasAction(Intent.ACTION_VIEW));
        Intents.release();
    }

    @Test
    public void testOpenGallery() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);

        ViewInteraction menu = onView(isAssignableFrom(AppCompatImageButton.class));
        menu.check(matches(isDisplayed()));
        menu.perform(click());

        ViewInteraction gallery = onView(withId(R.id.nav_gallery));
        gallery.perform(click());
        ViewInteraction itemSeven = onView(allOf(withId(R.id.item_number), withText("7")));
        itemSeven.check(matches(withText("7")));

        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }
}

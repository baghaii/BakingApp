package com.sepidehmiller.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

  @Rule
  public IntentsTestRule<MainActivity> mMainActivityIntentsTestRule = new IntentsTestRule<>(MainActivity.class);

  @Before
  public void stubAllExternalIntents() {
    intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
  }

  @Test
  public void clickRecipe_CheckExtras() {
    onView(withText("Nutella Pie")).perform(click());
    intended(allOf(
        hasExtraWithKey(RecipeAdapter.RECIPE),
        hasExtraWithKey(RecipeAdapter.INGREDIENTS),
        hasExtraWithKey(RecipeAdapter.STEPS)
    ));
  }

  // http://blog.sqisland.com/2015/05/espresso-match-toolbar-title.html
  @Test
  public void checkTitle() {
    onView(allOf(
        isAssignableFrom(TextView.class),
        withParent(isAssignableFrom(Toolbar.class))))
        .check(matches(withText("Baking Time")));

  }
}

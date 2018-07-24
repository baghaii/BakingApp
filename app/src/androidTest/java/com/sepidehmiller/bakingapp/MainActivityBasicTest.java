package com.sepidehmiller.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
  public void clickRecipe() {
    onView(withId(R.id.main_card_view)).perform(click());
    intended(allOf(
        hasAction(Intent.ACTION_SENDTO),
        hasExtraWithKey(RecipeAdapter.RECIPE),
        hasExtraWithKey(RecipeAdapter.INGREDIENTS),
        hasExtraWithKey(RecipeAdapter.STEPS)
    ));
  }
}

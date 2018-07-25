package com.sepidehmiller.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {

  // http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html
  @Rule
  public ActivityTestRule<RecipeActivity> rule = new ActivityTestRule<>(RecipeActivity.class,
      true, false);

  @Test
  public void demonstrateIntentTitleSetting() {
    Intent intent = new Intent();
    intent.putExtra(RecipeAdapter.RECIPE, "Nutella Pie");
    rule.launchActivity(intent);
    onView(allOf(
        isAssignableFrom(TextView.class),
        withParent(isAssignableFrom(Toolbar.class))))
        .check(matches(withText("Nutella Pie")));
  }
}

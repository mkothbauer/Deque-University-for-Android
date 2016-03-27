package com.dequesystems.accessibility101.verybroken;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.dequesystems.accessibility101.MainActivity;
import com.dequesystems.accessibility101.R;
import com.dequesystems.accessibility101.TestUtils;
import com.dequesystems.axeandroid.A11yAssert;
import com.dequesystems.axeandroid.DroidRuleSpeakableText;
import com.dequesystems.axeandroid.RuleAcronymAnnouncement;
import com.dequesystems.axeandroid.RuleControlLabels;
import com.dequesystems.axeandroid.RuleEditText;
import com.dequesystems.axeandroid.RuleImageContDesc;
import com.dequesystems.axeandroid.RuleTabWidget;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by chris.mcmeeking@deque.com on 3/24/16.
 *
 * Tests for the very broken fragment.  This is our "demo" test module.
 * The version committed to the repository will fail.  When we set up
 * CI we'll want to configure a test target that omits this test case.
 */
public class FragmentVeryBrokenTest {
    @Rule
    public ActivityTestRule<MainActivity> mFragmentActivityRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mActivity;

    @Before
    public void setupFragment() {

        mActivity = mFragmentActivityRule.getActivity();

        TestUtils.replaceAllContentWithFragment(mActivity, new FragmentVeryBroken());
    }

    @Test
    public void testIsAccessible() {
        A11yAssert.thatInstrumentation(InstrumentationRegistry.getInstrumentation())
                .expectedFailure(RuleAcronymAnnouncement.class, null)
                .expectedFailure(RuleControlLabels.class, null)
                .expectedFailure(RuleEditText.class, null)
                .expectedFailure(RuleImageContDesc.class, null)
                .expectedFailure(DroidRuleSpeakableText.class, null)
                .expectedFailure(RuleTabWidget.class, null)
                .isAccessible();
    }
}
package com.dequesystems.accessibility101.acronym;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.chriscm.clog.CLog;
import com.dequesystems.accessibility101.BuildConfig;
import com.dequesystems.accessibility101.MainActivity;
import com.dequesystems.accessibility101.TestUtils;
import com.dequesystems.axeandroid.A11yAssert;
import com.dequesystems.axeandroid.RuleDroidTouchTargetSize;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by chris.mcmeeking@deque.com on 3/16/16.
 *
 * Tests for the broken acronym announcement fragment.
 */
@RunWith(AndroidJUnit4.class)
public class AcronymAnnouncementAboutFragmentTest {

    static {
        CLog.initialize(AcronymAnnouncementAboutFragmentTest.class.getSimpleName(), BuildConfig.DEBUG);
    }

    @Rule
    public ActivityTestRule<MainActivity> mFragmentActivityRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mActivity;

    @Before
    public void setupFragment() {

        mActivity = mFragmentActivityRule.getActivity();

        TestUtils.replaceAllContentWithFragment(mActivity, new AcronymAnnouncementAboutFragment());
    }

    @Test
    public void testIsAccessible() {
        A11yAssert.thatInstrumentation(InstrumentationRegistry.getInstrumentation())
                .acceptWarnings()
                .isAccessible();
    }
}
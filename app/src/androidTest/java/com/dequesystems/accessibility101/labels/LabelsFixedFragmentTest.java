package com.dequesystems.accessibility101.labels;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.chriscm.clog.CLog;
import com.dequesystems.accessibility101.BuildConfig;
import com.dequesystems.accessibility101.MainActivity;
import com.dequesystems.accessibility101.TestUtils;
import com.dequesystems.axeandroid.A11yAssert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by chrismcmeeking on 3/18/16.
 */
public class LabelsFixedFragmentTest {
    static {
        CLog.initialize("DequeA11yTest", BuildConfig.DEBUG);
    }

    @Rule
    public ActivityTestRule<MainActivity> mFragmentActivityRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity mActivity;

    @Before
    public void setupFragment() {

        mActivity = mFragmentActivityRule.getActivity();

        TestUtils.replaceAllContentWithFragment(mActivity, new LabelsFixedFragment());
    }

    @Test
    public void testIsAccessible() {
        A11yAssert.thatInstrumentation(InstrumentationRegistry.getInstrumentation())
                .acceptWarnings()
                .isAccessible();
    }
}
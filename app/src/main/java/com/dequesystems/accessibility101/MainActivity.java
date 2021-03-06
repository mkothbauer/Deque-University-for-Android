package com.dequesystems.accessibility101;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dequesystems.a11yframework.TabLayoutWrapper;

/*
 * created by chris.mcmeeking@deque.com
 *
 * Runs set up/updates of some layout elements for the app
 */

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private boolean mIsOverlayOn = false;

    private View mOverlayView;

    private RelativeLayout mMainView;

    private StoryManager mStoryManager;

    private TabLayout mGlobalTabLayout;

    private TabLayoutWrapper mGlobalTabLayoutWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainView = (RelativeLayout) findViewById(R.id.aac_main_content);

        mTitle = getTitle();

        mStoryManager = new StoryManager(this);
        observeOverlayIsOn();

        mGlobalTabLayout = (TabLayout) findViewById(R.id.globalTabLayout);

        //Set up Navigation Drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), this.mStoryManager);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_example) {
            toggleOverlayIsOn(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOverlayOn() {
        return mIsOverlayOn;
    }

    public void toggleOverlayIsOn(MenuItem item) {
        mIsOverlayOn = !mIsOverlayOn;

        if (mIsOverlayOn) {
            item.setIcon(getResources().getDrawable(R.drawable.aac_sighted_icon));
            item.setTitle(getResources().getString(R.string.aac_talkBack_switch_on));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.aac_non_sighted_icon));
            item.setTitle(getResources().getString(R.string.aac_talkBack_switch_off));
        }

        observeOverlayIsOn();
    }

    public void observeOverlayIsOn() {

        if (mIsOverlayOn) {
            logDebug("Adding Overlay");

            if (mOverlayView == null) {
                mOverlayView = new ImageView(this);
                mOverlayView.setBackgroundColor(getResources().getColor(R.color.aac_deque_gray));
            }

            if (mOverlayView.getParent() != null) {
                mMainView.removeView(mOverlayView);
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mMainView.getLayoutParams());

            layoutParams.setMargins(0,0,0,0);

            mMainView.addView(mOverlayView, layoutParams);

            Log.wtf(LOG_TAG, mMainView.toString());

        } else {
            mMainView.removeView(mOverlayView);
        }
    }

    //Navigation Drawer Callbacks and support functions

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        int storyNumber = position > 0 ? position - 1 : 0;

        mStoryManager.setActiveStory(storyNumber);
        mTitle = mStoryManager.getActiveStory().getTitle();
    }

    @Override
    public void onNavigationDrawerClosed() {
        observeOverlayIsOn();
        Toolbar toolBar = (Toolbar)this.findViewById(R.id.action_bar);
        toolBar.getChildAt(0).sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
    }

    @Override
    public void onNavigationDrawerOpened() {
        logDebug("onNavigationDrawerOpened");
        mMainView.removeView(mOverlayView);
    }

    private void logDebug(String message) {
            Log.d(LOG_TAG, message);
    }

    public StoryManager getStoryManager(){return mStoryManager;}

    public TabLayout getTabLayout(){
        return mGlobalTabLayout;
    }

}
package com.ghostFood.util.slidingtab;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class SlidingFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String EMPTY_TOOLBAR_TITLE = "";

    public SlidingFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Drawable getPageDrawable(int position) {
        return null;
    }

    public String getToolbarTitle(int position) {
        return EMPTY_TOOLBAR_TITLE;
    }
}

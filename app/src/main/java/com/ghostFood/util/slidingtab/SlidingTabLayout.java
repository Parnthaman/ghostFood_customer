/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ghostFood.util.slidingtab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghostFood.R;


public class SlidingTabLayout extends HorizontalScrollView {

    private static final int TEXT_ONLY_TAB = R.layout.text_only;

    public static final int FOCUSED_WHITE       = Color.parseColor("#FFFFFF");
    public static final int NOT_FOCUSED_WHITE   = Color.parseColor("#000000");;

    private static final int TEXT_ID = R.id.TabText;

    private boolean mDistributeEvenly;

    private int customFocusedColor;
    private int customUnfocusedColor;

    private int mTitleOffset;
    private int textSize = 13;

    private TabType tabType;
    private ActionBar actionBar;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        mTabStrip = new SlidingTabStrip(context);
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void setCustomUnfocusedColor(int customUnfocusedColor) {
        this.customUnfocusedColor = customUnfocusedColor;
    }

    public void setCustomFocusedColor(int customFocusedColor) {
        this.customFocusedColor = customFocusedColor;
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    public void setTabType(TabType tabType) {
        this.tabType = tabType;
    }

    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void showIndicator(int position) {
        if (tabType !=  TabType.ICON_ONLY) return;
    }

    public void showIndicators(int... position) {
        if (tabType !=  TabType.ICON_ONLY) return;

        for (int pos:position) {
            View tabView = mTabStrip.getChildAt(pos);
        }
    }

    public void hideIndicator(int position) {
        if (tabType !=  TabType.ICON_ONLY) return;

    }

    public void hideIndicators(int... position) {
        if (tabType !=  TabType.ICON_ONLY) return;

    }

    public boolean isIndicatorVisible(int position) {
        if (tabType !=  TabType.ICON_ONLY) return false;
        return false;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        if (tabType == null)
            tabType = TabType.TEXT_ONLY;

        mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();

            SlidingFragmentPagerAdapter adapter = (SlidingFragmentPagerAdapter) mViewPager.getAdapter();
            if (actionBar != null)
                actionBar.setTitle(adapter.getToolbarTitle(mViewPager.getCurrentItem()));
        }
    }

    private void populateTabStrip() {
        final SlidingFragmentPagerAdapter adapter = (SlidingFragmentPagerAdapter) mViewPager.getAdapter();
        final OnClickListener tabClickListener = new TabClickListener();

        if (tabType == null)
            tabType = TabType.TEXT_ONLY;

        int focused_color = customFocusedColor != 0 ? customFocusedColor : FOCUSED_WHITE;
        int unfocused_color = customUnfocusedColor != 0 ? customUnfocusedColor : NOT_FOCUSED_WHITE;

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            ImageView tabImageView = null;

            switch (tabType) {
                case TEXT_ONLY:
                    tabView = LayoutInflater.from(getContext()).inflate(TEXT_ONLY_TAB, mTabStrip, false);
                    tabTitleView = (TextView) tabView.findViewById(TEXT_ID);
                    break;

            }

            if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            if (i == mViewPager.getCurrentItem())
                tabView.setSelected(true);

            if (tabTitleView != null && adapter.getPageTitle(i) != null) {
                tabTitleView.setText(adapter.getPageTitle(i));
                tabTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, this.textSize);
                tabTitleView.setAllCaps(false);
                tabTitleView.setTextColor(i == mViewPager.getCurrentItem() ?
                        focused_color : unfocused_color);
            }

            if (tabImageView != null && adapter.getPageDrawable(i) != null) {
                tabImageView.setImageDrawable(adapter.getPageDrawable(i));
                tabImageView.setColorFilter(i == mViewPager.getCurrentItem() ?
                        focused_color : unfocused_color, PorterDuff.Mode.MULTIPLY);
            }

            tabView.setOnClickListener(tabClickListener);
            mTabStrip.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            TextView tabTitleView = null;
            ImageView tabImageView = null;

            SlidingFragmentPagerAdapter adapter = (SlidingFragmentPagerAdapter) mViewPager.getAdapter();
            int focused_color = customFocusedColor != 0 ? customFocusedColor : FOCUSED_WHITE;
            int unfocused_color = customUnfocusedColor != 0 ? customUnfocusedColor : NOT_FOCUSED_WHITE;

            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            if (actionBar != null) {
                actionBar.setTitle(adapter.getToolbarTitle(mViewPager.getCurrentItem()));
            }

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);

                switch (tabType) {
                    case TEXT_ONLY:
                        tabTitleView = (TextView) mTabStrip.getChildAt(i).findViewById(TEXT_ID);
                        tabTitleView.setTextColor(i == mViewPager.getCurrentItem() ?
                                focused_color : unfocused_color);
                        break;

                }

            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}

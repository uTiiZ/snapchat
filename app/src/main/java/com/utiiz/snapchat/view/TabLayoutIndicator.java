package com.utiiz.snapchat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.kekstudio.dachshundtablayout.R.styleable;
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface;
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorType;
import com.utiiz.snapchat.helper.Snapchat;

public class TabLayoutIndicator extends TabLayout implements OnPageChangeListener {
    private static final int DEFAULT_HEIGHT_DP = 6;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private int mCurrentPosition;
    private boolean mCenterAlign;
    private LinearLayout mTabStrip;
    private AnimatedIndicatorType mAnimatedIndicatorType;
    private AnimatedIndicatorInterface mAnimatedIndicator;
    private int mTempPosition;
    private int mTempPositionOffsetPixels;
    private float mTempPositionOffset;

    public TabLayoutIndicator(Context context) {
        this(context, (AttributeSet)null);
    }

    public TabLayoutIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setSelectedTabIndicatorHeight(0);
        this.mTabStrip = (LinearLayout) super.getChildAt(0);
        TypedArray ta = context.obtainStyledAttributes(attrs, styleable.DachshundTabLayout);
        this.mIndicatorHeight = ta.getDimensionPixelSize(styleable.DachshundTabLayout_ddIndicatorHeight, (int) Snapchat.DPToPixel(3.0F, getContext()));
        this.mIndicatorColor = ta.getColor(styleable.DachshundTabLayout_ddIndicatorColor, -1);
        this.mCenterAlign = ta.getBoolean(styleable.DachshundTabLayout_ddCenterAlign, false);
        this.mAnimatedIndicatorType = AnimatedIndicatorType.values()[ta.getInt(styleable.DachshundTabLayout_ddAnimatedIndicator, 0)];
        ta.recycle();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mCenterAlign) {
            View firstTab = ((ViewGroup)this.getChildAt(0)).getChildAt(0);
            View lastTab = ((ViewGroup)this.getChildAt(0)).getChildAt(((ViewGroup)this.getChildAt(0)).getChildCount() - 1);
            ViewCompat.setPaddingRelative(this.getChildAt(0), this.getWidth() / 2 - firstTab.getWidth() / 2, 0, this.getWidth() / 2 - lastTab.getWidth() / 2, 0);
        }

        if (this.mAnimatedIndicator == null) {
            this.setupAnimatedIndicator();
        }

        this.onPageScrolled(this.mTempPosition, this.mTempPositionOffset, this.mTempPositionOffsetPixels);
    }

    private void setupAnimatedIndicator() {
        switch(this.mAnimatedIndicatorType) {
            case DACHSHUND:
                //this.setAnimatedIndicator(new DachshundIndicator(this));
                break;
            case POINT_MOVE:
                //this.setAnimatedIndicator(new PointMoveIndicator(this));
                break;
            case LINE_MOVE:
                this.setAnimatedIndicator(new LineIndicator(this));
                break;
            case POINT_FADE:
                //this.setAnimatedIndicator(new PointFadeIndicator(this));
                break;
            case LINE_FADE:
                //this.setAnimatedIndicator(new LineFadeIndicator(this));
        }

    }

    public void setAnimatedIndicator(AnimatedIndicatorInterface animatedIndicator) {
        this.mAnimatedIndicator = animatedIndicator;
        animatedIndicator.setSelectedTabIndicatorColor(this.mIndicatorColor);
        animatedIndicator.setSelectedTabIndicatorHeight(this.mIndicatorHeight);
        this.invalidate();
    }

    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        this.mIndicatorColor = color;
        if (this.mAnimatedIndicator != null) {
            this.mAnimatedIndicator.setSelectedTabIndicatorColor(color);
            this.invalidate();
        }

    }

    public void setSelectedTabIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
        if (this.mAnimatedIndicator != null) {
            this.mAnimatedIndicator.setSelectedTabIndicatorHeight(height);
            this.invalidate();
        }

    }

    public void setCenterAlign(boolean centerAlign) {
        this.mCenterAlign = centerAlign;
        this.requestLayout();
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
            viewPager.addOnPageChangeListener(this);
        }

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mAnimatedIndicator != null) {
            this.mAnimatedIndicator.draw(canvas);
        }

    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mTempPosition = position;
        this.mTempPositionOffset = positionOffset;
        this.mTempPositionOffsetPixels = positionOffsetPixels;
        if (position > this.mCurrentPosition || position + 1 < this.mCurrentPosition) {
            this.mCurrentPosition = position;
        }

        int mStartXLeft;
        int mStartXCenter;
        int mStartXRight;
        int mEndXLeft;
        int mEndXCenter;
        int mEndXRight;
        if (position != this.mCurrentPosition) {
            mStartXLeft = (int)this.getChildXLeft(this.mCurrentPosition);
            mStartXCenter = (int)this.getChildXCenter(this.mCurrentPosition);
            mStartXRight = (int)this.getChildXRight(this.mCurrentPosition);
            mEndXLeft = (int)this.getChildXLeft(position);
            mEndXRight = (int)this.getChildXRight(position);
            mEndXCenter = (int)this.getChildXCenter(position);
            if (this.mAnimatedIndicator != null) {
                this.mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft, mStartXCenter, mEndXCenter, mStartXRight, mEndXRight);
                this.mAnimatedIndicator.setCurrentPlayTime((long)((1.0F - positionOffset) * (float)((int)this.mAnimatedIndicator.getDuration())));
            }
        } else {
            mStartXLeft = (int)this.getChildXLeft(this.mCurrentPosition);
            mStartXCenter = (int)this.getChildXCenter(this.mCurrentPosition);
            mStartXRight = (int)this.getChildXRight(this.mCurrentPosition);
            if (this.mTabStrip.getChildAt(position + 1) != null) {
                mEndXLeft = (int)this.getChildXLeft(position + 1);
                mEndXCenter = (int)this.getChildXCenter(position + 1);
                mEndXRight = (int)this.getChildXRight(position + 1);
            } else {
                mEndXLeft = (int)this.getChildXLeft(position);
                mEndXCenter = (int)this.getChildXCenter(position);
                mEndXRight = (int)this.getChildXRight(position);
            }

            if (this.mAnimatedIndicator != null) {
                this.mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft, mStartXCenter, mEndXCenter, mStartXRight, mEndXRight);
                this.mAnimatedIndicator.setCurrentPlayTime((long)(positionOffset * (float)((int)this.mAnimatedIndicator.getDuration())));
            }
        }

        if (positionOffset == 0.0F) {
            this.mCurrentPosition = position;
        }

    }

    public void onPageSelected(int position) {
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public float getChildXLeft(int position) {
        return this.mTabStrip.getChildAt(position) != null ? this.mTabStrip.getChildAt(position).getX() : 0.0F;
    }

    public float getChildXCenter(int position) {
        return this.mTabStrip.getChildAt(position) != null ? this.mTabStrip.getChildAt(position).getX() + (float)(this.mTabStrip.getChildAt(position).getWidth() / 2) : 0.0F;
    }

    public float getChildXRight(int position) {
        return this.mTabStrip.getChildAt(position) != null ? this.mTabStrip.getChildAt(position).getX() + (float)this.mTabStrip.getChildAt(position).getWidth() : 0.0F;
    }

    public AnimatedIndicatorInterface getAnimatedIndicator() {
        return this.mAnimatedIndicator;
    }
}

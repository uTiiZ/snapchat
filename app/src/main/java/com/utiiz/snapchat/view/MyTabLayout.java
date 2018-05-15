package com.utiiz.snapchat.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

public class MyTabLayout extends TabLayout {
    public MyTabLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setSelectedTabIndicatorHeight(0);
    }
}

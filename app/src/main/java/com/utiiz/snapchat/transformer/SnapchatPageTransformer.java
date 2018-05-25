package com.utiiz.snapchat.transformer;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.utiiz.snapchat.R;
import com.utiiz.snapchat.activity.MainActivity;
import com.utiiz.snapchat.helper.Snapchat;
import com.utiiz.snapchat.interfaces.SnapchatInterface;

import butterknife.ButterKnife;

/**
 * Created by t.kervran on 31/03/2017.
 */

public class SnapchatPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        Integer[] COLORS = new Integer[] {Color.parseColor("#FF0000"), Color.parseColor("#FFFFFF"), Color.parseColor("#FF0000")};
        // Get the page index from the tag. This makes
        // it possible to know which page index you're
        // currently transforming - and that can be used
        // to make some important performance improvements.
        Activity a = MainActivity.a;
        Integer pagePosition = (Integer) view.getTag();
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        Drawable vectorFriend = a.findViewById(R.id.icon_friends).getBackground();
        GradientDrawable vectorCamera = (GradientDrawable) a.findViewById(R.id.icon_camera).getBackground();
        Drawable vectorDiscover = a.findViewById(R.id.icon_discover).getBackground();

        if (pagePosition == SnapchatInterface.CAMERA_PAGE_INDEX) {
            if (position >= 0.0F) {
                vectorFriend.setTint((Integer) argbEvaluator.evaluate(Math.abs(position), Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFCED4DA")));
                vectorCamera.setStroke((int) Snapchat.DPToPixel(5.0F, a.getApplicationContext()), (Integer) argbEvaluator.evaluate(Math.abs(position), Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFCED4DA")));
                vectorDiscover.setTint((Integer) argbEvaluator.evaluate(Math.abs(position), Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFCED4DA")));
                a.findViewById(R.id.icon_friends).setBackground(vectorFriend);
                a.findViewById(R.id.icon_camera).setBackground(vectorCamera);
                a.findViewById(R.id.icon_discover).setBackground(vectorDiscover);
            }

            a.findViewById(R.id.fl_memories).setAlpha(1 - (Math.abs(position * 2)));
            a.findViewById(R.id.rl_icon_camera).setScaleX(1 - (Math.abs(position / 3)));
            a.findViewById(R.id.rl_icon_camera).setScaleY(1 - (Math.abs(position / 3)));
            a.findViewById(R.id.rl_icon_friends).setScaleX(1 - (Math.abs(position / 4)));
            a.findViewById(R.id.rl_icon_friends).setScaleY(1 - (Math.abs(position / 4)));
            a.findViewById(R.id.rl_icon_discover).setScaleX(1 - (Math.abs(position / 4)));
            a.findViewById(R.id.rl_icon_discover).setScaleY(1 - (Math.abs(position / 4)));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) a.findViewById(R.id.fl_memories).getLayoutParams();
            params.setMargins(0, params.topMargin, 0, (int) Snapchat.DPToPixel((float) Snapchat.interpolate(new double[]{-35, 20, -35}, new double[]{-1, 0, 1}, position), a.getApplicationContext()));
            a.findViewById(R.id.fl_memories).setLayoutParams(params);

            Display display = a.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            params = (ViewGroup.MarginLayoutParams) a.findViewById(R.id.rl_icon_friends).getLayoutParams();
            params.setMargins(0, 0, (int) Snapchat.DPToPixel((float) Snapchat.interpolate(new double[]{20, Snapchat.PixelsToDP(width / 4, a.getApplicationContext()), 20}, new double[]{-1, 0, 1}, position), a.getApplicationContext()), params.bottomMargin);
            a.findViewById(R.id.rl_icon_friends).setLayoutParams(params);

            params = (ViewGroup.MarginLayoutParams) a.findViewById(R.id.rl_icon_discover).getLayoutParams();
            params.setMargins((int) Snapchat.DPToPixel((float) Snapchat.interpolate(new double[]{20, Snapchat.PixelsToDP(width / 4, a.getApplicationContext()), 20}, new double[]{-1, 0, 1}, position), a.getApplicationContext()), 0, 0, params.bottomMargin);
            a.findViewById(R.id.rl_icon_discover).setLayoutParams(params);
        }

        if (pagePosition == SnapchatInterface.FRIEND_PAGE_INDEX) {
            if (position >= -0.5 && position <= 0.0) {
                a.findViewById(R.id.label_friends).setAlpha(1 - (Math.abs(position * 2)));
            } else {
                a.findViewById(R.id.label_friends).setAlpha(0);
            }
        }

        if (pagePosition == SnapchatInterface.CAMERA_PAGE_INDEX) {
            if (position >= -0.5 && position <= 0.5) {
                a.findViewById(R.id.label_search).setAlpha(1 - Math.abs(position * 2));
            } else {
                a.findViewById(R.id.label_search).setAlpha(0);
            }
        }

        if (pagePosition == SnapchatInterface.DISCOVER_PAGE_INDEX) {
            if (position >= 0.0 && position <= 0.5) {
                a.findViewById(R.id.label_discover).setAlpha(1 - (Math.abs(position * 2)));
            } else {
                a.findViewById(R.id.label_discover).setAlpha(0);
            }
        }

        int pageWidth = view.getWidth();
        float pageWidthTimesPosition = pageWidth * position;
        int pageHeight = view.getHeight();
        float pageHeightTimesPosition = pageHeight * position;

        if (position <= -1.0f || position >= 1.0f) {
        } else if (position == 0.0f) {
        } else {
            if (pagePosition == 0 && a.findViewById(R.id.new_chat) != null) {
                a.findViewById(R.id.new_chat).setAlpha(1.0F - (Math.abs(position * 2)));
                a.findViewById(R.id.new_chat).setTranslationX(pageWidthTimesPosition / 4);
            } else {
                a.findViewById(R.id.new_chat).setAlpha(0.0F);
            }

            if (pagePosition == 1 && a.findViewById(R.id.flash) != null && a.findViewById(R.id.night_mode) != null && a.findViewById(R.id.camera) != null) {
                //a.findViewById(R.id.action_bar_border).setAlpha(1.0F - (Math.abs(position * 2)));
                a.findViewById(R.id.flash).setAlpha(1.0F - (Math.abs(position * 2)));
                a.findViewById(R.id.night_mode).setAlpha(1.0F - (Math.abs(position * 2)));
                a.findViewById(R.id.camera).setAlpha(1.0F - (Math.abs(position * 2)));
                a.findViewById(R.id.flash).setTranslationX(pageWidthTimesPosition / 4);
                a.findViewById(R.id.night_mode) .setTranslationX(pageWidthTimesPosition / 4);
                a.findViewById(R.id.camera) .setTranslationX(pageWidthTimesPosition / 4);
            }
        }

    }
}
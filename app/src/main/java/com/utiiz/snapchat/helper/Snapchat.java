package com.utiiz.snapchat.helper;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.utiiz.snapchat.adapter.DiscoverAdapter;
import com.utiiz.snapchat.adapter.FriendAdapter;
import com.utiiz.snapchat.interfaces.DiscoverInterface;
import com.utiiz.snapchat.interfaces.FriendInterface;
import com.utiiz.snapchat.model.Chat;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snapchat {

    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float DPToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float PixelsToDP(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /**
     * This methods interpolate a value to another
     *
     * @param x An array of value
     * @param y Another array of value
     * @param z The value you want to interpolate
     */

    public static double interpolate(double[] x, double[] y, double z) {

        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PolynomialSplineFunction f = linearInterpolator.interpolate(y, x);

        double value = f.value(z);

        return value;
    }

    public static void refreshDiscover(int position, RecyclerView mRecyclerView, DiscoverAdapter discoverAdapter, Integer[] COLORS) {
        List<Chat> newChatList = new ArrayList<>();

        switch (position) {
            case DiscoverInterface.POSITION_ALL :
                for (int i = 0; i < 15; i++) {
                    newChatList.add(new Chat("All", (i == 0 || i == 2 || i == 4 || i == 6) ? 250 : 300, COLORS[new Random().nextInt(COLORS.length)]));
                }
                break;
            case DiscoverInterface.POSITION_SUBSCRIPTIONS:
                for (int i = 0; i < 15; i++) {
                    newChatList.add(new Chat("Subscriptions", (i == 0 || i == 2 || i == 4 || i == 6) ? 250 : 300, COLORS[new Random().nextInt(COLORS.length)]));
                }
                break;
        }

        discoverAdapter.update(newChatList);
        mRecyclerView.setAdapter(discoverAdapter);
        mRecyclerView.setLayoutFrozen(true);
    }

    public static void refreshFriends(int position, RecyclerView mRecyclerView, FriendAdapter friendAdapter) {
        List<Chat> newChatList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            switch (position) {
                case FriendInterface.POSITION_GROUPS :
                    newChatList.add(new Chat("Groups"));
                    break;
                case FriendInterface.POSITION_STORIES :
                    newChatList.add(new Chat("Stories"));
                    break;
                case FriendInterface.POSITION_CHAT :
                    newChatList.add(new Chat("Chat"));
                    break;
            }
        }

        friendAdapter.update(newChatList);
        mRecyclerView.setAdapter(friendAdapter);
        mRecyclerView.setLayoutFrozen(true);
    }
}

package com.utiiz.snapchat.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.utiiz.snapchat.R;
import com.utiiz.snapchat.activity.MainActivity;
import com.utiiz.snapchat.adapter.NestedScrollViewOverScrollDecorAdapter;
import com.utiiz.snapchat.adapter.StringAdapter;
import com.utiiz.snapchat.helper.Snapchat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollStateListener;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;
import static me.everything.android.ui.overscroll.OverScrollBounceEffectDecoratorBase.DEFAULT_DECELERATE_FACTOR;
import static me.everything.android.ui.overscroll.OverScrollBounceEffectDecoratorBase.DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK;

public class FriendFragment extends Fragment {

    public LinearLayout mLinearLayout;
    @NonNull @BindView(R.id.nested_scroll_view) public NestedScrollView mNestedScrollView;
    @NonNull @BindView(R.id.recycler_view) public RecyclerView mRecyclerView;
    @NonNull @BindView(R.id.snap) public View mSnap;
    @NonNull @BindView(R.id.hands) public View mHands;
    @NonNull @BindView(R.id.rainbow) public View mRainbow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, mLinearLayout);

        mNestedScrollView.setPadding(0, mNestedScrollView.getPaddingTop() + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mSnap.getLayoutParams();
        params.setMargins(0, params.topMargin + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        mSnap.setLayoutParams(params);
        ViewGroup.MarginLayoutParams paramsHands = (ViewGroup.MarginLayoutParams) mHands.getLayoutParams();
        paramsHands.setMargins(0, paramsHands.topMargin + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        mHands.setLayoutParams(paramsHands);
        ViewGroup.MarginLayoutParams paramsRainbow = (ViewGroup.MarginLayoutParams) mRainbow.getLayoutParams();
        paramsRainbow.setMargins(0, paramsRainbow.topMargin + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        mRainbow.setLayoutParams(paramsRainbow);

        List<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("Hello");
        stringList.add("World");

        StringAdapter adapter = new StringAdapter(stringList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutFrozen(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        VerticalOverScrollBounceEffectDecorator decorator = new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(mNestedScrollView), 2F, DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK, DEFAULT_DECELERATE_FACTOR);

        /*decorator.setOverScrollStateListener(new IOverScrollStateListener() {
            @Override
            public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                switch (newState) {
                    case STATE_IDLE:
                        // No over-scroll is in effect.
                        Log.e("STATE", "STATE_IDLE");
                        break;
                    case STATE_DRAG_START_SIDE:
                        // Dragging started at the left-end.
                        Log.e("STATE", "STATE_DRAG_START_SIDE");
                        break;
                    case STATE_DRAG_END_SIDE:
                        // Dragging started at the right-end.
                        Log.e("STATE", "STATE_DRAG_END_SIDE");
                        break;
                    case STATE_BOUNCE_BACK:
                        Log.e("STATE", "STATE_BOUNCE_BACK");
                        if (oldState == STATE_DRAG_START_SIDE) {
                            // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                        } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                            // View is starting to bounce back from the *right-end*.
                        }
                        break;
                }
            }
        });*/

        final Boolean[] canBounceBack = {false};

        decorator.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {

                switch (state) {
                    case STATE_IDLE:
                        // No over-scroll is in effect.
                        Log.e("STATE", "STATE_IDLE");
                        break;
                    case STATE_DRAG_START_SIDE:
                        // Dragging started at the left-end.
                        Log.e("STATE", "STATE_DRAG_START_SIDE");

                        mHands.setAlpha(1);
                        mRainbow.setAlpha(0);

                        canBounceBack[0] = (offset * 0.5F >= mSnap.getHeight() * 0.33F) ? true : false;

                        if (offset <= mSnap.getHeight()) {
                            if (mSnap.getBackground().getConstantState() != getResources().getDrawable(R.drawable.ic_ghost_no_expression, null).getConstantState())
                                mSnap.setBackground(getResources().getDrawable(R.drawable.ic_ghost_no_expression, null));
                            mSnap.animate()
                                    .y(mNestedScrollView.getPaddingTop() + (offset * 0.5F))
                                    .setDuration(0)
                                    .start();

                            mRainbow.animate()
                                    .y(mNestedScrollView.getPaddingTop() + (offset * 0.5F))
                                    .setDuration(0)
                                    .start();

                            mHands.animate()
                                    .y(mNestedScrollView.getPaddingTop() + Snapchat.DPToPixel(2F, getContext()) + (offset * 0.855F))
                                    .setDuration(0)
                                    .start();
                        } else {
                            if (mSnap.getBackground().getConstantState() != getResources().getDrawable(R.drawable.ic_ghost_laught, null).getConstantState())
                                mSnap.setBackground(getResources().getDrawable(R.drawable.ic_ghost_laught, null));
                            mSnap.animate()
                                    .y(mNestedScrollView.getPaddingTop() + (offset) - (mSnap.getHeight() * 0.5F))
                                    .setDuration(0)
                                    .start();

                            mRainbow.animate()
                                    .y(mNestedScrollView.getPaddingTop() + (offset) - (mSnap.getHeight() * 0.5F))
                                    .setDuration(0)
                                    .start();

                            mHands.animate()
                                    .y(mNestedScrollView.getPaddingTop() + Snapchat.DPToPixel(21F, getContext()) + (offset) - (mSnap.getHeight() * 0.5F))
                                    .setDuration(0)
                                    .start();
                        }

                        break;
                    case STATE_DRAG_END_SIDE:
                        // Dragging started at the right-end.
                        Log.e("STATE", "STATE_DRAG_END_SIDE");
                        break;
                    case STATE_BOUNCE_BACK:
                        Log.e("STATE", "STATE_BOUNCE_BACK");

                        if (canBounceBack[0]) {
                            mHands.setAlpha(0);
                            mRainbow.setAlpha(1);

                            mSnap.animate()
                                    .y(-(Snapchat.PixelsToDP(mRainbow.getHeight() * 2F + mSnap.getHeight() * 3F, getContext())) - Snapchat.getStatusBarHeight(getContext()))
                                    .setDuration(300)
                                    .setInterpolator(new DecelerateInterpolator())
                                    .start();
                            mRainbow.animate()
                                    .y(-(Snapchat.PixelsToDP(mRainbow.getHeight() * 2F + mSnap.getHeight() * 3F, getContext())) - Snapchat.getStatusBarHeight(getContext()))
                                    .setDuration(300)
                                    .setInterpolator(new DecelerateInterpolator())
                                    .start();
                            canBounceBack[0] = false;
                        }

                        break;
                }
            }
        });

        return mLinearLayout;
    }

}

package com.utiiz.snapchat.fragment;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.utiiz.snapchat.R;
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
    @NonNull @BindView(R.id.snap_stun) public View mSnapStun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, mLinearLayout);

        mNestedScrollView.setPadding(0, mNestedScrollView.getPaddingTop() + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mSnapStun.getLayoutParams();
        params.setMargins(0, params.topMargin + Snapchat.getStatusBarHeight(getContext()), 0, 0);
        mSnapStun.setLayoutParams(params);

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

        decorator.setOverScrollStateListener(new IOverScrollStateListener() {
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
        });

        decorator.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                final View view = decor.getView();
                if (offset > 0) {
                    // 'view' is currently being over-scrolled from the top.
                } else if (offset < 0) {
                    // 'view' is currently being over-scrolled from the bottom.
                } else {
                    // No over-scroll is in-effect.
                    // This is synonymous with having (state == STATE_IDLE).
                }
            }
        });

        return mLinearLayout;
    }

}

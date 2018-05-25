package com.utiiz.snapchat.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.utiiz.snapchat.R;
import com.utiiz.snapchat.adapter.DiscoverAdapter;
import com.utiiz.snapchat.adapter.FragmentAdapter;
import com.utiiz.snapchat.adapter.FriendAdapter;
import com.utiiz.snapchat.adapter.NestedScrollViewOverScrollDecorAdapter;
import com.utiiz.snapchat.helper.Snapchat;
import com.utiiz.snapchat.interfaces.FriendInterface;
import com.utiiz.snapchat.interfaces.SnapchatInterface;
import com.utiiz.snapchat.model.Chat;
import com.utiiz.snapchat.view.LineIndicator;
import com.utiiz.snapchat.view.TabLayoutIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollUpdateListener;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;

import static me.everything.android.ui.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static me.everything.android.ui.overscroll.IOverScrollState.STATE_IDLE;
import static me.everything.android.ui.overscroll.OverScrollBounceEffectDecoratorBase.DEFAULT_DECELERATE_FACTOR;
import static me.everything.android.ui.overscroll.OverScrollBounceEffectDecoratorBase.DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK;

public class FriendFragment extends Fragment {

    public LinearLayout mLinearLayout;
    @NonNull @BindView(R.id.nested_scroll_view) public NestedScrollView mNestedScrollView;
    @NonNull @BindView(R.id.tab_layout) public TabLayoutIndicator mTabLayout;
    @NonNull @BindView(R.id.recycler_view) public RecyclerView mRecyclerView;
    @NonNull @BindView(R.id.view_pager) public ViewPager mViewPager;
    @NonNull @BindView(R.id.snap) public View mSnap;
    @NonNull @BindView(R.id.hands) public View mHands;
    @NonNull @BindView(R.id.rainbow) public View mRainbow;
    @NonNull @BindView(R.id.gradient_top) public View mGradient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, mLinearLayout);
        mLinearLayout.setTag(SnapchatInterface.FRIEND_PAGE_INDEX);

        LineIndicator lineIndicator = new LineIndicator(mTabLayout);
        lineIndicator.setContext(getContext());

        mTabLayout.setAnimatedIndicator(lineIndicator);
        mTabLayout.setSelectedTabIndicatorHeight((int) Snapchat.DPToPixel(2.0F, getContext()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(Color.parseColor("#BBFFFFFF"), Color.parseColor("#FFFFFFFF"));

        final ViewTreeObserver observer = mTabLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                TypedValue tv = new TypedValue();
                int actionBarHeight = 0;
                if (getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mSnap.getLayoutParams();
                params.setMargins(0, params.topMargin + Snapchat.getStatusBarHeight(getContext()) + mTabLayout.getHeight(), 0, 0);
                mSnap.setLayoutParams(params);
                ViewGroup.MarginLayoutParams paramsHands = (ViewGroup.MarginLayoutParams) mHands.getLayoutParams();
                paramsHands.setMargins(0, paramsHands.topMargin + Snapchat.getStatusBarHeight(getContext()) + mTabLayout.getHeight(), 0, 0);
                mHands.setLayoutParams(paramsHands);
                ViewGroup.MarginLayoutParams paramsRainbow = (ViewGroup.MarginLayoutParams) mRainbow.getLayoutParams();
                paramsRainbow.setMargins(0, paramsRainbow.topMargin + Snapchat.getStatusBarHeight(getContext()) + mTabLayout.getHeight(), 0, 0);
                mRainbow.setLayoutParams(paramsRainbow);
                ViewGroup.MarginLayoutParams paramsTabLayout = (ViewGroup.MarginLayoutParams) mTabLayout.getLayoutParams();
                paramsTabLayout.setMargins(0, paramsTabLayout.topMargin + Snapchat.getStatusBarHeight(getContext()) + actionBarHeight, 0, 0);
                mTabLayout.setLayoutParams(paramsTabLayout);

                mNestedScrollView.setPadding(0, mNestedScrollView.getPaddingTop() + Snapchat.getStatusBarHeight(getContext()) + mTabLayout.getHeight(), 0, 0);
            }
        });


        final List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));
        chatList.add(new Chat("Chat"));

        final FriendAdapter friendAdapter = new FriendAdapter(chatList);
        mRecyclerView.setAdapter(friendAdapter);
        mRecyclerView.setLayoutFrozen(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*mTabLayout.addTab(mTabLayout.newTab().setText("Groups"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Stories"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Chat"));*/
        final FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager(), getContext());

        adapter.add(RecyclerViewFragment.class, "Groups");
        adapter.add(RecyclerViewFragment.class, "Stories");
        adapter.add(RecyclerViewFragment.class, "Chat");
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getPagesCount());
        mViewPager.setCurrentItem(FriendInterface.POSITION_CHAT);

        mNestedScrollView.setFillViewport(true);

        final VerticalOverScrollBounceEffectDecorator decorator = new VerticalOverScrollBounceEffectDecorator(new NestedScrollViewOverScrollDecorAdapter(mNestedScrollView), 2F, DEFAULT_TOUCH_DRAG_MOVE_RATIO_BCK, DEFAULT_DECELERATE_FACTOR);

        final Boolean[] canBounceBack = {false};
        final double[] arAlpha = {0, 1};
        final double[] arOffset = {0, 150};

        decorator.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
            @Override
            public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {

                mTabLayout.animate()
                        .y(mNestedScrollView.getPaddingTop() - mTabLayout.getHeight() + offset)
                        .setDuration(0)
                        .start();

                if (offset == 0 && mTabLayout.getAlpha() != 1.0F)
                    mTabLayout.animate()
                            .alpha(1.0F)
                            .setDuration(0)
                            .start();
                else if (offset >= 0 && offset <= 150)
                    mTabLayout.animate()
                            .alpha(1.0F - (float) Snapchat.interpolate(arAlpha, arOffset, offset))
                            .setDuration(0)
                            .start();
                else if (offset > 150 && mTabLayout.getAlpha() != 0.0F)
                    mTabLayout.animate()
                            .alpha(0.0F)
                            .setDuration(0)
                            .start();

                switch (state) {
                    case STATE_IDLE:
                        // No over-scroll is in effect.
                        break;
                    case STATE_DRAG_START_SIDE:
                        // Dragging started at the left-end.
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
                        break;
                    case STATE_BOUNCE_BACK:

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
                            Snapchat.refreshFriends(mViewPager.getCurrentItem(), mRecyclerView, friendAdapter);
                        }

                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Snapchat.refreshFriends(position, mRecyclerView, friendAdapter);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float alpha = (1.0F * scrollY) / (1.0F * v.getPaddingTop());

                mGradient.setAlpha(scrollY == 0 ? 0 : alpha);
            }
        });

        init(getContext());

        return mLinearLayout;
    }

    private void init(Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://192.168.56.1:3000/tickets";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("INIT", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("INIT", error.getLocalizedMessage() + " - " + error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}

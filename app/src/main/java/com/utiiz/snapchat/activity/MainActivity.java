package com.utiiz.snapchat.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.utiiz.snapchat.R;
import com.utiiz.snapchat.adapter.FragmentAdapter;
import com.utiiz.snapchat.fragment.CameraFragment;
import com.utiiz.snapchat.fragment.DiscoverFragment;
import com.utiiz.snapchat.fragment.FriendFragment;
import com.utiiz.snapchat.fragment.RecyclerViewFragment;
import com.utiiz.snapchat.helper.Snapchat;
import com.utiiz.snapchat.interpolator.BounceInterpolator;
import com.utiiz.snapchat.transformer.SnapchatPageTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @NonNull @BindView(R.id.view_pager) public ViewPager mViewPager;
    @NonNull @BindView(R.id.background) public View mBackground;
    @NonNull @BindView(R.id.rl_icon_friends) public View rlFriendsIcon;
    @NonNull @BindView(R.id.icon_friends) public View vFriendsIcon;
    @NonNull @BindView(R.id.last_memory) public ImageView ivLastMemory;
    @NonNull @BindView(R.id.my_stories) public ImageView ivMyStories;
    @NonNull @BindView(R.id.action_bar) public LinearLayout mActionBar;
    @NonNull @BindView(R.id.action_bar_icons) public FrameLayout mActionBarIcons;
    @NonNull @BindView(R.id.flash_icon) public View vFlash;
    @NonNull @BindView(R.id.camera_icon) public View vCamera;
    @NonNull @BindView(R.id.night_mode_icon) public View vNightMode;
    public static Activity a;

    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Integer[] COLORS = null;
    public static ValueAnimator colorAnimation = new ValueAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        a = this;

        COLORS = new Integer[] {getApplicationContext().getColor(R.color.colorPrimaryBlue), getApplicationContext().getColor(android.R.color.transparent), getApplicationContext().getColor(R.color.colorPrimaryPurple)};

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            // flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, Snapchat.getStatusBarHeight(getApplicationContext()), 0, 0);
        mActionBar.setLayoutParams(params);

        params = (ViewGroup.MarginLayoutParams) mActionBarIcons.getLayoutParams();
        params.setMargins(0, Snapchat.getStatusBarHeight(getApplicationContext()), 0, 0);
        mActionBarIcons.setLayoutParams(params);

        final FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), getApplicationContext());

        adapter.add(FriendFragment.class, null);
        adapter.add(CameraFragment.class, null);
        adapter.add(DiscoverFragment.class, null);

        Integer mMainPageIndex = adapter.indexOf(CameraFragment.class);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getPagesCount());
        mViewPager.setPageTransformer(false, new SnapchatPageTransformer());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount() -1) && position < (COLORS.length - 1)) {
                    mBackground.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, COLORS[position], COLORS[position + 1]));
                } else {
                    mBackground.setBackgroundColor(COLORS[COLORS.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();
        //requestOptions.transform(new RoundedCornersTransformation((int) Snapchat.DPToPixel(5.0F, context), 0));
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(actionBarHeight));

        /*int drawableResourceId = this.getResources().getIdentifier("melanie", "drawable", this.getPackageName());

        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(drawableResourceId)
                .into(ivMyStories);*/
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load("https://picsum.photos/200/300/?image=73")
                .into(ivMyStories);

        requestOptions = new RequestOptions();
        requestOptions.fitCenter();
        //requestOptions.transform(new RoundedCornersTransformation((int) Snapchat.DPToPixel(5.0F, context), 0));
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners((int) Snapchat.DPToPixel(1.0F, getApplicationContext())));

        /*Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(drawableResourceId)
                .into(ivLastMemory);*/

        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load("https://picsum.photos/200/300/?image=73")
                .into(ivLastMemory);

        vFlash.setTag(false);
        vCamera.setTag(false);
        vNightMode.setTag(false);

        vFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlash.setTag(toggleSetting(vFlash, getDrawable(R.drawable.ic_flash_on), getDrawable(R.drawable.ic_flash_off), Boolean.parseBoolean(vFlash.getTag().toString())));
            }
        });

        vCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vCamera.setTag(toggleSetting(vCamera, getDrawable(R.drawable.ic_face_camera), getDrawable(R.drawable.ic_back_camera), Boolean.parseBoolean(vCamera.getTag().toString())));
            }
        });

        vNightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vNightMode.setTag(toggleSetting(vNightMode, getDrawable(R.drawable.ic_night_mode_on), getDrawable(R.drawable.ic_night_mode_off), Boolean.parseBoolean(vNightMode.getTag().toString())));
            }
        });

        mViewPager.setCurrentItem(mMainPageIndex);
    }

    public Boolean toggleSetting(View view, Drawable onDrawable, Drawable offDrawable, Boolean b) {
        if (!b) {
            view.setBackground(onDrawable);
        } else {
            view.setBackground(offDrawable);
        }

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1, 1.2F, 1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1, 1.2F, 1);
        animatorX.setInterpolator(new BounceInterpolator(0.2, 10));
        animatorY.setInterpolator(new BounceInterpolator(0.2, 10));
        animatorX.setDuration(500);
        animatorY.setDuration(500);
        animatorX.start();
        animatorY.start();

        return !b;
    }

}

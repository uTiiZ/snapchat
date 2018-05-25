package com.utiiz.snapchat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.utiiz.snapchat.R;
import com.utiiz.snapchat.interfaces.SnapchatInterface;

import butterknife.ButterKnife;

public class CameraFragment extends Fragment {

    public LinearLayout mLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, mLinearLayout);
        mLinearLayout.setTag(SnapchatInterface.CAMERA_PAGE_INDEX);

        //mNestedScrollView.setPadding(0, mNestedScrollView.getPaddingTop() + Snapchat.getStatusBarHeight(getContext()), 0, 0);

        return mLinearLayout;
    }

}

package com.utiiz.snapchat.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.utiiz.snapchat.R;
import com.utiiz.snapchat.adapter.DiscoverAdapter;
import com.utiiz.snapchat.model.Chat;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RecyclerViewFragment extends Fragment {

    //@NonNull @BindView(R.id.recycler_view) public RecyclerView mRecyclerView;

    public LinearLayout mLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, mLinearLayout);

        final List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));
        chatList.add(new Chat("Hello"));
        chatList.add(new Chat("World"));

        final DiscoverAdapter discoverAdapter = new DiscoverAdapter(chatList);
        /*mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutFrozen(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/

        return mLinearLayout;
    }

}

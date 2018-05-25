package com.utiiz.snapchat.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.utiiz.snapchat.R;
import com.utiiz.snapchat.helper.Snapchat;
import com.utiiz.snapchat.interfaces.SnapchatInterface;
import com.utiiz.snapchat.model.Chat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by t.kervran on 09/03/2017.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> implements Filterable {

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    private List<Chat> mChatList;
    private List<Chat> mChatListFiltered;
    private Context context;

    public FriendAdapter(List<Chat> chatList) {
        this.mChatList = chatList;
    }

    public class FriendHolder extends RecyclerView.ViewHolder {
        public View v;
        @Nullable @BindView(R.id.username) public TextView tvUsername;
        @Nullable @BindView(R.id.info) public TextView tvInfo;
        @Nullable @BindView(R.id.icon_friend) public ImageView ivIconFriend;
        @Nullable @BindView(R.id.line) public View vLine;
        @Nullable @BindView(R.id.stories) public ImageView vStories;
        @Nullable @BindView(R.id.see_again) public View vSeeAgain;

        public FriendHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, final int position) {
        final Chat chat = mChatList.get(position);
        holder.tvUsername.setText(chat.getName());

        RequestOptions requestOptions = new RequestOptions();

        if (new Random().nextInt(50 + 1) - 1 > 45) {
            requestOptions.fitCenter();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners((int) Snapchat.DPToPixel(72.0F, context)));


            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("https://picsum.photos/200/300/?image=" + new Random().nextInt(1084 + 1))
                    .into(holder.vStories);

            holder.vStories.setBackground(null);
        } else {
            holder.vSeeAgain.setVisibility(View.GONE);
            requestOptions.fitCenter();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners((int) Snapchat.DPToPixel(72.0F, context)));


            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load("https://picsum.photos/200/300/?image=" + new Random().nextInt(1084 + 1))
                    .into(holder.vStories);
        }

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load("https://picsum.photos/200/300/?image=" + new Random().nextInt(1084 + 1))
                .into(holder.vStories);

        requestOptions = new RequestOptions();
        requestOptions.fitCenter();

        switch (position) {
            case 0:
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load("https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/drooling-face_1f924.png")
                        .into(holder.ivIconFriend);
                break;
            case 1:
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load("https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/thinking-face_1f914.png")
                        .into(holder.ivIconFriend);
                break;
            case 3:
                Glide.with(context)
                        .setDefaultRequestOptions(requestOptions)
                        .load("https://emojipedia-us.s3.amazonaws.com/thumbs/72/facebook/138/serious-face-with-symbols-covering-mouth_1f92c.png")
                        .into(holder.ivIconFriend);
                break;
        }



        if (position == mChatList.size() - 1) {
            holder.vLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_friend, parent, false);
        return new FriendHolder(v);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mChatListFiltered = mChatList;
                } else {
                    List<Chat> filteredList = new ArrayList<>();
                    for (Chat c : mChatList) {
                        if (c.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(c);
                        }
                    }

                    mChatListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mChatListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mChatListFiltered = (ArrayList<Chat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void update(List<Chat> chatList) {
        this.mChatList.clear();
        this.mChatList = chatList;
        notifyDataSetChanged();
    }
}

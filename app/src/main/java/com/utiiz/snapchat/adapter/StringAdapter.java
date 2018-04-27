package com.utiiz.snapchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.utiiz.snapchat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by t.kervran on 09/03/2017.
 */

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.MyViewHolder> implements Filterable {

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }

    private List<String> stringList;
    private List<String> stringListFiltered;
    private Context context;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View v;
        @NonNull @BindView(R.id.string) public TextView tvString;

        public MyViewHolder(View view) {
            super(view);
            v = view;
            ButterKnife.bind(this, v);
        }
    }

    public StringAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final String string = stringList.get(position);
        holder.tvString.setText(string);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_string, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stringListFiltered = stringList;
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String s : stringList) {
                        if (s.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(s);
                        }
                    }

                    stringListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stringListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stringListFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

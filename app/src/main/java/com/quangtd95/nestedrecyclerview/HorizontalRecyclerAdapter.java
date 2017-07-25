package com.quangtd95.nestedrecyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Quang_TD on 3/21/16.
 */
class HorizontalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Integer> mList;
    private OnItemClickListener mItemClickListener;

    HorizontalRecyclerAdapter(ArrayList<Integer> list) {
        this.mList = list;
    }

    private class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textView;

        CellViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            }
            return false;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            default: {
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_list_item_type_title, viewGroup, false);
                return new CellViewHolder(v1);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            default: {
                CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
                cellViewHolder.textView.setText(String.valueOf(mList.get(position)));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // for both short and long click
    void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
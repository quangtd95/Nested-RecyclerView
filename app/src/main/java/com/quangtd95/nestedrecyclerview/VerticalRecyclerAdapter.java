package com.quangtd95.nestedrecyclerview;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * QuangTD on 3/21/16.
 */
class VerticalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ArrayList<Integer>> mList;
    private SparseIntArray listPosition = new SparseIntArray();
    private HorizontalRecyclerAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;

    VerticalRecyclerAdapter(ArrayList<ArrayList<Integer>> list) {
        this.mList = list;
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;

        CellViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        switch (viewType) {
            case 0: {
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_list_item_vertical, viewGroup, false);
                return new CellViewHolder(v1);
            }
            case 1:
                View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blank, viewGroup, false);
                return new CellViewHolder(v2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case 0: {
                CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
                cellViewHolder.mRecyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                cellViewHolder.mRecyclerView.setLayoutManager(layoutManager);
                HorizontalRecyclerAdapter adapter = new HorizontalRecyclerAdapter(mList.get(position));
                cellViewHolder.mRecyclerView.setAdapter(adapter);
                adapter.SetOnItemClickListener(mItemClickListener);

                int lastSeenFirstPosition = listPosition.get(position, 0);
                if (lastSeenFirstPosition >= 0) {
                    cellViewHolder.mRecyclerView.scrollToPosition(lastSeenFirstPosition);
                }
                break;
            }
            case 1:
            default:
                break;
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == 0) {
            final int position = viewHolder.getAdapterPosition();
            CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
            LinearLayoutManager layoutManager = ((LinearLayoutManager) cellViewHolder.mRecyclerView.getLayoutManager());
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            listPosition.put(position, firstVisiblePosition);
        }
        super.onViewRecycled(viewHolder);
    }


    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override public int getItemViewType(int position) {
        return (position % 10 == 0) ? 0 : 1;
    }

    // for both short and long click
    public void SetOnItemClickListener(final HorizontalRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
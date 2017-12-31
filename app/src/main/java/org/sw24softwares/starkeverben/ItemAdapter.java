package org.sw24softwares.starkeverben;

import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

class ItemAdapter extends DragItemAdapter<Pair<Long, String>, ItemAdapter.ViewHolder> {

        private int mLayoutId;
        private int mGrabHandleId;
        private boolean mDragOnLongPress;

        ItemAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
                mLayoutId = layoutId;
                mGrabHandleId = grabHandleId;
                mDragOnLongPress = dragOnLongPress;
                setItemList(list);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
                return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                String text = mItemList.get(position).second;
                holder.mText.setText(text);
                holder.itemView.setTag(mItemList.get(position));
        }

        @Override
        public long getUniqueItemId(int position) {
                return mItemList.get(position).first;
        }

        class ViewHolder extends DragItemAdapter.ViewHolder {
                TextView mText;

                ViewHolder(final View itemView) {
                        super(itemView, mGrabHandleId, mDragOnLongPress);
                        mText = (TextView) itemView.findViewById(R.id.text);
                }

                @Override
                public void onItemClicked(View view) {
                        Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onItemLongClicked(View view) {
                        Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
                        return true;
                }
        }
}

package org.sw24softwares.starkeverben;

import org.sw24softwares.starkeverben.BuildConfig;
import android.content.DialogInterface;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import android.widget.TextView;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

public class FormOrderActivity extends AppCompatActivity {
        private ArrayList<Pair<Long, String>> mItemArray;
        private DragListView mDragListView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_formorder);

                mDragListView = (DragListView) findViewById(R.id.drag_list_view);
                mDragListView.setScrollingEnabled(false);
                mDragListView.setDragListListener(new DragListView.DragListListenerAdapter() {
                                @Override
                                public void onItemDragStarted(int position) {
                                }
                                @Override
                                public void onItemDragEnded(int fromPosition, int toPosition) {
                                }
                        });

                mItemArray = new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                        mItemArray.add(new Pair<>((long) i, "Item " + i));
                }
        
                mDragListView.setLayoutManager(new LinearLayoutManager(this));
                ItemAdapter listAdapter = new ItemAdapter(mItemArray,
                                                          R.layout.list_item,
                                                          R.id.item_layout,
                                                          false);
                mDragListView.setAdapter(listAdapter, true);
                mDragListView.setCanDragHorizontally(false);
                mDragListView.setCustomDragItem(new MyDragItem(this, R.layout.list_item));
        }
        private static class MyDragItem extends DragItem {
                MyDragItem(Context context, int layoutId) {
                        super(context, layoutId);
                }

                @Override
                public void onBindDragView(View clickedView, View dragView) {
                        CharSequence text = ((TextView) clickedView.findViewById(R.id.text)).getText();
                        ((TextView) dragView.findViewById(R.id.text)).setText(text);
                        dragView.findViewById(R.id.item_layout).setBackgroundColor(dragView.getResources().getColor(R.color.list_item_background));
                }
        }
}

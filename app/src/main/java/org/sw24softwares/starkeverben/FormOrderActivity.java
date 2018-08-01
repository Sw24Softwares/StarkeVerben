package org.sw24softwares.starkeverben;

import org.sw24softwares.starkeverben.BuildConfig;
import android.content.DialogInterface;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import android.widget.TextView;
import android.widget.Button;

import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Set;
import android.util.ArraySet;

public class FormOrderActivity extends AppCompatActivity {
    private ArrayList<Integer> mItemArray;
    private DragListView mDragListView;
    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formorder);

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.setScrollingEnabled(false);

        mItemArray = new ArrayList<>();
        String formOrderPref = mSharedPref.getString("formOrder", null);
        if(formOrderPref == null)
            for(int i = 0; i < 6; i++)
                mItemArray.add(i);
        else
            for(char c : formOrderPref.toCharArray())
                mItemArray.add(Character.getNumericValue(c));

        mDragListView.setLayoutManager(new LinearLayoutManager(this));
        ItemAdapter listAdapter =
            new ItemAdapter(mItemArray, R.layout.list_item, R.id.item_layout, false);
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
            dragView.findViewById(R.id.item_layout)
                .setBackgroundColor(dragView.getResources().getColor(R.color.list_item_background));
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        String string = new String();
        for(int i = 0; i < mItemArray.size(); i++)
            string += Integer.toString(mItemArray.get(i));
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString("formOrder", string);
        editor.commit();
        FormOrderActivity.this.finish();
    }
}

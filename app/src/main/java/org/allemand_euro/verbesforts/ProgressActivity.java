package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {
        ExpandableListAdapter mListAdapter;
        ExpandableListView mExpListView;
        List<String> mListDataHeader;
        HashMap<String, List<String>> mListDataChild;
        DatabaseHelper mDatabaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_progress);

                mDatabaseHelper = new DatabaseHelper(this);

                mExpListView = (ExpandableListView) findViewById(R.id.progress_list);
                prepareListData();
                
                mListAdapter = new ExpandableListAdapter(this, mListDataHeader, mListDataChild);
                mExpListView.setAdapter(mListAdapter);
        }
        private void prepareListData() {
                mListDataHeader = new ArrayList<String>();
                mListDataChild = new HashMap<String, List<String>>();
 
                Cursor data = mDatabaseHelper.getListContents();

                while(data.moveToNext()){
                       mListDataHeader.add(data.getString(1));
                       List<String> details = new ArrayList<String>();
                       String results[] = data.getString(2).split(" ");
                       for(int i = 0; i < results.length; i++) {
                               details.add(results[i] + " / 5");
                       }
                       mListDataChild.put(mListDataHeader.get(mListDataHeader.size()-1), details);
                }

        }
}

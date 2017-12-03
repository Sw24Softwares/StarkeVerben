package org.sw24softwares.starkeverben;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.widget.ExpandableListView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class ProgressFragment extends Fragment {
        ExpandableListAdapter mListAdapter;
        ExpandableListView mExpListView;
        List<String> mListDataHeader;
        HashMap<String, List<String>> mListDataChild;
        DatabaseHelper mDatabaseHelper;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.activity_progress, container, false);

                Context context = getActivity();

                mDatabaseHelper = new DatabaseHelper(context);

                mExpListView = (ExpandableListView) view.findViewById(R.id.progress_list);
                prepareListData();
                
                mListAdapter = new ExpandableListAdapter(context, mListDataHeader, mListDataChild);
                mExpListView.setAdapter(mListAdapter);
                
                return view;
        }
        private void prepareListData() {
                mListDataHeader = new ArrayList<String>();
                mListDataChild = new HashMap<String, List<String>>();
 
                Cursor data = mDatabaseHelper.getListContents();
                data.moveToLast();
                data.moveToNext();

                while(data.moveToPrevious()){
                        String s [] = data.getString(1).split(" ");
                        int onTwenty = Math.round(Integer.parseInt(s[2]) * 2 / 10);
                        mListDataHeader.add(s[0] + " " + getString(R.string.at) + " "  + s[1] + " : " + s[2] + "% - (" + String.valueOf(onTwenty) + "/20)");
                        List<String> details = new ArrayList<String>();
                        String results[] = data.getString(2).split(" ");
                        for(int i = 0; i < results.length; i++)
                                details.add(results[i] + " / 5");
                        mListDataChild.put(mListDataHeader.get(mListDataHeader.size()-1), details);
                }

        }
}

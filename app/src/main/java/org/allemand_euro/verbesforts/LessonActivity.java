package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class LessonActivity extends AppCompatActivity {
        ExpandableListAdapter listAdapter;
        ExpandableListView expListView;
        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;
 
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_lesson);
                
                // get the listview
                expListView = (ExpandableListView) findViewById(R.id.lesson_list);
 
                // preparing list data
                prepareListData();
 
                listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
                // setting list adapter
                expListView.setAdapter(listAdapter);
        }
 
        /*
         * Preparing the list data
         */
        private void prepareListData() {
                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();
 
                // Adding child data
                listDataHeader.add("Top 250");
                listDataHeader.add("Now Showing");
                listDataHeader.add("Coming Soon..");
 
                // Adding child data
                List<String> top250 = new ArrayList<String>();
                top250.add("The Shawshank Redemption");
                top250.add("The Godfather");
                top250.add("The Godfather: Part II");
                top250.add("Pulp Fiction");
                top250.add("The Good, the Bad and the Ugly");
                top250.add("The Dark Knight");
                top250.add("12 Angry Men");
 
                List<String> nowShowing = new ArrayList<String>();
                nowShowing.add("The Conjuring");
                nowShowing.add("Despicable Me 2");
                nowShowing.add("Turbo");
                nowShowing.add("Grown Ups 2");
                nowShowing.add("Red 2");
                nowShowing.add("The Wolverine");
 
                List<String> comingSoon = new ArrayList<String>();
                comingSoon.add("2 Guns");
                comingSoon.add("The Smurfs 2");
                comingSoon.add("The Spectacular Now");
                comingSoon.add("The Canyons");
                comingSoon.add("Europa Report");
 
                listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
                listDataChild.put(listDataHeader.get(1), nowShowing);
                listDataChild.put(listDataHeader.get(2), comingSoon);
        }
}

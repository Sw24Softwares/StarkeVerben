package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Set;

import android.content.Intent;
import android.app.SearchManager;

import android.widget.ExpandableListView;

public class LessonActivity extends AppCompatActivity {
        ExpandableListAdapter listAdapter;
        ExpandableListView expListView;
        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;
 
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_lesson);

                onSearchRequested();
                
                expListView = (ExpandableListView) findViewById(R.id.lesson_list);
                
                // Get the intent, verify the action and get the query
                Intent intent = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                        String query = intent.getStringExtra(SearchManager.QUERY);
                        search(query);
                }
        }
 
        private void prepareListData(String search_word) {
                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();

                Vector<Verb> verbs = Loader.GetSingleton().mVerbs;
                // Adding child data
                for(int i = 0; i < verbs.size(); i++) {
                        Boolean contain = false;
                        List<String> details = new ArrayList<String>();
                        for(int j = 0; j < verbs.get(i).mForms.size(); j++) {
                                String caseVerb = Questions.FormToWord(j) + " : ";
                                for(int k = 0; k < verbs.get(i).mForms.get(j).size(); k++) {
                                        if(verbs.get(i).mForms.get(j).get(k).contains(search_word))
                                                contain = true;
                                        caseVerb += verbs.get(i).mForms.get(j).get(k);
                                        if(k < verbs.get(i).mForms.get(j).size() - 1)
                                                caseVerb += ", ";
                                }
                                details.add(caseVerb);
                        }
                        if(contain) {
                                listDataHeader.add(verbs.get(i).mForms.get(0).get(0));
                                listDataChild.put(listDataHeader.get(listDataHeader.size()-1), details);
                        }
                }
        }
        private void search(String word) {
                prepareListData(word);
                listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
        }
}

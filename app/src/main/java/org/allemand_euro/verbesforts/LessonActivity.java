package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
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
                
                expListView = (ExpandableListView) findViewById(R.id.lesson_list);
                prepareListData();
                
                listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
        }
 
        private void prepareListData() {
                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();

                Vector<Verb> verbs = Loader.GetSingleton().mVerbs;
                // Adding child data
                for(int i = 0; i < verbs.size(); i++) {
                        listDataHeader.add(verbs.get(i).mForms.get(0).get(0));
                        List<String> details = new ArrayList<String>();
                        for(int j = 0; j < verbs.get(i).mForms.size(); j++) {
                                String caseVerb = Questions.FormToWord(j) + " : ";
                                for(int k = 0; k < verbs.get(i).mForms.get(j).size(); k++) {
                                        caseVerb += verbs.get(i).mForms.get(j).get(k);
                                        if(k < verbs.get(i).mForms.get(j).size() - 1)
                                                caseVerb += ", ";
                                }
                                details.add(caseVerb);
                        }
                        listDataChild.put(listDataHeader.get(i), details);
                }
        }
}

package org.sw24softwares.starkeverben;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Set;
import java.util.Collections;
import java.util.Locale;

import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LessonFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lesson, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.lesson_search);
        // Initializing the search bar with the SearchManager
        ((MainActivity) getActivity()).initSearch(searchView);

        expListView = (ExpandableListView) view.findViewById(R.id.lesson_list);
        // Initiates the ListView with all the verbs
        search(new String());

        return view;
    }

    private void prepareListData(String search_word) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Resources res = GlobalData.getLocalizedResources(
            getActivity(), new Locale(sharedPref.getString("prefLanguage", "")));
        Vector<Verb> verbs = Settings.getSingleton().getVerbs();
        // Adding child data
        for(int i = 0; i < verbs.size(); i++) {
            Boolean contain = false;
            List<String> details = new ArrayList<String>();
            for(int j = 0; j < verbs.get(i).getAllForms().size(); j++) {
                String caseVerb =
                    Verb.formToWord(j) + " : " + verbs.get(i).getPrintedForm(j, false);
                for(int k = 0; k < verbs.get(i).getAllForms().get(j).size(); k++)
                    if(verbs.get(i).getAllForms().get(j).get(k).contains(search_word))
                        contain = true;
                details.add(caseVerb);
            }
            String caseVerb =
                Verb.formToWord(5) + " : " + Verb.boolToAux(verbs.get(i).getAuxiliary());
            details.add(caseVerb);
            String[] translations = res.getStringArray(
                res.getIdentifier(GlobalData.decompose(verbs.get(i).getAllForms().get(0).get(0)),
                                  "array", getActivity().getPackageName()));
            /*String*/ caseVerb =
                Verb.formToWord(4) + " : " + GlobalData.getList(translations, ", ");
            for(int j = 0; j < translations.length; j++) {
                if(translations[j].contains(search_word))
                    contain = true;
            }
            details.add(caseVerb);
            if(contain || search_word == new String()) {
                String suffix = "";
                int n =
                    Collections.frequency(listDataHeader, verbs.get(i).getAllForms().get(0).get(0));
                for(int j = 0; j < n; j++)
                    suffix += "\0";
                listDataHeader.add(verbs.get(i).getAllForms().get(0).get(0) + suffix);
                listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), details);
            }
        }
    }
    protected void search(String word) {
        prepareListData(word);
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }
}

package org.sw24softwares.starkeverben;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import org.sw24softwares.starkeverben.Core.Settings;
import org.sw24softwares.starkeverben.Core.Verb;
import org.sw24softwares.starkeverben.Core.VerbWithTranslation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class LessonFragment extends Fragment {
    ExpandableListView mExpListView;
    List<String> mListDataHeader;
    HashMap<String, List<String>> mListDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lesson, container, false);

        // Initializing the search bar with the SearchManager
        SearchView searchView = view.findViewById(R.id.lesson_search);
        ((MainActivity) getActivity()).initSearch(searchView);

        mExpListView = view.findViewById(R.id.lesson_list);

        // Initiates the ListView with all the verbs
        search("");

        return view;
    }

    private void prepareListData(String search_word) {
        mListDataHeader = new ArrayList<>();
        mListDataChild = new HashMap<>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Locale l = GlobalData.getTranslationLocale(sharedPref);
        Resources res = GlobalData.getLocalizedResources(getActivity(), l);

        // Creave Verbs with Translations
        Vector<Verb> verbs = Settings.getSingleton().getVerbs();
        Vector<VerbWithTranslation> verbsWithTranslation = new Vector<>();
        for (Verb v : verbs)
            verbsWithTranslation.add(GlobalData.androidVWTCreate(verbs, v, getActivity(), res));

        // Adding child data
        for (Verb v : verbsWithTranslation) {
            Boolean contain = false;
            List<String> details = new ArrayList<>();
            for (int j = 0; j < v.getAllForms().size(); j++) {
                String caseVerb = v.getPrintedForm(j, false);
                for (int k = 0; k < v.getAllForms().get(j).size(); k++)
                    if (v.getAllForms().get(j).get(k).contains(search_word))
                        contain = true;
                details.add(caseVerb);
            }
            String caseVerb = Verb.boolToAux(v.getAuxiliary());
            details.add(caseVerb);

            if (contain || search_word.isEmpty()) {
                // Manage same infinitive verbs
                StringBuilder suffix = new StringBuilder();
                int n = Collections.frequency(mListDataHeader, v.getInfinitives().get(0));
                for (int j = 0; j < n; j++)
                    suffix.append("\0");
                mListDataHeader.add(v.getInfinitives().get(0) + suffix);
                mListDataChild.put(mListDataHeader.get(mListDataHeader.size() - 1), details);
            }
        }
    }

    protected void search(String word) {
        prepareListData(word);
        ExpandableListAdapter listAdapter =
                new ExpandableListAdapter(getActivity(), mListDataHeader, mListDataChild);
        mExpListView.setAdapter(listAdapter);
    }
}

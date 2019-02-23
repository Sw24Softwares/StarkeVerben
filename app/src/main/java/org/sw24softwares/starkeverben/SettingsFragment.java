package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {
    public String[] addElementToArray(String[] baseArray, String obj) {
        List<String> temp = new ArrayList<>(Arrays.asList(baseArray));
        temp.add(obj);
        String[] simpleArray = new String[temp.size()];
        temp.toArray(simpleArray);
        return simpleArray;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        ListPreference guiList = (ListPreference) findPreference("prefLanguage");

        PreferenceScreen screen = (PreferenceScreen) findPreference("prefAbout");
        screen.setIntent(new Intent(getActivity(), AboutActivity.class));
        /*PreferenceScreen*/
        screen = (PreferenceScreen) findPreference("prefFormOrder");
        screen.setIntent(new Intent(getActivity(), FormOrderActivity.class));
    }
}

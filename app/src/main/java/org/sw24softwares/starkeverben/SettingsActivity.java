package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.Context;

import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class SettingsActivity  extends PreferenceActivity {
        public String[] addElementToArray(String[] baseArray, String obj) {
                List<String> temp = new ArrayList<String>(Arrays.asList(baseArray));
                temp.add(obj);
                String[] simpleArray = new String[temp.size()];
                temp.toArray(simpleArray);
                return simpleArray;
        }
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
                try {
                        ListPreference guiList = (ListPreference)findPreference("prefLanguage");
                        guiList.setEntries(addElementToArray(getAssets().list("Translations"),
                                                             getString(R.string.pref_language_translation_default)));
                        guiList.setEntryValues(addElementToArray(getAssets().list("Translations"),
                                                                 GlobalData.getLocalizedResources(this, Locale.ENGLISH).getString(R.string.pref_language_translation_default)));
                        guiList.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                                        @Override
                                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                                                try {
                                                        GlobalData.loadVerbs(SettingsActivity.this, (String)newValue);
                                                } catch (Exception e) {
                                                        Log.e("StarkeVerben", e.getMessage());
                                                }
                                                return true;
                                        }
                        });
                }
                catch (IOException e) {
                        System.exit(0);
                }

                PreferenceScreen screen = (PreferenceScreen) findPreference("prefAbout");
                screen.setIntent(new Intent(this, AboutActivity.class));
                //screen = (PreferenceScreen) findPreference("prefFormOrder");
                //screen.setIntent(new Intent(this, FormOrderActivity.class));

	}
}

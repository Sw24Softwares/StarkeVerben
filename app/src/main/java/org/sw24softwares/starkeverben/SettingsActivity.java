package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.ListPreference;

import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.Context;

import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SettingsActivity  extends PreferenceActivity {
        public String[] addElementToArray(String[] baseArray, String obj) {
                List<String> temp = new ArrayList<String>(Arrays.asList(baseArray));
                temp.add(obj);
                String[] simpleArray = new String[temp.size()];
                temp.toArray(simpleArray);
                return simpleArray;
        }
        Resources getLocalizedResources(Context context, Locale desiredLocale) {
                Configuration conf = context.getResources().getConfiguration();
                conf = new Configuration(conf);
                conf.setLocale(desiredLocale);
                Context localizedContext = context.createConfigurationContext(conf);
                return localizedContext.getResources();
        }
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
                try {
                        ListPreference guiList = (ListPreference)findPreference("prefLanguage");
                        guiList.setEntries(addElementToArray(getAssets().list("Translations"),
                                                             getString(R.string.pref_language_translation_default)));
                        guiList.setEntryValues(addElementToArray(getAssets().list("Translations"),
                                                                 getLocalizedResources(this, Locale.ENGLISH).getString(R.string.pref_language_translation_default)));
                }
                catch (IOException e) {
                        System.exit(0);
                }
	}
}

package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends PreferenceActivity {
    public String[] addElementToArray(String[] baseArray, String obj) {
        List<String> temp = new ArrayList<>(Arrays.asList(baseArray));
        temp.add(obj);
        String[] simpleArray = new String[temp.size()];
        temp.toArray(simpleArray);
        return simpleArray;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        ListPreference guiList = (ListPreference) findPreference("prefLanguage");

        PreferenceScreen screen = (PreferenceScreen) findPreference("prefAbout");
        screen.setIntent(new Intent(this, AboutActivity.class));
        /*PreferenceScreen*/
        screen = (PreferenceScreen) findPreference("prefFormOrder");
        screen.setIntent(new Intent(this, FormOrderActivity.class));
    }

    // Method for supporting the toolbar
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root =
                (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
        bar.setTitle(R.string.pref);
        root.addView(bar, 0);// insert at top
        bar.setNavigationOnClickListener(v -> finish());
    }
}

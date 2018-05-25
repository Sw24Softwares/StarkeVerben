package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.Context;

import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;

import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
    public String[] addElementToArray(String[] baseArray, String obj) {
        List<String> temp = new ArrayList<String>(Arrays.asList(baseArray));
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
        //*PreferenceScreen*/ screen = (PreferenceScreen) findPreference("prefFormOrder");
        //screen.setIntent(new Intent(this, FormOrderActivity.class));
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
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

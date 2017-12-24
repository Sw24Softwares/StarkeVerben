package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.Context;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Arrays;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

        Resources getLocalizedResources(Context context, Locale desiredLocale) {
                Configuration conf = context.getResources().getConfiguration();
                conf = new Configuration(conf);
                conf.setLocale(desiredLocale);
                Context localizedContext = context.createConfigurationContext(conf);
                return localizedContext.getResources();
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);
                
                BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation);
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_subject_black_24dp, R.string.test))
                        .addItem(new BottomNavigationItem(R.drawable.ic_timeline_black_24dp, R.string.progression))
                        .addItem(new BottomNavigationItem(R.drawable.ic_learn_black_24dp, R.string.single_lesson))
                        .addItem(new BottomNavigationItem(R.drawable.ic_list_black_24dp, R.string.lesson))
                        .setBarBackgroundColor("#FFFFFF")
                        .setActiveColor(R.color.colorPrimary)
                        .setMode(BottomNavigationBar.MODE_FIXED)
                        .initialise();
                
                Settings.getSingleton().setDebug(BuildConfig.DEBUG);
                try {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                        String languagePref = sharedPref.getString("prefLanguage", "");

                        BufferedReader verbs = null, trans = null;
                        verbs = new BufferedReader(new InputStreamReader(getAssets().open("verbs.txt"), "UTF-8"));
                        String translationPath = languagePref;
                        if(languagePref == getLocalizedResources(this, Locale.ENGLISH).getString(R.string.pref_language_translation_default)) {
                                String localeLanguagePath = getResources().getConfiguration().locale.getDisplayLanguage(Locale.ENGLISH);
                                if(Arrays.asList(getResources().getAssets().list("Translations/")).contains(localeLanguagePath))
                                        translationPath = localeLanguagePath;
                                else
                                        translationPath = "English";
                        }
                        trans = new BufferedReader(new InputStreamReader(getAssets().open("Translations/" + translationPath), "UTF-8"));

                        Resources res = getResources();
                        Settings.getSingleton().setFormString(0,res.getString(R.string.infinitive));
                        Settings.getSingleton().setFormString(1,res.getString(R.string.preterite));
                        Settings.getSingleton().setFormString(2,res.getString(R.string.participe));
                        Settings.getSingleton().setFormString(3,res.getString(R.string.third_person));
                        Settings.getSingleton().setFormString(4,res.getString(R.string.traduction));
                        Settings.getSingleton().setFormString(5,res.getString(R.string.auxiliary));
                        
                        VerbsLoader vl = new VerbsLoader();
                        vl.load(verbs);
                        TranslationLoader tl = new TranslationLoader();
                        tl.load(trans);

                        Settings.getSingleton().setVerbs(vl.getVerbs());
                        Settings.getSingleton().setTranslations(tl.getTranslations());
                } catch (Exception e) {
                        Log.e("StarkeVerben", e.getMessage());
                }

                Button testButton = (Button) findViewById(R.id.test);
                testButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, TestActivity.class);
                                        intent.putExtra("total", 0);
                                        intent.putExtra("marks", new int[0]);
                                        intent.putExtra("dialog", false);
                                        startActivity(intent);
                                }
                        });
                Button lessonButton = (Button) findViewById(R.id.lesson);
                lessonButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, LessonActivity.class);
                                        startActivity(intent);
                                }
                        });
                Button progressButton = (Button) findViewById(R.id.progress);
                progressButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, ProgressTabsActivity.class);
                                        startActivity(intent);
                                }
                        });
                Button singlelessonButton = (Button) findViewById(R.id.single_lesson);
                singlelessonButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, SingleLessonActivity.class);
                                        startActivity(intent);
                                }
                        });
        }
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.toolbar_main, menu);
                return true;
        }
        
        @Override
        public void onBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                case R.id.action_settings:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                default:
                        return super.onOptionsItemSelected(item);
                }
        }
}

package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Locale;
import java.util.Arrays;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Settings.getSingleton().setDebug(BuildConfig.DEBUG);
                try {
                        BufferedReader verbs = null, trans = null;
                        verbs = new BufferedReader(new InputStreamReader(getAssets().open("verbs.txt"), "UTF-8"));
                        String translationPath = "English.txt";
                        String localeLanguagePath = getResources().getConfiguration().locale.getDisplayLanguage(Locale.ENGLISH) + ".txt";
                        if(Arrays.asList(getResources().getAssets().list("")).contains(localeLanguagePath))
                                translationPath = localeLanguagePath; 
                        trans = new BufferedReader(new InputStreamReader(getAssets().open(translationPath), "UTF-8"));

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
                                        Intent intent = new Intent(MainActivity.this, ProgressGraphsActivity.class);
                                        //Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
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
        public void onBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
}

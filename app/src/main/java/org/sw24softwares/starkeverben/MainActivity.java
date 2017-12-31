package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
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

        FragmentTransaction mTransaction;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);
                
                Settings.getSingleton().setDebug(BuildConfig.DEBUG);
                try {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                        String languagePref = sharedPref.getString("prefLanguage", "System default");
                        GlobalData.loadVerbs(this, languagePref);
                } catch (Exception e) {
                        Log.e("StarkeVerben", e.getMessage());
                }

                Resources res = getResources();
                Settings.getSingleton().setFormString(0,res.getString(R.string.infinitive));
                Settings.getSingleton().setFormString(1,res.getString(R.string.preterite));
                Settings.getSingleton().setFormString(2,res.getString(R.string.participe));
                Settings.getSingleton().setFormString(3,res.getString(R.string.third_person));
                Settings.getSingleton().setFormString(4,res.getString(R.string.traduction));
                Settings.getSingleton().setFormString(5,res.getString(R.string.auxiliary));

                BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation);
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.ic_subject_black_24dp, R.string.test))
                        .addItem(new BottomNavigationItem(R.drawable.ic_timeline_black_24dp, R.string.progression))
                        .addItem(new BottomNavigationItem(R.drawable.ic_learn_black_24dp, R.string.single_lesson))
                        .addItem(new BottomNavigationItem(R.drawable.ic_list_black_24dp, R.string.lesson))
                        .setActiveColor(R.color.colorPrimary)
                        .setInActiveColor(R.color.inactiveBottomNav)
                        .setBarBackgroundColor(android.R.color.white)
                        .setMode(BottomNavigationBar.MODE_FIXED)
                        .initialise();
                
                mTransaction = getSupportFragmentManager().beginTransaction();
                mTransaction.add(R.id.main_container, new PreTestFragment()).commit();
               
                bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
                        @Override
                        public void onTabSelected(int position) {
                                mTransaction = getSupportFragmentManager().beginTransaction();
                                mTransaction.setCustomAnimations(android.R.anim.fade_in, 0);
                                
                                switch(position) {
                                        case 0:
                                                mTransaction.replace(R.id.main_container, new PreTestFragment());
                                                break;
                                        case 1:
                                                mTransaction.replace(R.id.main_container, new ProgressTabsFragment());
                                                break;
                                        case 2:
                                                mTransaction.replace(R.id.main_container, new SingleLessonFragment());
                                                break;
                                        case 3:
                                                mTransaction.replace(R.id.main_container, new LessonFragment());
                                                break;
                                        default:
                                                break;
                                }
                                mTransaction.commit();
                        }
                        @Override
                        public void onTabUnselected(int position) {
                        }
                        @Override
                        public void onTabReselected(int position) {
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

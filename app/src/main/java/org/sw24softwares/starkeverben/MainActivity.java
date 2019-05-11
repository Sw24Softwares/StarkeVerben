package org.sw24softwares.starkeverben;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import android.preference.PreferenceManager;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;
import androidx.preference.PreferenceFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.sw24softwares.starkeverben.Core.Settings;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction mTransaction;
    private static final String PRE_TEST = "PreTest";
    private static final String PROGRESS = "Progress";
    private static final String SINGLE_LESSON = "SingleLesson";
    private static final String LESSON = "Lesson";
    private static final String SETTINGS = "Settings";
    private static Resources res;

    protected void createTranslateDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(getString(R.string.translate_alert));
        alertDialog.setMessage(getString(R.string.translate_message));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.contribute_trans),
                (dialog, which) -> {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(res.getString(R.string.contribution_url)));
                    startActivity(browserIntent);
                });

        alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, getString(R.string.not_now),
                (dialog, which) -> {
                    SharedPreferences.Editor editor =
                            PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    editor.putBoolean("contribute", false);
                    editor.apply();
                    alertDialog.hide();
                });
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        Settings.getSingleton().setDebug(BuildConfig.DEBUG);
        try {
            GlobalData.loadVerbs(this);
        } catch (Exception e) {
            Log.e("StarkeVerben", e.getMessage());
        }

        res = getResources();

        String s = Locale.getDefault().getLanguage();
        String[] availableLang = res.getStringArray(R.array.language_values);
        if (!Arrays.asList(availableLang).contains(s)
                && PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                .getBoolean("contribute", true)) {
            createTranslateDialog();
        }

        Settings.getSingleton().setFormString(0, res.getString(R.string.infinitive));
        Settings.getSingleton().setFormString(1, res.getString(R.string.preterite));
        Settings.getSingleton().setFormString(2, res.getString(R.string.participe));
        Settings.getSingleton().setFormString(3, res.getString(R.string.third_person));
        Settings.getSingleton().setFormString(4, res.getString(R.string.traduction));
        Settings.getSingleton().setFormString(5, res.getString(R.string.auxiliary));

        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.main_container, new SingleLessonFragment(), SINGLE_LESSON).commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    mTransaction = getSupportFragmentManager().beginTransaction();

                    switch (item.getItemId()) {
                    case R.id.single_lesson:
                        mTransaction.replace(R.id.main_container, new SingleLessonFragment(),
                                             SINGLE_LESSON).commit();
                        return true;
                    case R.id.progression:
                        mTransaction.replace(R.id.main_container, new ProgressFragment(), PROGRESS).commit();
                        return true;
                    case R.id.lesson:
                        mTransaction.replace(R.id.main_container, new LessonFragment(), LESSON).commit();
                        return true;
                    case R.id.settings:
                        mTransaction.replace(R.id.main_container, new SettingsFragment()).commit();
                        return true;
                    }
                    return false;
                }
            });

        handleIntent(getIntent());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Following methods are used for the search bar in the LessonFragment
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        LessonFragment lessonFragment =
                (LessonFragment) getSupportFragmentManager().findFragmentByTag(LESSON);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            lessonFragment.search(query);
        }
    }

    protected void initSearch(SearchView searchView) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by default
    }

    @Override
    protected void onResume() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        int selectedItemId = bottomNav.getSelectedItemId();

        if(selectedItemId == R.id.progression) {
            mTransaction = getSupportFragmentManager().beginTransaction();
            mTransaction.replace(R.id.main_container, new ProgressFragment(), PROGRESS).commit();
        }
        super.onResume();
    }
}

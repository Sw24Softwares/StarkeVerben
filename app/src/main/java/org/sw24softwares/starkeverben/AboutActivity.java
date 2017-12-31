package org.sw24softwares.starkeverben;

import org.sw24softwares.starkeverben.BuildConfig;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.net.Uri;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                AboutPage aboutPage = new AboutPage(this)
                        .isRTL(false)
                        .setImage(R.mipmap.ic_launcher)
                        .addItem(new Element().setTitle("Version " + BuildConfig.VERSION_NAME))
                        .addGroup(getResources().getString(R.string.connect))
                        //.addWebsite("https://f-droid.org/packages/org.sw24softwares.starkeverben/")
                        .addGitHub("Sw24Softwares/StarkeVerben");
                aboutPage = setUpDependencies(aboutPage);
                aboutPage = setUpDevelopers(aboutPage);
                setContentView(aboutPage.create());
        }
        protected AboutPage setUpDependencies(AboutPage ap) {
                ap.addGroup(getResources().getString(R.string.dependencies));
                ap.addItem(addDependencies("MPAndroidChart","https://github.com/PhilJay/MPAndroidChart"));
                ap.addItem(addDependencies("Pulsator4Droid","https://github.com/booncol/Pulsator4Droid"));
                ap.addItem(addDependencies("BottomNavigation","https://github.com/Ashok-Varma/BottomNavigation"));
                ap.addItem(addDependencies("Android About Page","https://github.com/medyo/android-about-page"));
                ap.addItem(addDependencies("Material Design", "https://material.io"));
                return ap;
        }
        protected Element addDependencies(final String title, final String link) {
                Element elem = new Element()
                        .setTitle(title)
                        .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                return elem;
        }
        protected AboutPage setUpDevelopers(AboutPage ap) {
                ap.addGroup(getResources().getString(R.string.developers));
                ap.addItem(addDeveloper("24PaH", "https://github.com/24PaH", R.drawable._24pah));
                ap.addItem(addDeveloper("Swarthon", "https://github.com/Swarthon", R.drawable.swarthon));
                return ap;
        }
        protected Element addDeveloper(final String title, final String link, final Integer icon) {
                Element elem = new Element()
                        .setTitle(title)
                        .setIconDrawable(icon)
                        .setAutoApplyIconTint(false)
                        .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                return elem;
        }
}

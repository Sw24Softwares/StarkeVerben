package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.pref_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AboutPage aboutPage =
                new AboutPage(this)
                        .isRTL(false)
                        .setImage(R.drawable.about_image)
                        .addItem(new Element().setTitle(getResources().getString(R.string.version) + " "
                                + BuildConfig.VERSION_NAME))
                        .addGroup(getResources().getString(R.string.connect))
                        .addGitHub("Sw24Softwares/StarkeVerben", "GitHub")
                        .addItem(addWeblate("projects/starke-verben"))
                        .addItem(addGitter("Sw24Softwares/StarkeVerben"))
                        .addWebsite("https://sw24softwares.github.io/");
        aboutPage = setUpDependencies(aboutPage);
        aboutPage = setUpDevelopers(aboutPage);

        LinearLayout layout = findViewById(R.id.layout);
        layout.addView(aboutPage.create());
    }

    protected AboutPage setUpDependencies(AboutPage ap) {
        ap.addGroup(getResources().getString(R.string.dependencies));
        ap.addItem(addDependencies("MPAndroidChart", "https://github.com/PhilJay/MPAndroidChart"));
        ap.addItem(addDependencies("Pulsator4Droid", "https://github.com/booncol/Pulsator4Droid"));
        ap.addItem(
                addDependencies("BottomNavigation", "https://github.com/Ashok-Varma/BottomNavigation"));
        ap.addItem(
                addDependencies("Android About Page", "https://github.com/medyo/android-about-page"));
        ap.addItem(addDependencies("Material Design Icons", "https://material.io/icons"));
        return ap;
    }

    protected Element addDependencies(final String title, final String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        return new Element().setTitle(title).setIntent(intent);
    }

    protected AboutPage setUpDevelopers(AboutPage ap) {
        ap.addGroup(getResources().getString(R.string.developers));
        ap.addItem(addDeveloper("Hamza Parnica", "https://github.com/24PaH", R.drawable._24pah));
        ap.addItem(
                addDeveloper("Louis Vanhaelewyn", "https://github.com/Swarthon", R.drawable.swarthon));
        return ap;
    }

    protected Element addDeveloper(final String title, final String link, final Integer icon) {
        Element elem = new Element().setTitle(title).setAutoApplyIconTint(false).setIntent(
                new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        if (Build.VERSION.SDK_INT >= 21)
            elem.setIconDrawable(icon);
        return elem;
    }

    protected Element addGitter(final String link) {
        Element elem = new Element().setTitle("Gitter").setAutoApplyIconTint(false).setIntent(
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://gitter.im/" + link)));
        if (Build.VERSION.SDK_INT >= 21)
            elem.setIconDrawable(R.drawable.gitter);
        return elem;
    }

    protected Element addWeblate(final String link) {
        Element elem = new Element().setTitle("Weblate").setAutoApplyIconTint(false).setIntent(
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://hosted.weblate.org/" + link)));
        if (Build.VERSION.SDK_INT >= 21)
            elem.setIconDrawable(R.drawable.weblate);
        return elem;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

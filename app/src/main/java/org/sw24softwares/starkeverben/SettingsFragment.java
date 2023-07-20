package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {
    public String[] addElementToArray(String[] baseArray, String obj) {
        List<String> temp = new ArrayList<>(Arrays.asList(baseArray));
        temp.add(obj);
        String[] simpleArray = new String[temp.size()];
        temp.toArray(simpleArray);
        return simpleArray;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        ListPreference guiList = (ListPreference) findPreference("prefLanguage");

        PreferenceScreen formOrder = (PreferenceScreen) findPreference("prefFormOrder");
        formOrder.setIntent(new Intent(getActivity(), FormOrderActivity.class));

        Preference version = (Preference) findPreference("prefVersion");
        version.setTitle(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

        PreferenceScreen weblate = (PreferenceScreen) findPreference("prefWeblate");
        weblate.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://hosted.weblate.org/projects/starke-verben")));

        PreferenceScreen github = (PreferenceScreen) findPreference("prefGithub");
        github.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Sw24Softwares/StarkeVerben")));

        if(Build.VERSION.SDK_INT >= 21) {
            weblate.setIcon(R.drawable.ic_weblate_black);
            github.setIcon(R.drawable.ic_github_black);
        }

        PreferenceScreen hamzaDev = (PreferenceScreen) findPreference("prefHamzaDev");
        hamzaDev.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/24PaH")));

        PreferenceScreen louisDev = (PreferenceScreen) findPreference("prefLouisDev");
        louisDev.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Swarthon")));

        PreferenceScreen contributors = (PreferenceScreen) findPreference("prefContributors");
        contributors.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Sw24Softwares/StarkeVerben/blob/master/CONTRIBUTORS.md")));
    }
}

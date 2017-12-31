package org.sw24softwares.starkeverben;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Configuration;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Locale;
import java.util.Arrays;

class GlobalData {
        static public Resources getLocalizedResources(Context context, Locale desiredLocale) {
                Configuration conf = context.getResources().getConfiguration();
                conf = new Configuration(conf);
                conf.setLocale(desiredLocale);
                Context localizedContext = context.createConfigurationContext(conf);
                return localizedContext.getResources();
        }
        static public void loadVerbs (Context context, String languagePref)
                throws Exception {
                BufferedReader verbs = null, trans = null;
                verbs = new BufferedReader(new InputStreamReader(context.getAssets().open("verbs.txt"), "UTF-8"));
                String translationPath = languagePref;
                if(languagePref.equals(getLocalizedResources(context, Locale.ENGLISH).getString(R.string.pref_language_translation_default))) {
                        String localeLanguagePath = context.getResources().getConfiguration().locale.getDisplayLanguage(Locale.ENGLISH);
                        if(Arrays.asList(context.getAssets().list("Translations")).contains(localeLanguagePath))
                                translationPath = localeLanguagePath;
                        else
                                translationPath = "English";
                }
                trans = new BufferedReader(new InputStreamReader(context.getAssets().open("Translations/" + translationPath), "UTF-8"));
                
                
                VerbsLoader vl = new VerbsLoader();
                vl.load(verbs);
                TranslationLoader tl = new TranslationLoader();
                tl.load(trans);
                
                Settings.getSingleton().setVerbs(vl.getVerbs());
                Settings.getSingleton().setTranslations(tl.getTranslations());

        }
}

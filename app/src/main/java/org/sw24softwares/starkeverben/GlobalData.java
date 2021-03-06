package org.sw24softwares.starkeverben;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import org.sw24softwares.starkeverben.Core.Settings;
import org.sw24softwares.starkeverben.Core.Verb;
import org.sw24softwares.starkeverben.Core.VerbWithTranslation;
import org.sw24softwares.starkeverben.Core.VerbsLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;

public class GlobalData {
    static public String decompose(String s) {
        return s
                .replace("ß", "ss")
                .replace("ü", "u")
                .replace("ä", "a")
                .replace("ö", "o");
    }

    // Translations global functions
    static public String getTranslationName(Vector<Verb> verbs, Verb goal) {
        int translationNumber = 0;
        for (Verb v : verbs) {
            if (v == goal)
                break;
            if (v.getInfinitives().contains(goal.getInfinitives().get(0)))
                translationNumber++;
        }
        if (translationNumber == 0)
            return goal.getInfinitives().get(0);
        return goal.getInfinitives().get(0) + String.valueOf(translationNumber);
    }

    static public String[] getTranslations(Vector<Verb> verbs, Verb v, Context c,
                                           Resources res) {
        // Get translations from Android
        String cleanInfinitive = GlobalData.decompose(getTranslationName(verbs, v));
        int id = res.getIdentifier(cleanInfinitive, "string", c.getPackageName());
        String unpackedTranslation = res.getString(id);

        // Separate translations
        return unpackedTranslation.split(",");
    }

    static public VerbWithTranslation androidVWTCreate(Vector<Verb> verbs, Verb v, Context c,
                                                       Resources res) {
        String[] translations = getTranslations(verbs, v, c, res);
        Vector<String> transVec = new Vector<>(Arrays.asList(translations));
        return new VerbWithTranslation(v, transVec);
    }

    static public Locale getTranslationLocale(SharedPreferences sharedPref) {
        String codeName = sharedPref.getString("prefLanguage", "");
        if (codeName.equals(""))
            return Locale.getDefault();
        else
            return new Locale(sharedPref.getString("prefLanguage", ""));
    }

    static public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources();
    }

    static public void loadVerbs(Context context) throws Exception {
        BufferedReader verbs = new BufferedReader(
                new InputStreamReader(context.getAssets().open("verbs.txt"), "UTF-8"));

        VerbsLoader vl = new VerbsLoader();
        vl.load(verbs);

        Settings.getSingleton().setVerbs(vl.getVerbs());
    }

    static public String getList(String[] arr, String sep) {
        StringBuilder res = new StringBuilder();
        for (String s : arr)
            res.append(s).append(sep);
        res = new StringBuilder(res.substring(0, res.length() - sep.length()));// Delete end separator
        return res.toString();
    }

    static public String getList(Vector<String> v, String sep) {
        return getList(v.toArray(new String[0]), sep);
    }

    static public <T> Vector<T> oneElementVector(T elem) {
        Vector<T> v = new Vector<>();
        v.add(elem);
        return v;
    }
}

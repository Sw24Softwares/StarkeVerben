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
import java.util.Vector;

class GlobalData {
    static public String decompose(String s) {
        return s.replace("ß", "ss").replace("ü", "u").replace("ä", "a").replace("ö", "o");
    }
    static public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources();
    }
    static public void loadVerbs(Context context, String languagePref) throws Exception {
        BufferedReader verbs = null, trans = null;
        verbs = new BufferedReader(
            new InputStreamReader(context.getAssets().open("verbs.txt"), "UTF-8"));
        String translationPath = languagePref;

        VerbsLoader vl = new VerbsLoader();
        vl.load(verbs);

        Settings.getSingleton().setVerbs(vl.getVerbs());
    }
    static public String getList(String[] arr, String sep) {
        String res = new String();
        for(String s : arr)
            res += s + sep;
        res = res.substring(0, res.length() - sep.length());// Delete end separator
        return res;
    }
    static public String getList(Vector<String> v, String sep) {
        return getList(v.toArray(new String[0]), sep);
    }
    static public <T> Vector<T> oneElementVector(T elem) {
        Vector<T> v = new Vector<T>();
        v.add(elem);
        return v;
    }
}

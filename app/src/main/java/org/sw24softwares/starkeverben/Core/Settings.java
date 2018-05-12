package org.sw24softwares.starkeverben;

import java.util.Vector;

class Settings {
    protected Vector<Verb> mVerbs;
    protected String[] mFormStrings = new String[6];
    protected Boolean mDebug        = false;

    public Settings() {
    }

    // Setters
    public void setVerbs(Vector<Verb> verbs) {
        mVerbs = verbs;
    }
    public void setFormString(int i, String s) {
        mFormStrings[i] = s;
    }
    public void setDebug(Boolean d) {
        mDebug = d;
    }

    // Getters
    public Vector<Verb> getVerbs() {
        return mVerbs;
    }
    public Verb getVerb(int i) {
        for(Verb v : mVerbs)
            if(v.getIndex() == i)
                return v;
        return null;
    }
    public String getFormString(int i) {
        return mFormStrings[i];
    }
    public Boolean isDebug() {
        return mDebug;
    }

    static private Settings mSingleton = new Settings();
    static public Settings getSingleton() {
        return mSingleton;
    }
}

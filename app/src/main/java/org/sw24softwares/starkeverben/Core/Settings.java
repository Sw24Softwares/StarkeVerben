package org.sw24softwares.starkeverben;

import java.util.Vector;

class Settings {
        protected Vector<Translation> mTranslations;
        protected Vector<Verb> mVerbs;

        protected String[] mFormStrings = new String[6];
        
        public Settings() {
        }

        // Setters
        public void setTranslations(Vector<Translation> translations) {
                mTranslations = translations;
        }
        public void setVerbs(Vector<Verb> verbs) {
                mVerbs = verbs;
        }
        public void setFormString(int i, String s) {
                mFormStrings[i] = s;
       }

        // Getters
        public Vector<Translation> getTranslations() {
                return mTranslations;
        }
        public Vector<Verb> getVerbs() {
                return mVerbs;
        }
        public Verb getVerb(int i) {
                for(Verb v : mVerbs)
                        if(v.getIndex() == i)
                                return v;
                return null;
        }
        public Translation getTranslation(int i) {
                for(Translation t : mTranslations)
                        if(t.getIndex() == i)
                                return t;
                return null;
        }
        public String getFormString(int i) {
                return mFormStrings[i];
        }
        
        static private Settings mSingleton = new Settings();
        static public Settings getSingleton() {
                return mSingleton;
        }
}

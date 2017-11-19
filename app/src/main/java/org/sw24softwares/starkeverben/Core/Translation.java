package org.sw24softwares.starkeverben;

import java.util.Vector;

class Translation {
        protected int mIndex;
        protected Vector<String> mTranslations;

        public Translation() {
        }
        public Translation(int index, Vector<String> translations) {
                mIndex = index;
                mTranslations = translations;
        }

        public int getIndex() {
                return mIndex;
        }
        public Vector<String> getTranslations() {
                return mTranslations;
        }
}

package org.allemand_euro.verbesforts;

import android.content.res.Resources;

class Settings {
        private static Settings mInstance = null;
        static Settings GetSingleton() {
                if(mInstance == null)
                        mInstance = new Settings();
                return mInstance;
        }

        private Resources mResources;
        void SetResources(Resources res) {
                mResources = res;
        }
        Resources GetResources() {
                return mResources;
        }
}

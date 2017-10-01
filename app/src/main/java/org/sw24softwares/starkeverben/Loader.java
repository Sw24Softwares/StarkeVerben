package org.sw24softwares.starkeverben;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class Loader {
        public Vector<Verb> mVerbs = new Vector<Verb>();

        private Loader (BufferedReader bufferedReader) {
                try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) 
                                mVerbs.addElement(new Verb(line));
                }
                catch (IOException x) {
                        System.err.format("IOException: %s%n", x);
                }
        }
        static public Loader CreateSingleton(BufferedReader buffer) {
                if(mInstance == null)
                        mInstance = new Loader(buffer);
                return mInstance;
        }

        public static Loader GetSingleton() {
                return mInstance;
        }
        static Loader mInstance = null;
}

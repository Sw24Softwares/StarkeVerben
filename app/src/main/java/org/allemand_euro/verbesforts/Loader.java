package org.allemand_euro.verbesforts;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Vector;

public class Loader {
        public Vector<Verb> mVerbs = new Vector<Verb>();

        public void Load (BufferedReader bufferedReader) {
                try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) 
                                mVerbs.addElement(new Verb(line));
                }
                catch (IOException x) {
                        System.err.format("IOException: %s%n", x);
                }
        }
        public static Loader GetSingleton() {
                if(mInstance == null)
                        mInstance = new Loader();
                return mInstance;
        }
        static Loader mInstance = null;
}

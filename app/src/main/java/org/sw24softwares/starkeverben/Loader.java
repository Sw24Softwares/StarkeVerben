package org.sw24softwares.starkeverben;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Vector;

public class Loader {
        private LinkedHashMap<Integer, String> mLines = new LinkedHashMap<Integer, String>();
        public Vector<Verb> mVerbs = new Vector<Verb>();

        private Loader (BufferedReader bufferedReader, BufferedReader trans) {
                try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                                String parts[] = line.trim().split(" ");
                                Integer index = new Integer(Integer.parseInt(parts[parts.length -1]));
                                String verb = "";
                                for(int i = 0; i < parts.length -1; i++) // parts.length -1 to avoid taking the index
                                        verb += parts[i] + " ";
                                mLines.put(index, verb);
                        }
                        while ((line = trans.readLine()) != null) {
                                String parts[] = line.trim().split(" ");
                                Integer index = new Integer(Integer.parseInt(parts[0]));
                                mLines.replace(index,  mLines.get(index) + parts[1]);
                        }
                        Object[] entities = mLines.values().toArray();
                        for(Object o : entities)
                                mVerbs.addElement(new Verb((String)o));
                }
                catch (IOException x) {
                        System.err.format("IOException: %s%n", x);
                }
        }
        static public Loader CreateSingleton(BufferedReader buffer, BufferedReader trans) {
                if(mInstance == null)
                        mInstance = new Loader(buffer, trans);
                return mInstance;
        }

        public static Loader GetSingleton() {
                return mInstance;
        }
        static Loader mInstance = null;
}

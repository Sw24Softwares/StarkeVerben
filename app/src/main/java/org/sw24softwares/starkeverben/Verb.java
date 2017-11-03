package org.sw24softwares.starkeverben;

import java.util.Vector;

class Verb {
        public Vector<Vector<String>> mForms = new Vector<Vector<String>>();

        public Verb() {
        }
        public Verb(String sentence) {
                for(int i = 0; i < 6; i++) mForms.addElement(new Vector<String>());
                
                String parts[] = sentence.split(" ");
                for(int i = 0; i < parts.length && i < 6; i++) {
                        parts[i] = parts[i].replace("_"," "); // Convert _ to spaces, those are from the standard
                        String possibility [] = parts[i].split("/");
                        for(int j = 0; j < possibility.length; j++)
                                mForms.get(i).addElement(possibility[j]);
                }
                for(int i = 0; i < mForms.get(0).size(); i++) {
                        if(mForms.get(0).get(i).charAt(mForms.get(0).get(i).length() -1) == '*'){
                                mForms.get(5).addElement("sein");
                                mForms.get(0).set(i, mForms.get(0).get(i).replace("*",""));
                        }
                        else
                                mForms.get(5).addElement("haben");
                }
        }
        public Verb (String infinitive, String preterite, String participle, String traduction, String thirdPerson, String auxiliary) {
                mForms.get(0).addElement(infinitive);
                mForms.get(1).addElement(preterite);
                mForms.get(2).addElement(participle);
                mForms.get(3).addElement(thirdPerson);
                mForms.get(4).addElement(traduction);
                mForms.get(5).addElement(auxiliary);
        }
        public Verb clone() {
                Verb v = new Verb();
                v.mForms = (Vector<Vector<String>>)mForms.clone();
                return v;
        }
}

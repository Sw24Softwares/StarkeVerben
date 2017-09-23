package org.allemand_euro.verbesforts;

import java.util.Vector;
import java.util.Random;

import android.content.res.Resources;

class Question {
        public Verb mVerb;
        public int mFormType;
        public String mGivenForm;

        public Question(Verb verb, int formType, String givenForm) {
                mVerb = verb;
                mFormType = formType;
                mGivenForm = givenForm;
        }
        public static String standardize(String s) {
                String st = s.replace("ß","ss");
                return st;
        }
        public static Boolean Answer(Vector<String> answers, String s) {
                for(int i = 0; i < answers.size(); i++)
                        if(standardize(answers.get(i)).equalsIgnoreCase(standardize(s)))
                                return true;
                return false;
        }
}
class Questions {
        public Vector<Verb> mListOfVerbs;
        public Vector<Verb> mListOfUnusedVerbs;

        public Questions(Vector<Verb> verbs) {
                mListOfVerbs = verbs;
                mListOfUnusedVerbs = verbs;
        }
        public Question AskQuestion(int i) {
                Random rand = new Random();
                if(i < 0 || i > mListOfUnusedVerbs.size())
                        i = rand.nextInt(mListOfUnusedVerbs.size());
                int formType = rand.nextInt(5);

                int randomPick = rand.nextInt(mListOfUnusedVerbs.get(i).mForms.get(formType).size());
                String givenForm = mListOfUnusedVerbs.get(i).mForms.get(formType).get(randomPick);

                return new Question(mListOfUnusedVerbs.remove(i), formType, givenForm);
        }
        public static String FormToWord(int i) {
                Resources res = Settings.GetSingleton().GetResources();
                if(i == 0) return res.getString(R.string.infinitive);
                else if(i == 1) return res.getString(R.string.preterite);
                else if(i == 2) return res.getString(R.string.participe);
                else if(i == 3) return res.getString(R.string.third_person);
                else if(i == 4) return res.getString(R.string.traduction);
                else return res.getString(R.string.auxiliary);
        }
        public static String BoolToAux(Boolean b) {
                if(b)
                        return "sein";
                return "haben";
        }

        static private Questions mInstance = null;
        static public Questions CreateSingleton(Vector<Verb> verbs) {
                if(mInstance == null)
                        mInstance = new Questions(verbs);
                return mInstance;
        }
        static public Questions GetSingleton() {
                return mInstance;
        }
}

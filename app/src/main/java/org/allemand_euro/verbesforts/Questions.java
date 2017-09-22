package org.allemand_euro.verbesforts;

import java.util.Vector;
import java.util.Random;

class Question {
        public Verb mVerb;
        public int mFormType;
        public String mGivenForm;

        public Question(Verb verb, int formType, String givenForm) {
                mVerb = verb;
                mFormType = formType;
                mGivenForm = givenForm;
        }
        public Boolean Answer(String s, int form) {
                Vector<String> answers = mVerb.mForms.get(form);
                for(int i = 0; i < answers.size(); i++)
                        if(answers.get(i).equalsIgnoreCase(s))
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
                if(i == 0) return "Infinitif";
                else if(i == 1) return "Prétérit";
                else if(i == 2) return "Participe P.";
                else if(i == 3) return "3e Personne";
                else if(i == 4) return "Traduction";
                else return "Auxiliaire";
        }
        public static String BoolToAux(Boolean b) {
                if(b)
                        return "ist";
                return "hat";
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

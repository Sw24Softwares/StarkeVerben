package org.allemand_euro.verbesforts;

import java.util.Vector;
import java.util.Random;

class Question {
        public Verb mVerb;
        public int mFormType;
        public int mWantedType;
        public String mGivenForm;
        public Vector<String> mAnswers;

        public Question(Verb verb, int formType, int wantedType, String givenForm, Vector<String> answers) {
                mVerb = verb;
                mFormType = formType;
                mWantedType = wantedType;
                mGivenForm = givenForm;
                mAnswers = answers;
        }
        public Boolean Answer(String s) {
                Vector<String> answers = mAnswers;
                for(int i = 0; i < answers.size(); i++)
                        if(answers.get(i).equalsIgnoreCase(s))
                                return true;
                return false;
        }
}
class Questions {
        public Vector<Verb> mListOfVerbs;
        
        public Questions(Vector<Verb> verbs) {
                mListOfVerbs = verbs;
        }
        public Question AskQuestion(Integer i) {
                Random rand = new Random();
                if(i < 0 || i > mListOfVerbs.size())
                        i = rand.nextInt(mListOfVerbs.size());
                int formType = rand.nextInt(5);
                int wantedType = formType; // Just give same value to force loop to run
                while(wantedType == formType)
                        wantedType = rand.nextInt(6);
                
                int randomPick = rand.nextInt(mListOfVerbs.get(i).mForms.get(formType).size());
                String givenForm = mListOfVerbs.get(i).mForms.get(formType).get(randomPick);
                Vector<String> answers = mListOfVerbs.get(i).mForms.get(wantedType);
                
                return new Question(mListOfVerbs.get(i), formType, wantedType, givenForm, answers);
        }
        public static String FormToWord(int i) {
                if(i == 0) return "infinitif";
                else if(i == 1) return "prétérit";
                else if(i == 2) return "participe";
                else if(i == 3) return "troisième personne";
                else if(i == 4) return "traduction";
                else return "auxiliaire";
        }

        private Questions mInstance = null;
        public Questions CreateSingleton(Vector<Verb> verbs) {
                if(mInstance == null)
                        mInstance = new Questions(verbs);
                return mInstance;
        }
        public Questions GetSingleton() {
                return mInstance;
        }
}

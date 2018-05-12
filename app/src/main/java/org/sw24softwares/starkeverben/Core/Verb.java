package org.sw24softwares.starkeverben;

import java.util.Vector;

class Verb {
    // Index of the verb
    // Used for the translation
    protected int mIndex = -1;
    // Parts forming verbs
    // Vector are used in order to provide different possibilities
    protected Vector<String> mInfinitives  = new Vector<String>();
    protected Vector<String> mPreterites   = new Vector<String>();
    protected Vector<String> mParticiples  = new Vector<String>();
    protected Vector<String> mThirdPersons = new Vector<String>();
    // Auxiliary of the verb
    // true if sein
    protected Boolean mAuxiliary = false;

    // Default constructor
    public Verb() {
    }
    // Per member constructor
    public Verb(int index, Vector<String> infinitives, Vector<String> preterites,
        Vector<String> participles, Vector<String> thirdPersons, Boolean auxiliary) {
        mIndex        = index;
        mInfinitives  = infinitives;
        mPreterites   = preterites;
        mParticiples  = participles;
        mThirdPersons = thirdPersons;
        mAuxiliary    = auxiliary;
    }
    // Grouped constructor
    public Verb(int index, Vector<Vector<String>> parts, Boolean auxiliary) {
        this(index, parts.get(0), parts.get(1), parts.get(2), parts.get(3), auxiliary);
    }
    public Verb(Verb v) {
        this(v.mIndex, (Vector<Vector<String>>) v.getAllForms().clone(), v.mAuxiliary);
    }

    // Getters
    public int getIndex() {
        return mIndex;
    }
    public Vector<String> getInfinitives() {
        return mInfinitives;
    }
    public Vector<String> getPreterites() {
        return mPreterites;
    }
    public Vector<String> getParticiples() {
        return mParticiples;
    }
    public Vector<String> getThirdPersons() {
        return mThirdPersons;
    }
    public Boolean getAuxiliary() {
        return mAuxiliary;
    }
    public Vector<Vector<String>> getAllForms() {
        Vector<Vector<String>> allForms = new Vector<Vector<String>>();
        allForms.addElement(mInfinitives);
        allForms.addElement(mPreterites);
        allForms.addElement(mParticiples);
        allForms.addElement(mThirdPersons);
        return allForms;
    }
    public String getPrintedForm(int i, Boolean conjuguedAux) {
        if(i == 5) {
            if(conjuguedAux)
                return conjugueAux(mAuxiliary);
            return boolToAux(mAuxiliary);
        } else
            return GlobalData.getList(getAllForms().get(i), ", ");
    }

    public Verb clone() {
        Verb v = new Verb(mIndex, (Vector<Vector<String>>) getAllForms().clone(), mAuxiliary);
        return v;
    }
    public Vector<Integer> compare(Verb v) {
        Vector<Integer> res = new Vector<Integer>();
        int i               = 0;
        for(; i < getAllForms().size() && i < v.getAllForms().size(); i++)
            for(String s : getAllForms().get(i))
                if(v.getAllForms().get(i).contains(s))
                    res.add(i);
        if(mAuxiliary == v.mAuxiliary)
            res.add(i);
        return res;
    }

    static public String formToWord(int i) {
        return Settings.getSingleton().getFormString(i);
    }
    static public String boolToAux(Boolean b) {
        if(b)
            return "sein";
        return "haben";
    }
    static public String conjugueAux(Boolean b) {
        if(b)
            return "ist";
        return "hat";
    }
    static public String standardize(String s) {
        //                s = s.replace("ß","ss");
        return s;
    }
}

class VerbWithTranslation extends Verb {
    Vector<String> mTranslations;

    public VerbWithTranslation(Verb v, Vector<String> translations) {
        super(v);
        mTranslations = translations;
    }
    @Override
    public Vector<Vector<String>> getAllForms() {
        Vector<Vector<String>> v = super.getAllForms();
        v.add(mTranslations);
        return v;
    }
    @Override
    public String getPrintedForm(int i, Boolean conjuguedAux) {
        if(i == 5) {
            if(conjuguedAux)
                return conjugueAux(mAuxiliary);
            return boolToAux(mAuxiliary);
        } else
            return GlobalData.getList(getAllForms().get(i), ", ");
    }
    @Override
    public Vector<Integer> compare(Verb v) {
        Vector<Integer> res = new Vector<Integer>();
        int i               = 0;
        for(; i < getAllForms().size() && i < v.getAllForms().size(); i++)
            for(String s : getAllForms().get(i))
                if(v.getAllForms().get(i).contains(s))
                    res.add(i);
        if(mAuxiliary == v.mAuxiliary)
            res.add(i);
        return res;
    }
}

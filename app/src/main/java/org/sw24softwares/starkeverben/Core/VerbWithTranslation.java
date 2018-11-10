package org.sw24softwares.starkeverben.Core;

import org.sw24softwares.starkeverben.GlobalData;

import java.util.Vector;

public class VerbWithTranslation extends Verb {
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
        if (i == 5) {
            if (conjuguedAux)
                return conjugueAux(mAuxiliary);
            return boolToAux(mAuxiliary);
        } else
            return GlobalData.getList(getAllForms().get(i), ", ");
    }

    @Override
    public Vector<Integer> compare(Verb v) {
        Vector<Integer> res = new Vector<>();
        int i = 0;
        for (; i < getAllForms().size() && i < v.getAllForms().size(); i++)
            for (String s : getAllForms().get(i))
                if (v.getAllForms().get(i).contains(s))
                    res.add(i);
        if (mAuxiliary == v.mAuxiliary)
            res.add(i);
        return res;
    }
}

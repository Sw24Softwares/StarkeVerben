package org.sw24softwares.starkeverben;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.sw24softwares.starkeverben.Core.Settings;
import org.sw24softwares.starkeverben.Core.Verb;

import java.util.Locale;
import java.util.Random;

public class SingleLessonFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_single_lesson, container, false);

        final TextView infinitif = view.findViewById(R.id.infinitif_single_lesson);
        final TextView preterit = view.findViewById(R.id.preterit_single_lesson);
        final TextView participe = view.findViewById(R.id.participe_single_lesson);
        final TextView troisiemePersonne = view.findViewById(R.id.troisieme_personne_single_lesson);
        final TextView traduction = view.findViewById(R.id.traduction_single_lesson);
        final TextView aux = view.findViewById(R.id.auxiliary_single_lesson);

        Random rand = new Random();
        int verbNumber = rand.nextInt(Settings.getSingleton().getVerbs().size());
        Verb verb = Settings.getSingleton().getVerbs().get(verbNumber);

        String auxiliary;

        if (verb.getAuxiliary())
            auxiliary = "ist ";
        else
            auxiliary = "hat ";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Locale l = GlobalData.getTranslationLocale(sharedPref);
        Resources res = GlobalData.getLocalizedResources(getActivity(), l);
        String[] translationsArray = GlobalData.getTranslations(Settings.getSingleton().getVerbs(),
                verb, getActivity(), res);

        StringBuilder translations = new StringBuilder();
        for (int i = 0; i < translationsArray.length; i++) {
            translations.append(translationsArray[i]);
            if (i != translationsArray.length - 1)
                translations.append(", ");
        }

        infinitif.setText(verb.getInfinitives().get(0));
        preterit.setText(verb.getPreterites().get(0));
        participe.setText(verb.getParticiples().get(0));
        troisiemePersonne.setText(verb.getThirdPersons().get(0));
        traduction.setText(translations.toString());
        aux.setText(auxiliary);

        Button next = view.findViewById(R.id.next_single_lesson);
        next.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, new SingleLessonFragment());
            transaction.commit();
        });

        return view;
    }
}

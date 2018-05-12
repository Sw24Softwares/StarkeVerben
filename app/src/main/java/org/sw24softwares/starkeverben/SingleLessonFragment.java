package org.sw24softwares.starkeverben;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.util.Locale;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SingleLessonFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.activity_single_lesson, container, false);

                final TextView infinitif = (TextView) view.findViewById(R.id.infinitif_single_lesson);
                final TextView preterit = (TextView) view.findViewById(R.id.preterit_single_lesson);
                final TextView participe = (TextView) view.findViewById(R.id.participe_single_lesson);
                final TextView troisiemePersonne = (TextView) view.findViewById(R.id.troisieme_personne_single_lesson);
                final TextView traduction = (TextView) view.findViewById(R.id.traduction_single_lesson);
                final TextView aux = (TextView) view.findViewById(R.id.auxiliary_single_lesson);

                Random rand = new Random();
                int verbNumber = rand.nextInt(Settings.getSingleton().getVerbs().size());
                Verb verb = Settings.getSingleton().getVerbs().get(verbNumber);

                String auxiliary;
                
                if(verb.getAuxiliary())
                        auxiliary = "ist ";
                else
                        auxiliary = "hat ";

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Resources res = GlobalData.getLocalizedResources(getActivity(),new Locale(sharedPref.getString("prefLanguage", "")));
                String[] trans = res.getStringArray(res.getIdentifier(GlobalData.decompose(verb.getAllForms().get(0).get(0)),"array",getActivity().getPackageName()));
                
                infinitif.setText(verb.getAllForms().get(0).get(0));
                preterit.setText(verb.getAllForms().get(1).get(0));
                participe.setText(verb.getAllForms().get(2).get(0));
                troisiemePersonne.setText(verb.getAllForms().get(3).get(0));
                traduction.setText(trans[0]);
                aux.setText(auxiliary);

                Button next = (Button) view.findViewById(R.id.next_single_lesson);
                next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.replace(R.id.main_container, new SingleLessonFragment());
                                        transaction.commit();
                                }
                        });
                
                return view;
        }
}

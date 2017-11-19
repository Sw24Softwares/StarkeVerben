package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleLessonActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_single_lesson);

                final TextView infinitif = (TextView) findViewById(R.id.infinitif_single_lesson);
                final TextView preterit = (TextView) findViewById(R.id.preterit_single_lesson);
                final TextView participe = (TextView) findViewById(R.id.participe_single_lesson);
                final TextView troisiemePersonne = (TextView) findViewById(R.id.troisieme_personne_single_lesson);
                final TextView traduction = (TextView) findViewById(R.id.traduction_single_lesson);
                final TextView aux = (TextView) findViewById(R.id.auxiliary_single_lesson);

                Random rand = new Random();
                int verbNumber = rand.nextInt(Settings.getSingleton().getVerbs().size());
                Verb verb = Settings.getSingleton().getVerbs().get(verbNumber);

                String auxiliary;
                
                if(verb.getAuxiliary())
                        auxiliary = "ist ";
                else
                        auxiliary = "hat ";
                
                infinitif.setText(verb.getAllForms().get(0).get(0));
                preterit.setText(verb.getAllForms().get(1).get(0));
                participe.setText(verb.getAllForms().get(2).get(0));
                troisiemePersonne.setText(verb.getAllForms().get(3).get(0));
                traduction.setText(Settings.getSingleton().getTranslations().get(verbNumber).getTranslations().get(0));
                aux.setText(auxiliary);

                Button next = (Button) findViewById(R.id.next_single_lesson);
                next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(SingleLessonActivity.this, SingleLessonActivity.class);
					finish();
                                        startActivity(intent);
					overridePendingTransition(0,0);
                                }
                        });
                Button stop = (Button) findViewById(R.id.stop_single_lesson);
                stop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(SingleLessonActivity.this, MainActivity.class);
                                        startActivity(intent);
                                }
                        });
        }
}

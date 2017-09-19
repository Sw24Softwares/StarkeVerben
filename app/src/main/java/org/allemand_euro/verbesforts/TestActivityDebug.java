package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

import static android.R.color.black;

public class TestActivityDebug extends AppCompatActivity {
        public Question mQuestion;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test1);

                final EditText infinitif = (EditText) findViewById(R.id.infinitif);
                final EditText preterit = (EditText) findViewById(R.id.preterit);
                final EditText participe = (EditText) findViewById(R.id.participe);
                final EditText troisiemePersonne = (EditText) findViewById(R.id.troisieme_personne);
                final EditText traduction = (EditText) findViewById(R.id.traduction);
                final CheckBox aux = (CheckBox) findViewById(R.id.auxiliaire);


                infinitif.setHint(Questions.FormToWord(0));
                preterit.setHint(Questions.FormToWord(1));
                participe.setHint(Questions.FormToWord(2));
                troisiemePersonne.setHint(Questions.FormToWord(3));
                traduction.setHint(Questions.FormToWord(4));

                mQuestion = Questions.GetSingleton().AskQuestion(-1);
                if(mQuestion.mFormType == 0) {
                        infinitif.setText(mQuestion.mGivenForm);
                        infinitif.setEnabled(false);
                        infinitif.setTextColor(getResources().getColor(black));
                }
                if(mQuestion.mFormType == 1) {
                        preterit.setText(mQuestion.mGivenForm);
                        preterit.setEnabled(false);
                        preterit.setTextColor(getResources().getColor(black));
                }
                if(mQuestion.mFormType == 2) {
                        participe.setText(mQuestion.mGivenForm);
                        participe.setEnabled(false);
                        participe.setTextColor(getResources().getColor(black));
                }
                if(mQuestion.mFormType == 3) {
                        troisiemePersonne.setText(mQuestion.mGivenForm);
                        troisiemePersonne.setEnabled(false);
                        troisiemePersonne.setTextColor(getResources().getColor(black));
                }
                if(mQuestion.mFormType == 4) {
                        traduction.setText(mQuestion.mGivenForm);
                        traduction.setEnabled(false);
                        traduction.setTextColor(getResources().getColor(black));
                }

                Button button = (Button) findViewById(R.id.verify);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Boolean right = true;
                                        String auxiliaire;

                                        if (aux.isChecked())
                                                auxiliaire = "sein";
                                        else
                                                auxiliaire = "haben";

                                        if(!mQuestion.Answer(infinitif.getText().toString(),0))
                                                right = false;
                                        if(!mQuestion.Answer(preterit.getText().toString(),1))
                                                right = false;
                                        if(!mQuestion.Answer(participe.getText().toString(),2))
                                                right = false;
                                        if(!mQuestion.Answer(troisiemePersonne.getText().toString(),3))
                                                right = false;
                                        if(!mQuestion.Answer(traduction.getText().toString(),4))
                                                right = false;
                                        if(!mQuestion.Answer(auxiliaire,5))
                                                right = false;

                                        if(right) {
                                                Intent intent = new Intent(TestActivityDebug.this, ResultRightActivity.class);
                                                startActivity(intent);
                                        }
                                        else {
                                                Intent intent = new Intent(TestActivityDebug.this, ResultWrongActivity.class);
                                                //                                                intent.putExtra("answer", mQuestion.mAnswers.toArray(new String[0]));
                                                startActivity(intent);
                                        }

                                }
                        });

        }
}

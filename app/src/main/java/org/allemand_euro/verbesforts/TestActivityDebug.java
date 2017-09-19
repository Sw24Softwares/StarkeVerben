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

        protected void fillEditText(EditText editText, String text) {
                editText.setText(text);
                editText.setEnabled(false);
                editText.setTextColor(getResources().getColor(black));
        }
        
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
                if(mQuestion.mFormType == 0) fillEditText( infinitif,         mQuestion.mGivenForm );
                if(mQuestion.mFormType == 1) fillEditText( preterit,          mQuestion.mGivenForm );
                if(mQuestion.mFormType == 2) fillEditText( participe,         mQuestion.mGivenForm );
                if(mQuestion.mFormType == 3) fillEditText( troisiemePersonne, mQuestion.mGivenForm );
                if(mQuestion.mFormType == 4) fillEditText( traduction,        mQuestion.mGivenForm );

                Button button = (Button) findViewById(R.id.verify);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Boolean right[] = {true,true,true,true,true};
                                        String auxiliaire;

                                        if(!mQuestion.Answer(infinitif.getText().toString(),0))
                                                right[0] = false;
                                        if(!mQuestion.Answer(preterit.getText().toString(),1))
                                                right[1] = false;
                                        if(!mQuestion.Answer(participe.getText().toString(),2))
                                                right[2] = false;
                                        if(!mQuestion.Answer(troisiemePersonne.getText().toString(),3))
                                                right[3] = false;
                                        if(!mQuestion.Answer(traduction.getText().toString(),4))
                                                right[4] = false;
                                        if(!mQuestion.Answer(Questions.BoolToAux(aux.isChecked()),5))
                                                right[5] = false;

                                        Intent intent = new Intent(TestActivityDebug.this, ResultRightActivity.class);
                                        startActivity(intent);
                                        intent.putExtra("right", right);
                                }
                        });

        }
}

package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.inputmethod.InputMethodManager;

import java.util.Date;

import static android.R.color.black;

public class TestActivity extends AppCompatActivity {
        public Question mQuestion;

        protected void fillEditText(EditText editText, String text) {
                editText.setText(text);
                editText.setEnabled(false);
                editText.setTextColor(getResources().getColor(black));
        }

	protected boolean testDuration(long start) {
		long time = (System.currentTimeMillis() - start) / 1000;
		if (time < 20)
			return true;
		else
			return false;
	}
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test);
		
		final long start = System.currentTimeMillis();

		Questions.GetSingleton().mListOfUnusedVerbs = Questions.GetSingleton().mListOfVerbs;

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

                final int total = getIntent().getExtras().getInt("total");
                final int marks[] = getIntent().getExtras().getIntArray("marks");

                
                final RelativeLayout layout = (RelativeLayout) findViewById(R.id.test_layout);
                layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                                if(getCurrentFocus()!=null) {
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    layout.requestFocus();
                                }
                        }
                });

                Button button = (Button) findViewById(R.id.verify);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
					
					String time = getIntent().getExtras().getString("dialog");
					if(time == null)
						time = "false";

					if(!testDuration(start) && time.equals("false"))
						intent.putExtra("dialog", "false");
					else
						intent.putExtra("dialog", "true");
					
					String givenAnswers[] = new String[6];
                                        String auxiliaire;

                                        givenAnswers[0] = infinitif.getText().toString();
                                        givenAnswers[1] = preterit.getText().toString();
                                        givenAnswers[2] = participe.getText().toString();
                                        givenAnswers[3] = troisiemePersonne.getText().toString();
                                        givenAnswers[4] = traduction.getText().toString();
                                        givenAnswers[5] = Questions.BoolToAux(aux.isChecked());

                                        intent.putExtra("givenAnswers", givenAnswers);

                                        for (int i = 0; i < 6; i++)
					intent.putExtra(Questions.FormToWord(i), mQuestion.mVerb.mForms.get(i).toArray(new String[0]));
                                        intent.putExtra("givenFormType", mQuestion.mFormType);
                                        intent.putExtra("total", total);
                                        intent.putExtra("marks", marks);
                                        startActivity(intent);
                                }
                        });

        }
}

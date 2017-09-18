package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

public class TestActivityDebug extends AppCompatActivity {
        public Question mQuestion;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test1);

                ((EditText)findViewById(R.id.infinitif)).setHint(Questions.FormToWord(0));
                ((EditText)findViewById(R.id.preterit)).setHint(Questions.FormToWord(1));
                ((EditText)findViewById(R.id.participe)).setHint(Questions.FormToWord(2));
                ((EditText)findViewById(R.id.troisieme_personne)).setHint(Questions.FormToWord(3));
                ((EditText)findViewById(R.id.traduction)).setHint(Questions.FormToWord(4));
                
                mQuestion = Questions.GetSingleton().AskQuestion(-1);
                if(mQuestion.mFormType == 0) {
                        ((EditText)findViewById(R.id.infinitif)).setText(mQuestion.mGivenForm);
                        ((EditText)findViewById(R.id.infinitif)).setEnabled(false);
                }
                if(mQuestion.mFormType == 1) {
                        ((EditText)findViewById(R.id.preterit)).setText(mQuestion.mGivenForm);
                        ((EditText)findViewById(R.id.preterit)).setEnabled(false);
                }
                if(mQuestion.mFormType == 2) {
                        ((EditText)findViewById(R.id.participe)).setText(mQuestion.mGivenForm);
                        ((EditText)findViewById(R.id.participe)).setEnabled(false);
                }
                if(mQuestion.mFormType == 3) {
                        ((EditText)findViewById(R.id.troisieme_personne)).setText(mQuestion.mGivenForm);
                        ((EditText)findViewById(R.id.troisieme_personne)).setEnabled(false);
                }
                if(mQuestion.mFormType == 4) {
                        ((EditText)findViewById(R.id.traduction)).setText(mQuestion.mGivenForm);
                        ((EditText)findViewById(R.id.traduction)).setEnabled(false);
                }


                Button button = (Button) findViewById(R.id.verify);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Boolean right = true;
                                        if(!mQuestion.Answer(((EditText)findViewById(R.id.infinitif)).getText().toString(),0))
                                                right = false;
                                        if(!mQuestion.Answer(((EditText)findViewById(R.id.preterit)).getText().toString(),1))
                                                right = false;                                       
                                        if(!mQuestion.Answer(((EditText)findViewById(R.id.participe)).getText().toString(),2))
                                                right = false;
                                        if(!mQuestion.Answer(((EditText)findViewById(R.id.troisieme_personne)).getText().toString(),3))
                                                right = false;
                                        if(!mQuestion.Answer(((EditText)findViewById(R.id.traduction)).getText().toString(),4))
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

package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

public class TestActivity extends AppCompatActivity {
        public Question mQuestion;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test);

                /*                mQuestion = Questions.GetSingleton().AskQuestion(-1);
                
                TextView text = (TextView) findViewById(R.id.form);
                text.setText(Questions.FormToWord(mQuestion.mFormType) + " : " + mQuestion.mGivenForm + '\n' +
                             Questions.FormToWord(mQuestion.mWantedType) + " : ");

                Button button = (Button) findViewById(R.id.verifier);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        EditText editor = (EditText)findViewById(R.id.reponse);
                                        
                                        String answer = editor.getText().toString();
                                        if(mQuestion.Answer(answer)) {
                                                Intent intent = new Intent(TestActivity.this, ResultRightActivity.class);
                                                startActivity(intent);
                                        }
                                        else {
                                                Intent intent = new Intent(TestActivity.this, ResultWrongActivity.class);
                                                intent.putExtra("answer", mQuestion.mAnswers.toArray(new String[0]));
                                                startActivity(intent);
                                        }
                                }
                        });
                */
        }
}

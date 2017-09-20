package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Vector;

import android.content.res.Resources;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.graphics.Color;

public class ResultRightActivity extends AppCompatActivity {
        protected void initTextView(TextView textView, String givenAnswer, Vector<String> answers) {
                Boolean right = false;
                for(int i = 0; i < answers.size(); i++) {
                        if(answers.get(i).equalsIgnoreCase(givenAnswer))
                                right = true;
                        textView.setText(answers.get(i));
                }
                if(right)
                        textView.setTextColor(Color.GREEN);
                if(!right)
                        textView.setTextColor(Color.RED);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_result);

                final TextView infinitif = (TextView) findViewById(R.id.infinitif_result);
                final TextView preterit = (TextView) findViewById(R.id.preterit_result);
                final TextView participe = (TextView) findViewById(R.id.participe_result);
                final TextView troisiemePersonne = (TextView) findViewById(R.id.troisieme_personne_result);
                final TextView traduction = (TextView) findViewById(R.id.traduction_result);
                final TextView aux = (TextView) findViewById(R.id.auxiliaire_result);                

                Vector<Vector<String>> answers = new Vector<Vector<String>>();
                for(int i = 0; i < 6; i++)
                        answers.addElement(new Vector(Arrays.asList(getIntent().getExtras().getStringArray(Questions.FormToWord(i)))));

                String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
                int givenFormType = getIntent().getExtras().getInt("givenFormType");

                initTextView(infinitif, givenAnswers[0], answers.get(0));
                initTextView(preterit, givenAnswers[1], answers.get(1));
                initTextView(participe, givenAnswers[2], answers.get(2));
                initTextView(troisiemePersonne, givenAnswers[3], answers.get(3));
                initTextView(traduction, givenAnswers[4], answers.get(4));
                initTextView(aux, givenAnswers[5], answers.get(5));
        }
}

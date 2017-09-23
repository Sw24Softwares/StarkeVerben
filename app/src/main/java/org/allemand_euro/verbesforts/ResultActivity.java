package org.allemand_euro.verbesforts;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.ContentValues;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.Vector;

import android.content.res.Resources;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.graphics.Color;

public class ResultActivity extends AppCompatActivity {
        int mMarks[] = null;
        DatabaseHelper mDatabaseHelper;
        
        protected void initTextView(TextView textView, String givenAnswer, Vector<String> answers, Boolean changeColor) {
                Boolean right = false;
                for(int i = 0; i < answers.size(); i++) {
                        if(answers.get(i).equalsIgnoreCase(givenAnswer))
                                right = true;
                        textView.setText(answers.get(i));
                }
                if(right && changeColor) {
                        textView.setTextColor(ContextCompat.getColor(this, R.color.good));
                        mMarks[mMarks.length-1]++;
                }
                if(!right && changeColor)
                        textView.setTextColor(ContextCompat.getColor(this, R.color.bad));
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

                mDatabaseHelper = new DatabaseHelper(this);
                
                Vector<Vector<String>> answers = new Vector<Vector<String>>();
                for(int i = 0; i < 6; i++)
                        answers.addElement(new Vector(Arrays.asList(getIntent().getExtras().getStringArray(Questions.FormToWord(i)))));

                String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
                int givenFormType = getIntent().getExtras().getInt("givenFormType");

                final int total = getIntent().getExtras().getInt("total");
                int marks[] = getIntent().getExtras().getIntArray("marks");
                mMarks = Arrays.copyOf(marks, marks.length +1);

                if(givenAnswers[5].equals("sein"))	givenAnswers[5] = "ist";
		else					givenAnswers[5] = "hat";
		if(answers.get(5).contains("sein"))	answers.set(5, new Vector(Arrays.asList("ist")));
		else					answers.set(5, new Vector(Arrays.asList("hat")));
		
		initTextView(infinitif, givenAnswers[0], answers.get(0), givenFormType != 0);
                initTextView(preterit, givenAnswers[1], answers.get(1), givenFormType != 1);
                initTextView(participe, givenAnswers[2], answers.get(2), givenFormType != 2);
                initTextView(troisiemePersonne, givenAnswers[3], answers.get(3), givenFormType != 3);
                initTextView(traduction, givenAnswers[4], answers.get(4), givenFormType != 4);
                initTextView(aux, givenAnswers[5], answers.get(5), givenFormType != 5);

                Button arreter = (Button) findViewById(R.id.arreter);
                Button continuer = (Button) findViewById(R.id.continuer);
                
                arreter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(ResultActivity.this, MainActivity.class);

                                        String marks = new String();
                                        for(int i = 0; i < mMarks.length; i++)
                                                marks += String.valueOf(mMarks[i]) + ' ';
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(DatabaseHelper.COLUMN_1, new SimpleDateFormat("dd/MM/yyyy à HH:mm").format(new Date()));
                                        contentValues.put(DatabaseHelper.COLUMN_2, marks);
                                        mDatabaseHelper.addData(contentValues);
                                        startActivity(intent);
                                }
                        });

                continuer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(ResultActivity.this, TestActivity.class);
                                        intent.putExtra("total", total + 1);
                                        intent.putExtra("marks", mMarks);
                                        startActivity(intent);
                                }
                        });
        }
}

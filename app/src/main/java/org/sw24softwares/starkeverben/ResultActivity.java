package org.sw24softwares.starkeverben;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.ContentValues;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Vector;


public class ResultActivity extends AppCompatActivity {
        int mMarks[] = null;
        DatabaseHelper mDatabaseHelper;

        protected void saveMarkDialog(final int theMarks[]) {
                AlertDialog alertDialog = new AlertDialog.Builder(ResultActivity.this).create();

		alertDialog.setTitle(getString(R.string.save));
                alertDialog.setMessage(getString(R.string.save_message));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                
		new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ResultActivity.this, MainActivity.class);

                            String marks = new String();
                            int totalPercent = 0;

                            for(int i = 0; i < theMarks.length; i++) {
                                marks += String.valueOf(theMarks[i]) + ' ';
                                totalPercent += theMarks[i];
                            }

                            totalPercent = Math.round(totalPercent * 100 / (theMarks.length * 5));
                            int onTwenty = Math.round(totalPercent * 2 / 10);

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatabaseHelper.COLUMN_1, new SimpleDateFormat("dd/MM/yyyy '" + getString(R.string.at) + "' HH:mm : '" + String.valueOf(totalPercent) + "% - (" + onTwenty + "/20)'").format(new Date()));
                            contentValues.put(DatabaseHelper.COLUMN_2, marks);
                            mDatabaseHelper.addData(contentValues);
			    
			    startActivity(intent);
                        }
                    });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                alertDialog.show();
        }

        protected void saveMark(final int theMarks[]) {
		Intent intent = new Intent(ResultActivity.this, MainActivity.class);

		String marks = new String();
		int totalPercent = 0;

		for(int i = 0; i < theMarks.length; i++) {
                        marks += String.valueOf(theMarks[i]) + ' ';
                        totalPercent += theMarks[i];
		}

		totalPercent = Math.round(totalPercent * 100 / (theMarks.length * 5));
		int onTwenty = Math.round(totalPercent * 2 / 10);

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.COLUMN_1, new SimpleDateFormat("dd/MM/yyyy '" + getString(R.string.at) + "' HH:mm : '" + String.valueOf(totalPercent) + "% - (" + onTwenty + "/20)'").format(new Date()));
		contentValues.put(DatabaseHelper.COLUMN_2, marks);
		mDatabaseHelper.addData(contentValues);

		startActivity(intent);
        }

        protected void initTextView(TextView textView, String givenAnswer, Vector<String> answers, Boolean changeColor) {
                Boolean right = false;
                for(int i = 0; i < answers.size(); i++)
                        answers.set(i, Verb.standardize(answers.get(i)));
                if(answers.contains(Verb.standardize(givenAnswer)))
                        right = true;
                textView.setText(answers.get(0));
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
                        answers.addElement(new Vector(Arrays.asList(getIntent().getExtras().getStringArray(Verb.formToWord(i)))));

                String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
                int givenFormType = getIntent().getExtras().getInt("givenFormType");
	
		final Boolean dialog = getIntent().getExtras().getBoolean("dialog");		//String to see if the user has took more of 20 seconds to complete each test (outputs the dialog if not)

                final int total = getIntent().getExtras().getInt("total");
                int marks[] = getIntent().getExtras().getIntArray("marks");
                mMarks = Arrays.copyOf(marks, marks.length +1);

                if(givenAnswers[5].equals("sein"))	givenAnswers[5] = "ist";
                else                                    givenAnswers[5] = "hat";
                if(answers.get(5).contains("sein"))	answers.set(5, new Vector(Arrays.asList("ist")));
                else                                    answers.set(5, new Vector(Arrays.asList("hat")));

                initTextView(infinitif, givenAnswers[0], (Vector<String>)answers.get(0).clone(), givenFormType != 0);
                initTextView(preterit, givenAnswers[1], (Vector<String>)answers.get(1).clone(), givenFormType != 1);
                initTextView(participe, givenAnswers[2], (Vector<String>)answers.get(2).clone(), givenFormType != 2);
                initTextView(troisiemePersonne, givenAnswers[3], (Vector<String>)answers.get(3).clone(), givenFormType != 3);
                initTextView(traduction, givenAnswers[4], (Vector<String>)answers.get(4).clone(), givenFormType != 4);
                initTextView(aux, givenAnswers[5], (Vector<String>)answers.get(5).clone(), givenFormType != 5);

                Button arreter = (Button) findViewById(R.id.arreter);
                Button continuer = (Button) findViewById(R.id.continuer);

                arreter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
					if(dialog)
						saveMarkDialog(mMarks);
					else
						saveMark(mMarks);
                                }
                });

                continuer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(ResultActivity.this, TestActivity.class);
                                        intent.putExtra("total", total + 1);
                                        intent.putExtra("marks", mMarks);
					intent.putExtra("dialog", dialog);
                                        startActivity(intent);
                                }
                        });
        }
}

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

        protected void saveMarkDialog() {
                AlertDialog alertDialog = new AlertDialog.Builder(ResultActivity.this).create();
		alertDialog.setTitle(getString(R.string.save));
                alertDialog.setMessage(getString(R.string.save_message));

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
		new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                                saveMark();
                        }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                                ResultActivity.this.finish();
                                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                                startActivity(intent);
                        }
                });
                alertDialog.show();
        }

        protected void saveMark() {
		String marks = new String();
		int totalPercent = 0;

		for(int i = 0; i < mMarks.length; i++) {
                        marks += String.valueOf(mMarks[i]) + ' ';
                        totalPercent += mMarks[i];
		}

		totalPercent = Math.round(totalPercent * 100 / (mMarks.length * 5));
		int onTwenty = Math.round(totalPercent * 2 / 10);

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.COLUMN_1, new SimpleDateFormat("dd/MM/yyyy '" + getString(R.string.at) + "' HH:mm : '" + String.valueOf(totalPercent) + "% - (" + onTwenty + "/20)'").format(new Date()));
		contentValues.put(DatabaseHelper.COLUMN_2, marks);

                DatabaseHelper databaseHelper = new DatabaseHelper(this);
		databaseHelper.addData(contentValues);

                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
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

                final Vector<TextView> textViews = new Vector<TextView>();
                textViews.addElement((TextView) findViewById(R.id.infinitive_result));
                textViews.addElement((TextView) findViewById(R.id.preterit_result));
                textViews.addElement((TextView) findViewById(R.id.participe_result));
                textViews.addElement((TextView) findViewById(R.id.third_person_result));
                textViews.addElement((TextView) findViewById(R.id.translation_result));
                textViews.addElement((TextView) findViewById(R.id.auxiliary_result));

                Vector<Vector<String>> answers = new Vector<Vector<String>>();
                for(int i = 0; i < 6; i++)
                        answers.addElement(new Vector(Arrays.asList(getIntent().getExtras().getStringArray(Verb.formToWord(i)))));

                String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
                int givenFormType = getIntent().getExtras().getInt("givenFormType");
	
                final int marks[] = getIntent().getExtras().getIntArray("marks");
                mMarks = Arrays.copyOf(marks, marks.length +1);

                if(givenAnswers[5].equals("sein"))	givenAnswers[5] = "ist";
                else                                    givenAnswers[5] = "hat";
                if(answers.get(5).contains("sein"))	answers.set(5, new Vector(Arrays.asList("ist")));
                else                                    answers.set(5, new Vector(Arrays.asList("hat")));

                // Init the TextViews
                for(int i = 0; i < textViews.size(); i++)
                        initTextView(textViews.get(i), givenAnswers[i], (Vector<String>)answers.get(i).clone(), givenFormType != i);


                Button finish = (Button) findViewById(R.id.finish);
                finish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        // Whether open the dialog or not
					if(getIntent().getExtras().getBoolean("dialog"))
						saveMarkDialog();
					else
						saveMark();
                                }
                });
                
                Button go_on = (Button) findViewById(R.id.go_on);
                go_on.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        ResultActivity.this.finish();
                                        Intent intent = new Intent(ResultActivity.this, TestActivity.class);
                                        intent.putExtra("total", getIntent().getExtras().getInt("total") + 1);
                                        intent.putExtra("marks", mMarks);
					intent.putExtra("dialog", getIntent().getExtras().getBoolean("dialog"));
                                        startActivity(intent);
                                }
                        });
        }
}

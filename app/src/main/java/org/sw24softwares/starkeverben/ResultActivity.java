package org.sw24softwares.starkeverben;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.ContentValues;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Vector;
import java.util.Locale;

import android.preference.PreferenceManager;

public class ResultActivity extends AppCompatActivity {
    protected Verb mVerb;
    protected VerbWithTranslation mVWT;
    protected int mMarks[] = null;

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

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_1,
                          new SimpleDateFormat("dd/MM/yyyy HH:mm " + String.valueOf(totalPercent))
                              .format(new Date()));
        contentValues.put(DatabaseHelper.COLUMN_2, marks);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.addData(contentValues);

        ResultActivity.this.finish();
    }

    protected void initTextViews(Vector<TextView> textViews, Verb givenVerb, Verb verb, int form) {
        Vector<Integer> compareRes = verb.compare(givenVerb);

        for(int i = 0; i < textViews.size(); i++) {
            Boolean changeColor = form != i;
            if(compareRes.contains(new Integer(i)) && changeColor) {
                textViews.get(i).setTextColor(ContextCompat.getColor(this, R.color.good));
                mMarks[mMarks.length - 1]++;
            } else if(changeColor)
                textViews.get(i).setTextColor(ContextCompat.getColor(this, R.color.bad));
            textViews.get(i).setText(verb.getPrintedForm(i, true));
        }
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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Resources res = GlobalData.getLocalizedResources(
            this, new Locale(sharedPref.getString("prefLanguage", "")));

        int givenFormType = getIntent().getExtras().getInt("givenFormType");

        // Get answers
        String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
        Vector<Vector<String>> vec = new Vector();
        for(int i = 0; i < 4; i++)
            vec.add(GlobalData.oneElementVector(givenAnswers[i]));
        Verb givenVerb = new Verb(-1, vec, givenAnswers[5].equals("sein"));
        Verb givenVWT =
            new VerbWithTranslation(givenVerb, GlobalData.oneElementVector(givenAnswers[4]));

        // Construct possible verbs
        int verbIndex = getIntent().getExtras().getInt("verbIndex");
        mVerb = Settings.getSingleton().getVerbs().get(verbIndex);
        mVWT = GlobalData.androidVWTCreate(mVerb, this, res);
        Vector<VerbWithTranslation> possibleVerbs = new Vector<VerbWithTranslation>();
        for(int i = 0; i < Settings.getSingleton().getVerbs().size(); i++) {
            Verb v = Settings.getSingleton().getVerbs().get(i);
            VerbWithTranslation vwt = GlobalData.androidVWTCreate(v, this, res);
            if(vwt.getAllForms().get(givenFormType).equals(mVWT.getAllForms().get(givenFormType)))
                possibleVerbs.add(vwt);
        }
        int cmin = 0;
        for(VerbWithTranslation pv : possibleVerbs) {
            int s = pv.compare(givenVWT).size();
            if(s > cmin) {
                mVWT = pv;
                cmin = s;
            }
        }

        // Get marks array and increase its length
        final int marks[] = getIntent().getExtras().getIntArray("marks");
        mMarks = Arrays.copyOf(marks, marks.length + 1);

        // Init the TextViews
        initTextViews(textViews, givenVWT, mVWT, givenFormType);

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

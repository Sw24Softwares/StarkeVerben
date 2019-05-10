package org.sw24softwares.starkeverben;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.sw24softwares.starkeverben.Core.Settings;
import org.sw24softwares.starkeverben.Core.Verb;
import org.sw24softwares.starkeverben.Core.VerbWithTranslation;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class ResultActivity extends AppCompatActivity {
    protected Verb mVerb;
    protected VerbWithTranslation mVWT;
    protected int mMarks[] = null;

    protected void saveMarkDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ResultActivity.this).create();
        alertDialog.setTitle(getString(R.string.save_question));
        alertDialog.setMessage(getString(R.string.save_message));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                (dialog, which) -> saveMark());

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                (dialog, which) -> ResultActivity.this.finish());
        alertDialog.show();
    }

    protected void saveMark() {
        StringBuilder marks = new StringBuilder();
        int totalPercent = 0;

        for (int mMark : mMarks) {
            marks.append(String.valueOf(mMark)).append(' ');
            totalPercent += mMark;
        }

        totalPercent = Math.round(totalPercent * 100 / (mMarks.length * 5));

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_1,
                new SimpleDateFormat("dd/MM/yyyy HH:mm " + String.valueOf(totalPercent))
                        .format(new Date()));
        contentValues.put(DatabaseHelper.COLUMN_2, marks.toString());

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.addData(contentValues);

        ResultActivity.this.finish();
    }

    protected void orderViews(Vector<View> views, Vector<Integer> order) {
        for (int i = 0; i < views.size(); i++) {
            RelativeLayout.LayoutParams p
                    = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (order.get(i) != 0)
                p.addRule(RelativeLayout.BELOW, views.get(order.indexOf(order.get(i) - 1)).getId());
            p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            views.get(i).setLayoutParams(p);
        }
    }

    protected void initTextViews(Vector<TextView> textViews, Verb givenVerb, Verb verb, int form) {
        Vector<Integer> compareRes = verb.compare(givenVerb);

        for (int i = 0; i < textViews.size(); i++) {
            Boolean changeColor = form != i;
            if (compareRes.contains(i) && changeColor) {
                textViews.get(i).setTextColor(ContextCompat.getColor(this, R.color.good));
                mMarks[mMarks.length - 1]++;
            } else if (changeColor)
                textViews.get(i).setTextColor(ContextCompat.getColor(this, R.color.bad));
            textViews.get(i).setText(verb.getPrintedForm(i, true));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Locale l = GlobalData.getTranslationLocale(sharedPref);
        Resources res = GlobalData.getLocalizedResources(this, l);
        String formOrderPref = sharedPref.getString("formOrder", null);

        final Vector<Integer> formsOrder = new Vector<>();
        if (formOrderPref == null)
            for (int i = 0; i < 6; i++)
                formsOrder.addElement(i);
        else
            for (char c : formOrderPref.toCharArray())
                formsOrder.addElement(Character.getNumericValue(c));

        // Search for Auxiliary and delete it
        formsOrder.remove(5);

        final Vector<Integer> formsOrderReverse = new Vector<>();
        formsOrderReverse.setSize(6);
        for (int i = 0; i < formsOrder.size(); i++)
            formsOrderReverse.set(formsOrder.get(i), i);
	for (int i = 0; i < formsOrderReverse.size(); i++)
	    if(formsOrderReverse.get(i) == null)
		formsOrderReverse.remove(i);

        final Vector<TextView> textViews = new Vector<>();
        textViews.addElement((TextView) findViewById(R.id.infinitive_result));
        textViews.addElement((TextView) findViewById(R.id.preterit_result));
        textViews.addElement((TextView) findViewById(R.id.participle_result));
        textViews.addElement((TextView) findViewById(R.id.third_person_result));
        textViews.addElement((TextView) findViewById(R.id.translation_result));
        textViews.addElement((TextView) findViewById(R.id.auxiliary_result));

        final Vector<View> forms = new Vector<>();
        forms.addElement((View) findViewById(R.id.infinitive_result));
        forms.addElement((View) findViewById(R.id.preterit_result));
        forms.addElement((View) findViewById(R.id.aux_part_layout));
        forms.addElement((View) findViewById(R.id.third_person_result));
        forms.addElement((View) findViewById(R.id.translation_result));
        orderViews(forms, formsOrderReverse);

        Vector<Verb> verbs = Settings.getSingleton().getVerbs();
        int givenFormType = getIntent().getExtras().getInt("givenFormType");

        // Get answers
        String givenAnswers[] = getIntent().getExtras().getStringArray("givenAnswers");
        Vector<Vector<String>> vec = new Vector();
        for (int i = 0; i < 4; i++)
            vec.add(GlobalData.oneElementVector(givenAnswers[i]));
        Verb givenVerb = new Verb(-1, vec, givenAnswers[5].equals("sein"));
        Verb givenVWT =
                new VerbWithTranslation(givenVerb, GlobalData.oneElementVector(givenAnswers[4]));

        // Construct possible verbs
        int verbIndex = getIntent().getExtras().getInt("verbIndex");
        mVerb = verbs.get(verbIndex);
        mVWT = GlobalData.androidVWTCreate(verbs, mVerb, this, res);
        Vector<VerbWithTranslation> possibleVerbs = new Vector<>();
        for (Verb v : verbs) {
            VerbWithTranslation vwt = GlobalData.androidVWTCreate(verbs, v, this, res);
            if (vwt.getAllForms().get(givenFormType).equals(mVWT.getAllForms().get(givenFormType)))
                possibleVerbs.add(vwt);
        }
        int cmin = 0;
        for (VerbWithTranslation pv : possibleVerbs) {
            int s = pv.compare(givenVWT).size();
            Vector<String> pvForms = pv.getAllForms().get(givenFormType);
            String givenForm = givenVWT.getAllForms().get(givenFormType).get(0);
            if (s > cmin && pvForms.contains(givenForm)) {
                mVWT = pv;
                cmin = s;
            }
        }

        // Get marks array and increase its length
        final int marks[] = getIntent().getExtras().getIntArray("marks");
        mMarks = Arrays.copyOf(marks, marks.length + 1);

        // Init the TextViews
        initTextViews(textViews, givenVWT, mVWT, givenFormType);

        Button finish = findViewById(R.id.finish);
        finish.setOnClickListener(view -> {
            // Whether open the dialog or not
            if (getIntent().getExtras().getBoolean("dialog"))
                saveMarkDialog();
            else
                saveMark();
        });

        Button go_on = findViewById(R.id.go_on);
        go_on.setOnClickListener(view -> {
            ResultActivity.this.finish();
            Intent intent = new Intent(ResultActivity.this, TestActivity.class);
            intent.putExtra("total", getIntent().getExtras().getInt("total") + 1);
            intent.putExtra("marks", mMarks);
            intent.putExtra("dialog", getIntent().getExtras().getBoolean("dialog"));
            startActivity(intent);
        });
    }
}

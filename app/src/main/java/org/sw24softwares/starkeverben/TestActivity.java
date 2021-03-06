package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.sw24softwares.starkeverben.Core.Settings;
import org.sw24softwares.starkeverben.Core.Verb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import static android.R.color.black;

public class TestActivity extends AppCompatActivity {
    protected int mVerbIndex;
    protected int mFormType;
    protected Verb mVerb;
    protected String[] mTranslations;

    protected void fillEditText(EditText editText, String text) {
        editText.setText(text);
        editText.setEnabled(false);
        editText.setTextColor(getResources().getColor(black));
    }

    protected boolean testDuration(long start) {
        long time = (System.currentTimeMillis() - start) / 1000;
        return time < 20;
    }

    protected void orderViews(Vector<View> views, Vector<Integer> order) {
        for (int i = 0; i < views.size(); i++) {
            if (order.get(i) == 0)
                continue;
            int width = (int) (165 * Resources.getSystem().getDisplayMetrics().density);
            if (i == 5)
                width = ViewGroup.LayoutParams.WRAP_CONTENT;
            RelativeLayout.LayoutParams p
                    = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.addRule(RelativeLayout.BELOW, views.get(order.indexOf(order.get(i) - 1)).getId());
            if (i == 5)
                p.addRule(RelativeLayout.CENTER_HORIZONTAL);
            views.get(i).setLayoutParams(p);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Set<String> defaultGivenForms =
                new HashSet<>(Arrays.asList(getResources().getStringArray(R.array.forms_index)));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String formOrderPref = sharedPref.getString("formOrder", null);
        Set<String> givenForms = sharedPref.getStringSet("givenFormsInTest", defaultGivenForms);

        if (givenForms.isEmpty())
            givenForms = defaultGivenForms;

        final Vector<Integer> formsOrder = new Vector<>();
        if (formOrderPref == null)
            for (int i = 0; i < 6; i++)
                formsOrder.addElement(i);
        else
            for (char c : formOrderPref.toCharArray())
                formsOrder.addElement(Character.getNumericValue(c));

        final Vector<Integer> formsOrderReverse = new Vector<>();
        formsOrderReverse.setSize(6);
        for (int i = 0; i < formsOrder.size(); i++)
            formsOrderReverse.set(formsOrder.get(i), i);

        final Vector<EditText> editForms = new Vector<>();
        editForms.addElement((EditText) findViewById(R.id.infinitive));
        editForms.addElement((EditText) findViewById(R.id.preterite));
        editForms.addElement((EditText) findViewById(R.id.participe));
        editForms.addElement((EditText) findViewById(R.id.third_person));
        editForms.addElement((EditText) findViewById(R.id.traduction));
        final CheckBox aux = findViewById(R.id.auxiliary);

        final Vector<View> forms = new Vector<>();
        for (View v : editForms)
            forms.addElement(v);
        forms.addElement(aux);
        orderViews(forms, formsOrderReverse);

        Button button = findViewById(R.id.verify);
        RelativeLayout.LayoutParams p
                = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, forms.get(formsOrder.get(formsOrder.size() - 1)).getId());
        p.addRule(RelativeLayout.CENTER_HORIZONTAL);
        button.setLayoutParams(p);

        for (int i = 0; i < editForms.size(); i++)
            editForms.get(i).setHint(Verb.formToWord(i));

        // Select verb
        Vector<Verb> verbs = Settings.getSingleton().getVerbs();
        Random rand = new Random();
        mVerbIndex = rand.nextInt(verbs.size());
        mVerb = verbs.get(mVerbIndex);

        // Get translations
        Locale l = GlobalData.getTranslationLocale(sharedPref);
        Resources res = GlobalData.getLocalizedResources(this, l);
        mTranslations = GlobalData.getTranslations(verbs, mVerb, this, res);

        // Getting the given form, depends on the user preferences
        mFormType =
                Integer.parseInt(givenForms.toArray(new String[0])[rand.nextInt(givenForms.size())]);

        for (int i = 0; i < 4; i++) {
            if (mFormType == i) {
                int possibility = rand.nextInt(mVerb.getAllForms().get(mFormType).size());
                fillEditText(editForms.get(i),
                        mVerb.getAllForms().get(mFormType).get(possibility));
            }
        }
        if (mFormType == 4)
            fillEditText(editForms.get(4), mTranslations[0]);

        final int total = getIntent().getExtras().getInt("total");
        final int marks[] = getIntent().getExtras().getIntArray("marks");
        final long start = System.currentTimeMillis();

        final RelativeLayout layout = findViewById(R.id.test_layout);
        layout.setOnClickListener(v -> {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        0);
                layout.requestFocus();
            }
        });

        button.setOnClickListener(view -> {
            TestActivity.this.finish();
            Intent intent = new Intent(TestActivity.this, ResultActivity.class);

            // Whether a test has taken more than 20 seconds to be done
            Boolean time = getIntent().getExtras().getBoolean("dialog");
            intent.putExtra("dialog", testDuration(start) || time);

            String givenAnswers[] = new String[6];
            for (int i = 0; i < editForms.size(); i++)
                givenAnswers[i] = editForms.get(i).getText().toString();
            givenAnswers[5] = Verb.boolToAux(aux.isChecked());
            intent.putExtra("givenAnswers", givenAnswers);

            intent.putExtra("verbIndex", mVerbIndex);
            intent.putExtra("givenFormType", mFormType);
            intent.putExtra("total", total);
            intent.putExtra("marks", marks);
            startActivity(intent);
        });
    }
}

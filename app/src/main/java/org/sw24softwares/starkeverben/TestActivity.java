package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.inputmethod.InputMethodManager;

import java.util.Date;
import java.util.Random;
import java.util.Arrays;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.Locale;

import android.preference.PreferenceManager;

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
		if (time < 20)
			return true;
		else
			return false;
	}

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test);

                Set<String> defaultGivenForms = new HashSet<String>(Arrays.asList(getResources().getStringArray(R.array.forms_index)));

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                Set<String> formOrderPref = sharedPref.getStringSet("formOrder", null);
                Set<String> givenForms = sharedPref.getStringSet("givenFormsInTest", defaultGivenForms);

                if (givenForms.isEmpty()) givenForms = defaultGivenForms;

                final Vector<Integer> formsOrder = new Vector<Integer>();
                if(formOrderPref == null)
                        for(int i = 0; i < 5; i++)
                                formsOrder.addElement(i);
                else
                        for(int i = 0; i < formOrderPref.toArray().length; i++)
                                formsOrder.addElement(Integer.parseInt((String)formOrderPref.toArray()[i]));

                final Vector<Integer> formsOrderReverse = new Vector<Integer>();
                formsOrderReverse.setSize(5);
                for(int i = 0; i < formsOrder.size(); i++)
                        formsOrderReverse.set(formsOrder.get(i),i);

                final Vector<EditText> editForms = new Vector<EditText>();
                editForms.addElement((EditText) findViewById(R.id.e0));
                editForms.addElement((EditText) findViewById(R.id.e1));
                editForms.addElement((EditText) findViewById(R.id.e2));
                editForms.addElement((EditText) findViewById(R.id.e3));
                editForms.addElement((EditText) findViewById(R.id.e4));
                final CheckBox aux = (CheckBox) findViewById(R.id.auxiliary);

                for(int i = 0; i < editForms.size(); i++)
                        editForms.get(i).setHint(Verb.formToWord(formsOrder.get(i)));

                // Select verb
                Random rand = new Random();
                mVerbIndex = 96;
                //                mVerbIndex = rand.nextInt(Settings.getSingleton().getVerbs().size());
                mVerb = Settings.getSingleton().getVerbs().get(mVerbIndex);

                // Get translations
                Resources res = GlobalData.getLocalizedResources(this,new Locale(sharedPref.getString("prefLanguage", "")));
                mTranslations = res.getStringArray(res.getIdentifier(GlobalData.decompose(mVerb.getAllForms().get(0).get(0)),"array",getPackageName()));

                // Getting the given form, depends on the user preferences
                mFormType = Integer.parseInt(givenForms.toArray(new String[givenForms.size()])[rand.nextInt(givenForms.size())]);

                for(int i = 0; i < 4; i++) {
                        if(mFormType == i) {
                                int possibility = rand.nextInt(mVerb.getAllForms().get(mFormType).size());
                                fillEditText(editForms.get(formsOrderReverse.get(i)), mVerb.getAllForms().get(mFormType).get(possibility));
                        }
                }
                if(mFormType == 4)
                        fillEditText(editForms.get(formsOrderReverse.get(4)), mTranslations[0]);

                final int total = getIntent().getExtras().getInt("total");
                final int marks[] = getIntent().getExtras().getIntArray("marks");
                final long start = System.currentTimeMillis();

                final RelativeLayout layout = (RelativeLayout) findViewById(R.id.test_layout);
                layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                                if(getCurrentFocus() != null) {
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
                                        TestActivity.this.finish();
                                        Intent intent = new Intent(TestActivity.this, ResultActivity.class);

                                        // Whether a test has taken more than 20 seconds to be done
					Boolean time = getIntent().getExtras().getBoolean("dialog");
                                        intent.putExtra("dialog", testDuration(start) || time);

					String givenAnswers[] = new String[6];
                                        for(int i = 0; i < editForms.size(); i++)
                                                givenAnswers[formsOrder.get(i)] = editForms.get(i).getText().toString();
                                        givenAnswers[5] = Verb.boolToAux(aux.isChecked());
                                        intent.putExtra("givenAnswers", givenAnswers);

                                        intent.putExtra("verbIndex", mVerbIndex);
                                        intent.putExtra("givenFormType", mFormType);
                                        intent.putExtra("total", total);
                                        intent.putExtra("marks", marks);
                                        startActivity(intent);
                                }
                        });

        }
}

package org.sw24softwares.starkeverben;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
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

import static android.R.color.black;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TestActivity extends AppCompatActivity {
        protected int mGivenVerb;
        protected int mFormType;
        protected int mPossibility;
        protected Verb mVerb;
        
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
		
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                Set<String> formOrderPref = sharedPref.getStringSet("formOrder", null);

                final Vector<Integer> formsOrder = new Vector<Integer>();
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
                
                Random rand = new Random();
                mGivenVerb = rand.nextInt(Settings.getSingleton().getVerbs().size());
                mVerb = Settings.getSingleton().getVerbs().get(mGivenVerb);
                mFormType = rand.nextInt(5);
                
                for(int i = 0; i < 4; i++) {
                        if(mFormType == i) {
                                mPossibility = rand.nextInt(mVerb.getAllForms().get(mFormType).size());
                                fillEditText(editForms.get(formsOrderReverse.get(i)), mVerb.getAllForms().get(mFormType).get(mPossibility));
                        }
                }
                if(mFormType == 4) {
                        mPossibility = rand.nextInt(Settings.getSingleton().getTranslations().get(mGivenVerb).getTranslations().size());
                        fillEditText(editForms.get(formsOrderReverse.get(4)), Settings.getSingleton().getTranslations().get(mGivenVerb).getTranslations().get(mPossibility));
                }

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

                                        for (int i = 0; i < 4; i++)
                                                intent.putExtra(Verb.formToWord(i), mVerb.getAllForms().get(i).toArray(new String[0]));
                                        intent.putExtra(Verb.formToWord(4), Settings.getSingleton().getTranslations().get(mGivenVerb).getTranslations().toArray(new String[0]));
                                        intent.putExtra(Verb.formToWord(5), Arrays.asList(Verb.boolToAux(mVerb.getAuxiliary())).toArray());
                                        intent.putExtra("givenFormType", mFormType);
                                        intent.putExtra("total", total);
                                        intent.putExtra("marks", marks);
                                        startActivity(intent);
                                }
                        });

        }
}

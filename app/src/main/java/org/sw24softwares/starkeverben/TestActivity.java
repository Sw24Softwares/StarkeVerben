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

import static android.R.color.black;

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
		
		final long start = System.currentTimeMillis();

                final Vector<EditText> editForms = new Vector<EditText>();
                editForms.addElement((EditText) findViewById(R.id.infinitif));
                editForms.addElement((EditText) findViewById(R.id.preterit));
                editForms.addElement((EditText) findViewById(R.id.participe));
                editForms.addElement((EditText) findViewById(R.id.third_person));
                editForms.addElement((EditText) findViewById(R.id.translation));
                final CheckBox aux = (CheckBox) findViewById(R.id.auxiliary);
                
                for(int i = 0; i < editForms.size(); i++)
                        editForms.get(i).setHint(Verb.formToWord(i));
                
                Random rand = new Random();
                mGivenVerb = rand.nextInt(Settings.getSingleton().getVerbs().size());
                mVerb = Settings.getSingleton().getVerbs().get(mGivenVerb);
                mFormType = rand.nextInt(5);
                mPossibility = rand.nextInt(mVerb.getAllForms().get(mFormType).size());


                for(int i = 0; i < 4; i++)
                        if(mFormType == i)
                                fillEditText(editForms.get(i), mVerb.getAllForms().get(mFormType).get(mPossibility));
                if(mFormType == 4)
                        fillEditText(editForms.get(4), Settings.getSingleton().getTranslations().get(mGivenVerb).getTranslations().get(mPossibility));

                final int total = getIntent().getExtras().getInt("total");
                final int marks[] = getIntent().getExtras().getIntArray("marks");
                
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
                                        Intent intent = new Intent(TestActivity.this, ResultActivity.class);

                                        // Whether a test has taken more than 20 seconds to be done
					Boolean time = getIntent().getExtras().getBoolean("dialog");
                                        intent.putExtra("dialog", testDuration(start) || time);
					
					String givenAnswers[] = new String[6];
                                        givenAnswers[5] = Verb.boolToAux(aux.isChecked());
                                        for(int i = 0; i < editForms.size(); i++)
                                                givenAnswers[i] = editForms.get(i).getText().toString();

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

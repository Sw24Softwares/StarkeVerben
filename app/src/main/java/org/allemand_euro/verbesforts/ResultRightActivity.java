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

public class ResultRightActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_resultright);

                final TextView infinitif = (TextView) findViewById(R.id.infinitif_result);
                final TextView preterit = (TextView) findViewById(R.id.preterit_result);
                final TextView participe = (TextView) findViewById(R.id.participe_result);
                final TextView troisiemePersonne = (TextView) findViewById(R.id.troisieme_personne_result);
                final TextView traduction = (TextView) findViewById(R.id.traduction_result);
                final TextView aux = (TextView) findViewById(R.id.auxiliaire_result);                

                Vector<Vector<String>> answers = new Vector<Vector<String>>();
                for(int i = 0; i < 6; i++)
                        answers.addElement(new Vector(Arrays.asList(getIntent().getExtras().getStringArray(Questions.FormToWord(i)))));

                String givenAnswers[] = getIntent().getExtras().getStringArray("givenForms");
                int givenFormType = getIntent().getExtras().getInt("givenFormType");
        }
}

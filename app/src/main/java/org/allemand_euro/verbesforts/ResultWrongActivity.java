package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.res.Resources;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Vector;

public class ResultWrongActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_resultwrong);
                TextView text = (TextView) findViewById(R.id.comment);
                
                /*                String answers[] = getIntent().getExtras().getStringArray("answer");
                String toShow = new String(); //= text.getText().toString();
                for(int i = 0; i < answers.length; i++) {
                        toShow += answers[i];
                        if(i < answers.length - 1)
                                toShow += " ou ";
                                }

                                text.setText(toShow);*/

        }
}

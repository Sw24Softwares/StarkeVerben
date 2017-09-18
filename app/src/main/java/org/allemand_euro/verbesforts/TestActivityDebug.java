package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.Vector;

public class TestActivityDebug extends AppCompatActivity {
        public Question mQuestion;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_test1);
        }
}

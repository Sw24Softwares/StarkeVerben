package org.allemand_euro.verbesforts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.content.res.Resources;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                BufferedReader reader = null;
                try {
                        reader = new BufferedReader(new InputStreamReader(getAssets().open("verbs.txt"), "UTF-8"));
                        Loader.GetSingleton().Load(reader);
                } catch (IOException e) {
                        System.exit(0);
                }
                
                Button button = (Button) findViewById(R.id.test);
                button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, TestActivityDebug.class);
                                        startActivity(intent);
                                }
                        });
        }
}

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
                        Questions.CreateSingleton(Loader.GetSingleton().mVerbs);

                } catch (IOException e) {
                        System.exit(0);
                }

                Button testButton = (Button) findViewById(R.id.test);
                testButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, TestActivity.class);
                                        startActivity(intent);
                                }
                        });
                Button lessonButton = (Button) findViewById(R.id.lesson);
                lessonButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, LessonActivity.class);
                                        startActivity(intent);
                                }
                        });
        }

        @Override
        public void onBackPressed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
}

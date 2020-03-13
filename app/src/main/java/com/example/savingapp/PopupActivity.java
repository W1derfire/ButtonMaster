package com.example.savingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    TextView finalScore;
    Button otraVez;
    Button goToMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        saveModel();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        finalScore = findViewById(R.id.finalScoreView);
        otraVez = findViewById(R.id.playAgain);
        goToMenu = findViewById(R.id.goToMenu);


        finalScore.setText("Score: " + MainActivity.theModel.getLatestScore()+"\n\n");

        otraVez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToGame(v);
            }
        });

        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToHome(v);
            }
        });


    }

    protected void changeToGame(View view) {
        Intent changeIntent = new Intent(this, GameActivity.class);
        startActivity(changeIntent);
    };

    protected void changeToHome(View view) {
        Intent changeIntent = new Intent(this, MainActivity.class);
        startActivity(changeIntent);
    };

    private void saveModel() {
        // write (serialize) the model object
        try {
            System.out.println("Saving!");

            System.out.println(MainActivity.theModel.getName());

            File savedModelFile = new File(getApplicationContext().getFilesDir(), "savedButtonGameModel");
            FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
            ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
            out.writeObject(MainActivity.theModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}

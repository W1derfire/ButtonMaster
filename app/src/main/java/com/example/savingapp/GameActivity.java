package com.example.savingapp;
//package com.popupwindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import android.widget.PopupWindow;


public class GameActivity extends AppCompatActivity {

    //Model theModel;

    static Timer timer = new Timer();
    static int interval = 15;
    static int secs = 10;
    static boolean timesChanged = false;
    private TextView timeView;
    private Button button;
    Point size = new Point();
    private TextView scoreView;

    PopupWindow popupWindow;
    TextView finalScore;
    Button playAgainB;
    Button toMenu;
    ConstraintLayout linearLayot;
    LinearLayout linear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        restart();

        finalScore = findViewById(R.id.finalScoreView);
        playAgainB = findViewById(R.id.playAgain);
        toMenu = findViewById(R.id.goToMenu);

        linearLayot = findViewById(R.id.linearLayout1);



        //Timer stuff starts here.

        timeView = findViewById(R.id.timing);
        scoreView = findViewById(R.id.scoreView);

        button = findViewById(R.id.pointButton);


        ///Come back here!
        //

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getWindowManager().getDefaultDisplay().getSize(size);
                MainActivity.theModel.addPoint();
                button.setX((float)(Math.random()* (size.x*0.8)));
                button.setY((float)(Math.random()* (size.y*0.8)));
                updateScore();
            }
        });


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(setInterval());

                updateText();
                timesChanged = true;

                if(interval == 0){

                    if(MainActivity.theModel.getHighScore() < MainActivity.theModel.getLatestScore()) {
                        MainActivity.theModel.setHighScore(MainActivity.theModel.getLatestScore());
                    }
                    getPopUp();
                }

            }
        }, 1000, 1000);


    }


   // }

    @Override
    protected void onStop() {
        // save the model before quitting
        this.saveModel();

        super.onStop();
    }


    protected void getPopUp(){

        Intent i = new Intent(getApplicationContext(), PopupActivity.class);
        startActivity(i);

    }

    private void saveModel() {
        // write (serialize) the model object
        try {
            File savedModelFile = new File(getApplicationContext().getFilesDir(), "savedButtonGameModel");
            FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
            ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
            out.writeObject(MainActivity.theModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }


    protected void updateText(){

        this.runOnUiThread(new Runnable() { // EDIT: ...Ui... requires a lowercase "i"
            @Override
            public final void run() {
                // this runs on UI thread
                timeView.setText("Time: " + interval);
            }
        });

    }



    protected void updateScore(){

        this.runOnUiThread(new Runnable() { // EDIT: ...Ui... requires a lowercase "i"
            @Override
            public final void run() {
                // this runs on UI thread
                scoreView.setText("Score: " + MainActivity.theModel.getLatestScore());
            }
        });

    }


    private void restart() {
      interval = 15;


      MainActivity.theModel.setToZero();
      timer = new Timer();

    };

}

package com.example.savingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import android.content.Intent;
import java.io.*;
import android.content.Context;



public class MainActivity extends AppCompatActivity {


    static Model theModel;
    private Button goButton;
    EditText name;
    EditText pw;
    TextView highScore;
    static ImageView avatar;
    public static final int GET_FROM_GALLERY = 1;
    public Context context;
    public ProxyBitmap m;
    public static ProxyBitmap what;
    public static Drawable d;
    public ImageButton rotator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        d = getResources().getDrawable(R.drawable.gradient);

        //m = new ProxyBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.gradient));
        //what = m;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bitmap m =
        //static Bitmap what = m;
        //System.out.println(m.getWidth());

        //theModel = new Model();

        if(theModel == null){
            theModel = new Model();
        }

        //context = onCreateView().getContext();

        //theModel = new Model();

        goButton = findViewById(R.id.playButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                changeToGame(view);
            }
        });

        initializeModel();

        highScore = findViewById(R.id.highScoreView);
        highScore.setText("High Score: " + theModel.getHighScore());

        name = findViewById(R.id.nameEnterer);
        name.setText(theModel.getName());
        if(theModel.getImage() != null) {
            System.out.println(theModel.getImage().toString() + "\n\nHi\n\n");
        }
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                theModel.setName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        avatar = findViewById(R.id.avatar);
        if(theModel.getImage() != null) {
            avatar.setImageBitmap(theModel.getImage());
        }

        avatar.setRotation(theModel.getRotation());

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });


        rotator = findViewById(R.id.rotateButton);

        rotator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theModel.addRotation();
                avatar.setRotation(theModel.getRotation());
            }
        });

        }

    @Override
    protected void onStop() {
        // save the model before quitting
        this.saveModel();

        super.onStop();
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    private void saveModel() {
        // write (serialize) the model object
        try {
            System.out.println("Saving!");

            System.out.println(theModel.getName());

            File savedModelFile = new File(getApplicationContext().getFilesDir(), "savedButtonGameModel");
            FileOutputStream savedModelFileStream = new FileOutputStream(savedModelFile);
            ObjectOutputStream out = new ObjectOutputStream(savedModelFileStream);
            //System.out.println(theModel.getImage().toString() + "\n\nHi\n\n");
            out.writeObject(theModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void initializeModel() {
        // try to read (deserialize) model object from disk
        File savedModelFile = new File(getApplicationContext().getFilesDir(), "savedButtonGameModel");
        if (savedModelFile.exists()) {
            try {
                FileInputStream savedModelFileStream = new FileInputStream(savedModelFile);
                ObjectInputStream in = new ObjectInputStream(savedModelFileStream);
                //theModel.readObject();
                theModel = (Model)in.readObject();
                System.out.println(theModel.getName());
                in.close();
            } catch (Exception ex) {

                System.out.println("There was an error. Whoops!");

                ex.printStackTrace();
            }
        }
        if (theModel == null) {
            //theModel = new Model();
        }

        if(theModel.avatar == null){

            System.out.println(m.toString());
            //theModel.setImage(m);
        }

    }

        protected void changeToGame(View view) {
            Intent changeIntent = new Intent(this, GameActivity.class);
            startActivity(changeIntent);
        };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            //bitmap.
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //bitmap = MediaStore.Images.Media.getContentUri();
                avatar.setImageBitmap(bitmap);

                System.out.println("I like " + bitmap.toString()+"\n\n\n");
                theModel.setImage(bitmap);
                //System.out.println("I like " + theModel.getImage().toString()+"\n\n\n");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("There was an error. Whoops!\n\n");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("There was an error. Whoops!\n\n");
            }
        }
    }



    }




//}

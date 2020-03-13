package com.example.savingapp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.provider.MediaStore;
import android.util.Base64;



import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;


//import javax.imageio.ImageIO;

public class Model implements Serializable {

    public CharSequence name;
    public String password;
    public int latestScore;
    public int highScore;
    public int rotationFactor;
    public ProxyBitmap avatar;
    private byte[] encodeByte;
    private transient Bitmap bitmap;


    Model() {

        name = "";
        password = "";
        latestScore = 0;
        highScore = 0;
        avatar = new ProxyBitmap(MainActivity.drawableToBitmap(MainActivity.d));
        rotationFactor = 0;

    };


    private void writeObject(ObjectOutputStream objectOutput) throws IOException{
        objectOutput.defaultWriteObject();
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectOutput);
        // manually write the bitmap
    }

    private void readObject(ObjectInputStream objectInputStream) throws Exception{
        objectInputStream.defaultReadObject();

        //InputStream is = new BufferedInputStream(new FileInputStream("source.gif"));

        //objectInputStream.
        //setAvatar(SwingFXUtils.toFXImage(ImageIO.read(objectInputStream), null));



    }


     public CharSequence getName() {
        return name;
    }

     public void setName(CharSequence nam) {
        name = nam;
    }

     public String getPassword() {
        return password;
    }

     public void setPassword(String passwor) {
        password = passwor;
    }

    public void addRotation(){
        this.rotationFactor += 90;
        this.rotationFactor = this.rotationFactor%360;
    }

    public int getRotation(){
        return this.rotationFactor;
    }


    public Bitmap getImage() {

        return avatar.getBitmap();
    }

    public void setImage(Bitmap b) {
        avatar = new ProxyBitmap(b);
    }


     public int getLatestScore() {
        return latestScore;
    }

     public void addPoint() {
        latestScore += 1;
    }

     public void setToZero(){
        latestScore = 0;
        //highScore = 0;
    }

     public int getHighScore() {
        return highScore;
    }

     public void setHighScore(int highScre) {
        highScore = highScre;
    }
}

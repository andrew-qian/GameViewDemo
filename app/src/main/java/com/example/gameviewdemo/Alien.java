package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Alien {

    Bitmap alien[] = new Bitmap[2];

    int alienX, alienY, velocity, alienFrame;


    public Alien(Context context) {
        alien[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien1);
        alien[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien2);
        resetPosition();
    }

    public void resetPosition(){
        alienX = GameView.dWidth - 400;
        alienY = 100;
        velocity = 15;
        alienFrame = 0;
    }

    public void blankSprite(Context context) {
        Bitmap temp[] = new Bitmap[2];
        temp[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_pixel);
        temp[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_pixel);
        setAlien(temp);
    }

    public Bitmap getBitmap(){
        return alien[alienFrame];
    }

    public int getWidth(){
        return alien[0].getWidth();
    }

    public int getHeight(){
        return alien[0].getHeight();
    }

    public void setMediumalienX(int mediumalienX) {
        this.alienX = mediumalienX;
    }

    public int getMediumalienX() {
        return alienX;
    }

    public int getMediumalienY() {
        return alienY;
    }

    public void setMediumalienY(int mediumalienY) {
        this.alienY = mediumalienY;
    }


    public Bitmap[] getAlien() {
        return alien;
    }

    public void setAlien(Bitmap[] alien) {
        this.alien = alien;
    }
}

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

    public Bitmap getBitmap(){
        return alien[alienFrame];
    }

    public int getWidth(){
        return alien[0].getWidth();
    }

    public int getHeight(){
        return alien[0].getHeight();
    }

    public void setAlienX(int alienX) {
        this.alienX = alienX;
    }

    public int getAlienX() {
        return alienX;
    }

    public int getAlienY() {
        return alienY;
    }

    public void setAlienY(int alienY) {
        this.alienY = alienY;
    }


}

package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SmallAlien {

    Bitmap small_alien[] = new Bitmap[2];

    int small_alienX, smallAlienY, velocity, small_alienFrame;


    public SmallAlien(Context context) {
        small_alien[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien1);
        small_alien[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien2);
        resetPosition();
    }

    public void resetPosition(){
        small_alienX = GameView.dWidth - 400;
        smallAlienY = 100;
        velocity = 15;
        small_alienFrame = 0;
    }

    public Bitmap getBitmap(){
        return small_alien[small_alienFrame];
    }

    public int getWidth(){
        return small_alien[0].getWidth();
    }

    public int getHeight(){
        return small_alien[0].getHeight();
    }

    public void setSmall_alienX(int small_alienX) {
        this.small_alienX = small_alienX;
    }

    public int getSmall_alienX() {
        return small_alienX;
    }

    public int getSmallAlienY() {
        return smallAlienY;
    }

    public void setSmallAlienY(int smallAlienY) {
        this.smallAlienY = smallAlienY;
    }


}

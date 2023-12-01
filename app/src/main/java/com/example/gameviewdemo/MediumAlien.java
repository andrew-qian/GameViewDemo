package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MediumAlien extends Alien {

    Bitmap mediumalien[] = new Bitmap[2];


    int mediumalienX, mediumalienY, mediumalienvelocity, mediumalienFrame;


    public MediumAlien(Context context) {
        super(context);
        mediumalien[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien1);
        mediumalien[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien2);
    }

    @Override
    public void resetPosition() {
        mediumalienX = GameView.dWidth - 400;
        mediumalienY = 250;
        mediumalienvelocity = 25;
        mediumalienFrame = 0;
    }

    @Override
    public void blankSprite(Context context) {
        Bitmap temp[] = new Bitmap[2];
        temp[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_pixel);
        temp[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_pixel);
        setMediumalien(temp);
    }



    @Override
    public Bitmap getBitmap() {
        return mediumalien[mediumalienFrame];
    }

    @Override
    public int getWidth() {
        return mediumalien[0].getWidth();
    }

    @Override
    public int getHeight() {
        return mediumalien[0].getHeight();
    }

    @Override
    public int getMediumalienX() {
        return mediumalienX;
    }

    @Override
    public void setMediumalienX(int mediumalienX) {
        this.mediumalienX = mediumalienX;
    }

    @Override
    public int getMediumalienY() {
        return mediumalienY;
    }

    @Override
    public void setMediumalienY(int mediumalienY) {
        this.mediumalienY = mediumalienY;
    }

    public int getMediumalienvelocity() {
        return mediumalienvelocity;
    }

    public void setMediumalienvelocity(int mediumalienvelocity) {
        this.mediumalienvelocity = mediumalienvelocity;
    }

    public int getMediumalienFrame() {
        return mediumalienFrame;
    }

    public void setMediumalienFrame(int mediumalienFrame) {
        this.mediumalienFrame = mediumalienFrame;
    }

    public Bitmap[] getMediumalien() {
        return mediumalien;
    }

    public void setMediumalien(Bitmap[] mediumalien) {
        this.mediumalien = mediumalien;
    }

}

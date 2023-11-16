package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MediumAlien extends Alien {

    Bitmap alien[] = new Bitmap[2];

    public MediumAlien(Context context) {
        super(context);
        alien[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien1);
        alien[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien2);
    }

    @Override
    public void resetPosition() {
        alienX = GameView.dWidth - 400;
        alienY = 250;
        velocity = 15;
        alienFrame = 0;
    }

    @Override
    public Bitmap getBitmap() {
        return alien[alienFrame];
    }

    @Override
    public int getWidth() {
        return alien[0].getWidth();
    }

    @Override
    public int getHeight() {
        return alien[0].getHeight();
    }


}

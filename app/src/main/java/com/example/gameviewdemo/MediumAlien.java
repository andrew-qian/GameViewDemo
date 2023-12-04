package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MediumAlien {

    Bitmap mediumalien[] = new Bitmap[2];


    int mediumalienX, mediumalienY, mediumalienvelocity, mediumalienFrame;


    public MediumAlien(Context context) {
//        super(context);
        mediumalien[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien1);
        mediumalien[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.medium_alien2);
    }

    public void resetPosition() {
        mediumalienX = GameView.dWidth - 400;
        mediumalienY = 250;
        mediumalienvelocity = 125;
        mediumalienFrame = 0;
    }


}

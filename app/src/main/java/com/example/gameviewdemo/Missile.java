package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Missile {
    int x, y;

    int mVelocity;
    Bitmap missile;

    public Missile(Context context, int tankX, int tankY){
        missile = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
        x = tankX - getMissileWidth()/2;
        y = tankY - 58 - getMissileHeight()/2;
        mVelocity = 100;

    }
    public int getMissileWidth(){
        return missile.getWidth();
    }
    public int getMissileHeight(){
        return missile.getHeight();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(missile, x, y, null);

    }
}

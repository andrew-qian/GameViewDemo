package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Tank {

    int x, y;
    int height, width;
    Bitmap tankBitmap;

    public Tank(Context context, int x, int y, int width, int height) {
        Bitmap init  = BitmapFactory.decodeResource(context.getResources(), R.drawable.tank);
        tankBitmap = Bitmap.createScaledBitmap(init, width, height, false);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(tankBitmap, x, y, null);
    }

    public int getHeight() {
        return tankBitmap.getHeight();
    }

    public int getWidth() {
        return tankBitmap.getWidth();
    }
}

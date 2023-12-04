package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Tank {

    int x, y;
    int height, width;
    Bitmap tankBitmap;

    public Tank(Context context, int x, int y, int height, int width) {
        tankBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tank);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
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

package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Alien {

    Bitmap bitmaps[] = new Bitmap[2];

    int x, y, frame, height, width, rowPosition;


    public Alien(Context context, int x, int y, int width, int height, int rowPosition) {
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien1);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_alien2);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rowPosition = rowPosition;
        frame = 0;
    }

    public Bitmap getBitmap(int frame) {
        return bitmaps[frame];
    }

    public int getX() {
        return x;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }


    public int getFrame() {
        return frame;
    }

    public void updateFrame() {
        this.frame = (frame+1) % 2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmaps[frame], x, y, null);

    }


}

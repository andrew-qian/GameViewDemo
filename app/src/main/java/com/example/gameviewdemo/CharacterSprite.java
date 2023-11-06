package com.example.gameviewdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    private Bitmap image;
    private int x,y;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public CharacterSprite(Bitmap bmp) {
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {
        if (x<5 && y < 5){
            x = screenWidth/2;
            y = screenHeight/2;
        }
        else {
            x += xVelocity;
            y += yVelocity;
            if ((x > screenWidth - image.getWidth()) || (x < 0)){
                xVelocity = xVelocity*-1;
            }
            if ((y > screenHeight - image.getHeight()) || (y < 0)) {
                yVelocity = yVelocity * -1;
            }
        }
    }
}
package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Arrow{

    int x, y, width, height;
    int isRight;

    Bitmap bitmaps[] = new Bitmap[2];


    public Arrow(Context context, int x, int y, int width, int height, int isRight) {
        Bitmap init0  = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_left);
        Bitmap init1  = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        bitmaps[0] = Bitmap.createScaledBitmap(init0, width, height, false);
        bitmaps[1] = Bitmap.createScaledBitmap(init1, width, height, false);
        this.x = x;
        this.y = y;
        this.isRight = isRight;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmaps[isRight], x, y, null);
    }


}

package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Button {
    int x, y, frame;

    Bitmap bitmaps[] = new Bitmap[2];

    public Button(Context context) {
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_unpressed);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.button_pressed);
        this.x = 800;
        this.y = 1000;

        frame = 0;
    }

    public void updateFrame() {
        this.frame = (frame+1) % 2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmaps[frame], x, y, null);
    }

}

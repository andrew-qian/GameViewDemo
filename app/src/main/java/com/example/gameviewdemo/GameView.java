package com.example.gameviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.time.chrono.ChronoLocalDate;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private CharacterSprite characterSprite;

    Bitmap background;
    Rect rect;
    int dWidth, dHeight;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight= size.y;
        rect = new Rect(0,0,dWidth,dHeight);
        thread = new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.alien));

        thread.setRunning(true);
        thread.t.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        characterSprite.update();
    }

//    @Override
//    protected void onDraw(Canvas canvas){
//        super.onDraw(canvas);
//        canvas.drawBitmap(background,null,rect,null);
//    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background,null,rect,null);
        characterSprite.draw(canvas);
    }


}

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
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.time.chrono.ChronoLocalDate;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private CharacterSprite characterSprite;

    Bitmap background;
    Rect rect;
    int dWidth, dHeight;

    Bitmap small_alien[] = new Bitmap[2];
    int small_alienX, smallAlienY, velocity, small_alienFrame;
    int small_alienWidth;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;

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
        small_alien[0] = BitmapFactory.decodeResource(getResources(), R.drawable.small_alien1);
        small_alien[1] = BitmapFactory.decodeResource(getResources(), R.drawable.small_alien2);
        small_alienX = 400;
        smallAlienY = 100;
        velocity = 2;
        small_alienFrame = 0;
        small_alienWidth = small_alien[0].getWidth();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
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

    @Override
    protected void onDraw(Canvas canvas){

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background,null,rect,null);
        canvas.drawBitmap(small_alien[small_alienFrame], small_alienX, smallAlienY, null);

        if small_alienFrame++;
        if(small_alienFrame > 1){
            small_alienFrame = 0;
        }
        small_alienX -= velocity;
        if (small_alienX < 5 || small_alienX > (dWidth - small_alienWidth - 5)){
            velocity = -velocity;
        }
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


}

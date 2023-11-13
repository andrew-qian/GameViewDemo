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
import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private CharacterSprite characterSprite;

    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<SmallAlien> smallAliens;


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
        smallAliens = new ArrayList<>();
        for(int i=0; i<2;i++){
            SmallAlien smallAlien = new SmallAlien(context);
            smallAliens.add(smallAlien);
        }

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
        for(int i=0; i<smallAliens.size(); i++){
            SmallAlien currentSA = smallAliens.get(i);
            canvas.drawBitmap(currentSA.getBitmap(), currentSA.small_alienX, currentSA.smallAlienY, null);
            currentSA.small_alienFrame++;
            if(currentSA.small_alienFrame > 1){
                currentSA.small_alienFrame = 0;
            }
            currentSA.small_alienX -= currentSA.velocity;
            if(currentSA.small_alienX < currentSA.getWidth()){
                currentSA.resetPosition();
                if (currentSA.small_alienX < 5 || currentSA.small_alienX > (dWidth - currentSA.getWidth() - 5)){
                    currentSA.velocity = -currentSA.velocity;
              }
            }
        }
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


}

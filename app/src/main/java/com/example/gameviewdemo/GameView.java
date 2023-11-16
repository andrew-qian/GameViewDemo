package com.example.gameviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Alien> aliens, mediumAliens;

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
        aliens = new ArrayList<>();
        mediumAliens = new ArrayList<>();
        for(int i=0; i<2;i++){
            Alien alien = new Alien(context);
            alien.setAlienX(alien.getAlienX() - i*(alien.getWidth() + 50));
            aliens.add(alien);
            MediumAlien mediumAlien = new MediumAlien(context);
            mediumAliens.add(mediumAlien);
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

    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background,null,rect,null);

        for(int i = 0; i< aliens.size(); i++){
            Alien currentSA = aliens.get(i);
            canvas.drawBitmap(currentSA.getBitmap(), currentSA.alienX, currentSA.alienY, null);
            currentSA.alienFrame++;
            if(currentSA.alienFrame > 1){
                currentSA.alienFrame = 0;
            }
            Alien currentMA = mediumAliens.get(i);
            canvas.drawBitmap(currentMA.getBitmap(), currentMA.alienX, currentMA.alienY, null );
            currentMA.alienFrame++;
            if(currentMA.alienFrame > 1){
                currentMA.alienFrame = 0;
            }
            try {
                Thread.sleep(UPDATE_MILLIS + 70);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentSA.alienX -= currentSA.velocity;
            if ((currentSA.alienX - currentSA.getWidth()) < 5 || (currentSA.alienX + currentSA.getWidth()) > (dWidth - currentSA.getWidth() - 5)) {
                if (i == 1) {
                    currentSA.velocity = -currentSA.velocity;
                    currentSA.setAlienY(currentSA.getAlienY() + 100);
                } else {
                    Alien lead = aliens.get(1);
                    currentSA.setAlienY(lead.getAlienY());
                    currentSA.velocity = -currentSA.velocity;

                }

            }
            currentMA.alienX -= currentSA.velocity;

        }
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


}

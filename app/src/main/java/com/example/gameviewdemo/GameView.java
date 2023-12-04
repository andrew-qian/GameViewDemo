package com.example.gameviewdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Alien> aliens;
    ArrayList<MediumAlien> mediumAliens;
    ArrayList<Missile> missiles;
    ArrayList<Explosion> explosions;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;


    AlienRow alienRow1, alienRow2;

    Tank tank;

    Context context;

    Button button;
    int fire = 0, point = 0, count = 0;


    public GameView(Context context) {
        super(context);
        this.context = context;

        getHolder().addCallback(this);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        thread = new MainThread(getHolder(), this);
        aliens = new ArrayList<>();
        mediumAliens = new ArrayList<>();
        missiles = new ArrayList<>();
        explosions = new ArrayList<>();

        alienRow1 = new AlienRow(context, 3, 15, 90, 65, 0);
        alienRow2 = new AlienRow(context, 3, 15, 90, 65, 1);

        tank = new Tank(context, (dWidth / 2 - 90 / 2), (dHeight - 58), 58, 90);

        button = new Button(context);

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
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(background, null, rect, null);
        moveRow(alienRow1, canvas);
        moveRow(alienRow2, canvas);
        tank.draw(canvas);
        button.draw(canvas);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveRow(AlienRow alienRow, Canvas canvas){
        int nextX = alienRow.x + alienRow.velocity;
        int nextY = alienRow.y;
        if (nextX <= 0){
            nextX = 0;
            alienRow.changeDirection();
            nextY += alienRow.height*3;
        }

        if (nextX > dWidth - alienRow.alienWidth*alienRow.numAliens*2){
            nextX = dWidth - alienRow.alienWidth*alienRow.numAliens*2;
            alienRow.changeDirection();
            nextY += alienRow.height*3;
        }

        alienRow.setPosition(nextX, nextY);
        alienRow.draw(canvas);
        alienRow.updateFrame();
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        // change so on some button
//        if(action == MotionEvent.ACTION_DOWN){
//            if(touchX >= (dWidth/2 - tank.width/2) && touchX <= (dWidth/2 + tank.width/2) && touchY >= (dHeight - tank.height)){
//                Log.i("Tank","is tapped");
//                if (missiles.size() < 3) {
//                    Missile m = new Missile(context);
//                    missiles.add(m);
//                }
//            }
//
//        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tank.x = (int)touchX;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            tank.x = (int)touchX;
        }
        return true;
    }


}

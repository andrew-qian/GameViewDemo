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

    Bitmap background, tankBitmap;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Alien> aliens;
    ArrayList<MediumAlien> mediumAliens;
    ArrayList<Missile> missiles;
    ArrayList<Explosion> explosions;

    ArrayList<AlienRow> alienrows;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;


    AlienRow alienRow1, alienRow2;

    Tank tank;

    Context context;

    Button button;

    Arrow arrowLeft, arrowRight;

    public static final int NUM_ALIENROWS = 3;

    int fire = 0, point = 0, count = 0;


    public GameView(Context context) {
        super(context);
        this.context = context;

        getHolder().addCallback(this);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        tankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
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
        alienrows = new ArrayList<>();

        for (int i = 0; i < NUM_ALIENROWS; i++){
            AlienRow alienRow = new AlienRow(context, 3, 15, dWidth / 8, dWidth / 8, i);
            alienrows.add(alienRow);
        }



        alienRow1 = new AlienRow(context, 3, 15, dWidth / 8, dWidth / 8, 0);
        alienRow2 = new AlienRow(context, 3, 15, dWidth / 8, dWidth / 8, 1);

        alienrows.add(alienRow1)

        tank = new Tank(context, (dWidth / 2 - tankBitmap.getWidth()/2), (dHeight - 580), dWidth / 5, dWidth / 8);

        button = new Button(context, dWidth * 2 / 3, dHeight * 5 / 6);

        arrowLeft = new Arrow(context, dWidth /8, dHeight * 6 / 7, dWidth / 6, dWidth / 6, 0);
        arrowRight = new Arrow(context, dWidth /8 + dWidth / 6 + dWidth / 50, dHeight * 6 / 7 , dWidth / 6, dWidth / 6, 1);


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
        canvas.drawBitmap(background, null, rect, null);
        moveRow(alienRow1, canvas);
        moveRow(alienRow2, canvas);
        tank.draw(canvas);
        button.draw(canvas);
        arrowLeft.draw(canvas);
        arrowRight.draw(canvas);

        moveMissile(canvas);
        checkCollision(canvas);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveRow(AlienRow alienRow, Canvas canvas) {
        int nextX = alienRow.x + alienRow.velocity;
        int nextY = alienRow.y;
        if (nextX <= 0) {
            nextX = 0;
            alienRow.changeDirection();
            nextY += alienRow.height;
        }

        if (nextX > dWidth - alienRow.width) {
            nextX = dWidth - alienRow.width;
            alienRow.changeDirection();
            nextY += alienRow.height;
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
        boolean moveLeft = false;
        boolean moveRight = false;

//        tank.x = (int)touchX - tank.width/2;
        if (action == MotionEvent.ACTION_DOWN) {
            if (touchX >= (button.x) && touchX <= (button.x + button.bitmaps[0].getWidth()) && touchY >= (button.y) && touchY <= (button.y + button.bitmaps[0].getHeight())) {
                Log.i("Qian", "missile created");
                button.updateFrame();
                if (missiles.size() < 3) {
                    Missile m = new Missile(context, tank.x + tank.getWidth()/2, tank.y, dWidth/32, dHeight/16);
                    missiles.add(m);
                }

            }

            if (touchX >= (arrowLeft.x) && touchX <= (arrowLeft.x + arrowLeft.bitmaps[0].getWidth()) && touchY >= (arrowLeft.y) && touchY <= (arrowLeft.y + arrowLeft.bitmaps[0].getHeight())) {
                if (!(tank.x - 60 < 0)){
                    tank.x -= 60;
                }
            }

            if (touchX >= (arrowRight.x) && touchX <= (arrowRight.x + arrowRight.bitmaps[0].getWidth()) && touchY >= (arrowRight.y) && touchY <= (arrowRight.y + arrowRight.bitmaps[0].getHeight())) {
                if (!(tank.x + tank.getWidth() + 60 > dWidth)){
                    tank.x += 60;
                }
            }
        }

        return false;

    }


    public void moveMissile(Canvas canvas){
        ArrayList<Missile> missilesToRemove = new ArrayList<>();
        for (Missile missile : missiles){
            missile.y -= missile.mVelocity;
            missile.draw(canvas)  ;
            if (missile.y + missile.getMissileHeight() < 0){
                missilesToRemove.add(missile);
            }

        }
        for (Missile missile : missilesToRemove){
            missiles.remove(missile);
        }
    }

    public void checkCollision(Canvas canvas){
        ArrayList<Missile> missilesToDelete = new ArrayList<>();
        ArrayList<Alien> alienRow1AliensToDelete = new ArrayList<>();
        ArrayList<Alien> alienRow2AliensToDelete = new ArrayList<>();

        for (Missile missile : missiles){
            for (Alien alien : alienRow1.alienArray){
                if (missileCollision(alien, missile)){
                    Log.i("qian", "collision");
                    addExplosion(canvas, alien);
                    missilesToDelete.add(missile);
                    alienRow1AliensToDelete.add(alien);
                }
            }
            for (Alien alien : alienRow2.alienArray){
                if (missileCollision(alien, missile)){
                    Log.i("qian", "collision");
                    addExplosion(canvas, alien);
                    missilesToDelete.add(missile);
                    alienRow2AliensToDelete.add(alien);
                }
            }
        }

        for (Missile missile : missilesToDelete){
            missiles.remove(missile);
        }
        for (Alien alien : alienRow1AliensToDelete){
            alienRow1.removeAlien(alien);
        }
        for (Alien alien : alienRow2AliensToDelete){
            alienRow2.removeAlien(alien);
        }
    }

    public boolean missileCollision(Alien alien, Missile missile) {
        if (alien.y + alien.height < missile.y || alien.y > missile.y + missile.getMissileHeight()) {
            return false;
        }
        if (alien.x + alien.width < missile.x || alien.x > missile.x + missile.getMissileWidth()) {
            return false;
        }
        return true;
    }

    public void addExplosion(Canvas canvas, Alien alien){
        Explosion explosion = new Explosion(context, alien);
        explosion.x = alien.x + alien.width / 2 - explosion.getExplosionWidth() / 2;
        explosion.y = alien.y + alien.height / 2 - explosion.getExplosionHeight() / 2;
        explosion.draw(canvas);
    }


}

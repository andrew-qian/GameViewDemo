package com.example.gameviewdemo;

import android.app.Activity;
import android.content.Context;
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

    Bitmap background, tank;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Alien> aliens, mediumAliens, missiles, explosions;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    int tankWidth;
    static int tankHeight;




    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight= size.y;
        rect = new Rect(0,0,dWidth,dHeight);
        thread = new MainThread(getHolder(), this);
        aliens = new ArrayList<>();
        missiles = new ArrayList<>();
        explosions = new ArrayList<>();


        mediumAliens = new ArrayList<>();
        for(int i=0; i<2;i++){
            Alien alien = new Alien(context);
            alien.setAlienX(alien.getAlienX() - i*(alien.getWidth() + 50));
            aliens.add(alien);
            MediumAlien mediumAlien = new MediumAlien(context);
            mediumAlien.setAlienX(mediumAlien.getAlienX() - i*(mediumAlien.getWidth() + 50));
            mediumAliens.add(mediumAlien);
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        tankWidth = tank.getWidth();
        tankHeight = tank.getHeight();
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
            Alien lead = aliens.get(1);
            if ((lead.alienX - lead.getWidth()) < 5 || (lead.alienX + lead.getWidth()) > (dWidth - lead.getWidth() - 5)) {
                if (i == 1) {
                    currentSA.velocity = -currentSA.velocity;
                    currentSA.setAlienY(currentSA.getAlienY() + 100);
                } else {
                    currentSA.setAlienY(lead.getAlienY());
                    currentSA.velocity = -currentSA.velocity;
                }

            }

            currentMA.alienX -= currentMA.velocity;
            Alien leadMedium = mediumAliens.get(1);

            if ((leadMedium.alienX - leadMedium.getWidth()) < 5 || (leadMedium.alienX + leadMedium.getWidth()) > (dWidth - leadMedium.getWidth() - 5)) {
                if (i == 1) {
                    currentMA.velocity = -currentMA.velocity;
                    currentMA.setAlienY(currentMA.getAlienY() + 100);
                } else {
                    currentMA.setAlienY(leadMedium.getAlienY());
                    currentMA.velocity = -currentMA.velocity;
                }

            }

        }
        canvas.drawBitmap(tank, (dWidth/2 - tankWidth/2), dHeight-tankHeight, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(touchX >= (dWidth/2 - tankWidth/2) && touchX <= (dWidth/2 + tankWidth/2) && touchY >= (dHeight - tankHeight)){ // change so on some button
                Log.i("Tank","is tapped");
                if (missiles.size() < 3) {
                    Missile m = new Missile(context);
                    missiles.add(m);
                    if(fire != 0){
                        sp.play(fire, 1, 1, 0, 0, 1);
                    }
                }
            }
        }
        return true;
    }


}

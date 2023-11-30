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
    ArrayList<Alien> aliens;
    ArrayList<MediumAlien> mediumAliens;
    ArrayList<Missile> missiles;
    ArrayList<Explosion> explosions;

    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    int tankWidth;
    static int tankHeight;

    Context context;
    int fire = 0, point = 0, count = 0;


    public GameView(Context context) {
        super(context);
        this.context = context;

        getHolder().addCallback(this);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        tank = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
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



        for (int i = 0; i < 2; i++) {
            Alien alien = new Alien(context);
            alien.setMediumalienX(alien.getMediumalienX() - i * (alien.getWidth() + 50));
            aliens.add(alien);
            MediumAlien mediumAlien = new MediumAlien(context);
            mediumAlien.setMediumalienX(mediumAlien.getMediumalienX() - i * (mediumAlien.getWidth() + 50));
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
        canvas.drawBitmap(background, null, rect, null);
        canvas.drawBitmap(tank, (dWidth / 2 - tankWidth / 2), dHeight - tankHeight, null);


        for (int i = 0; i < 2; i++) {
            Boolean SAnotNull = true;
            Boolean MAnotNull = true;
            Alien currentSA = aliens.get(i);
            MediumAlien currentMA = mediumAliens.get(i);

            if (currentSA == null){
                SAnotNull = false;
                Log.i("Qian", "small alien is null");

            }

            if (SAnotNull){
                canvas.drawBitmap(currentSA.getBitmap(), currentSA.alienX, currentSA.alienY, null);
                currentSA.alienFrame++;
                if (currentSA.alienFrame > 1) {
                    currentSA.alienFrame = 0;
                }
            }

            if (currentMA == null){
                MAnotNull = false;
                Log.i("Qian", "medium alien is null");
            }


            if (MAnotNull){
                canvas.drawBitmap(currentMA.getBitmap(), currentMA.mediumalienX, currentMA.mediumalienY, null);
                currentMA.mediumalienFrame++;
                if (currentMA.mediumalienFrame > 1) {
                    currentMA.mediumalienFrame = 0;
                }
            }

            try {
                Thread.sleep(UPDATE_MILLIS + 70);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (SAnotNull){
                currentSA.alienX -= currentSA.velocity;
                Alien lead = aliens.get(1);
                if (lead == null){
                    lead = aliens.get(0);
                }
                if ((lead.alienX - lead.getWidth()) < 5 || (lead.alienX + lead.getWidth()) > (dWidth - lead.getWidth() - 5)) {
                    if (i == 1) {
                        currentSA.velocity = -currentSA.velocity;
                        currentSA.setMediumalienY(currentSA.getMediumalienY() + 100);
                    } else {
                        currentSA.setMediumalienY(lead.getMediumalienY());
                        currentSA.velocity = -currentSA.velocity;
                    }

                }
            }

            if (MAnotNull){
                currentMA.mediumalienX -= currentMA.mediumalienvelocity;
                MediumAlien leadMedium = mediumAliens.get(1);
                if (leadMedium == null){
                    leadMedium = mediumAliens.get(0);
                }

                if ((leadMedium.mediumalienX - leadMedium.getWidth()) < 5 || (leadMedium.mediumalienX + leadMedium.getWidth()) > (dWidth - leadMedium.getWidth() - 5)) {
                    if (i == 1) {
                        currentMA.mediumalienvelocity = -currentMA.mediumalienvelocity;
                        currentMA.setMediumalienY(currentMA.getMediumalienY() + 100);
                    } else {
                        currentMA.setMediumalienY(leadMedium.getMediumalienY());
                        currentMA.mediumalienvelocity = -currentMA.mediumalienvelocity;
                    }

                }
            }
        }

        for (int i = 0; i < missiles.size(); i++) {
            if (missiles.get(i).y > -missiles.get(i).getMissileHeight()) {

                missiles.get(i).y -= missiles.get(i).mVelocity;
                canvas.drawBitmap(missiles.get(i).missile, missiles.get(i).x, missiles.get(i).y, null);

                Boolean contactAlienZero = missiles.get(i).x >= aliens.get(0).alienX && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (aliens.get(0).alienX + aliens.get(0).getWidth()) && missiles.get(i).y >= aliens.get(0).alienY &&
                        missiles.get(i).y <= (aliens.get(0).alienY + aliens.get(0).getHeight());
                Boolean contactAlienOne = missiles.get(i).x >= aliens.get(1).alienX && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (aliens.get(1).alienX + aliens.get(1).getWidth()) && missiles.get(i).y >= aliens.get(1).alienY &&
                        missiles.get(i).y <= (aliens.get(1).alienY + aliens.get(1).getHeight());
                Boolean contactMediumAlienZero = missiles.get(i).x >= mediumAliens.get(0).mediumalienX && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (mediumAliens.get(0).mediumalienX + mediumAliens.get(0).getWidth()) && missiles.get(i).y >= mediumAliens.get(0).mediumalienY &&
                        missiles.get(i).y <= (mediumAliens.get(0).mediumalienY + mediumAliens.get(0).getHeight());
                Boolean contactMediumAlienOne = missiles.get(i).x >= mediumAliens.get(1).mediumalienX && (missiles.get(i).x + missiles.get(i).getMissileWidth())
                        <= (mediumAliens.get(1).mediumalienX + mediumAliens.get(1).getWidth()) && missiles.get(i).y >= mediumAliens.get(1).mediumalienY &&
                        missiles.get(i).y <= (mediumAliens.get(1).mediumalienY + mediumAliens.get(1).getHeight());
                if (contactAlienZero) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionX = aliens.get(0).alienX + aliens.get(0).getWidth() / 2 - explosion.getExplosionWidth() / 2;
                    explosion.explosionY = aliens.get(0).alienY + aliens.get(0).getHeight() / 2 - explosion.getExplosionHeight() / 2;
                    explosions.add(explosion);
                    aliens.set(0, null);
//                    aliens.get(0).blankSprite(context);
                    count++;
                    missiles.remove(i);

                } else if (contactAlienOne) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionX = aliens.get(1).alienX + aliens.get(1).getWidth() / 2 - explosion.getExplosionWidth() / 2;
                    explosion.explosionY = aliens.get(1).alienY + aliens.get(1).getHeight() / 2 - explosion.getExplosionHeight() / 2;
                    explosions.add(explosion);
                    aliens.set(1, null);
//                    aliens.get(1).blankSprite(context);
                    count++;
                    missiles.remove(i);
                }
//

                else if (contactMediumAlienZero) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionX = mediumAliens.get(0).mediumalienX + mediumAliens.get(0).getWidth() / 2 - explosion.getExplosionWidth() / 2;
                    explosion.explosionY = mediumAliens.get(0).mediumalienY + mediumAliens.get(0).getHeight() / 2 - explosion.getExplosionHeight() / 2;
                    explosions.add(explosion);
                    mediumAliens.set(0, null);

//                    mediumAliens.get(0).blankSprite(context);
                    count++;
                    missiles.remove(i);


                } else if (contactMediumAlienOne) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionX = mediumAliens.get(1).mediumalienX + mediumAliens.get(1).getWidth() / 2 - explosion.getExplosionWidth() / 2;
                    explosion.explosionY = mediumAliens.get(1).mediumalienY + mediumAliens.get(1).getHeight() / 2 - explosion.getExplosionHeight() / 2;
                    explosions.add(explosion);
                    mediumAliens.set(1, null);

//                    mediumAliens.get(1).blankSprite(context);
                    count++;
                    missiles.remove(i);

                    }
                } else {
                    missiles.remove(i);
                }

        }

        for(int j=0; j<explosions.size(); j++){
            canvas.drawBitmap(explosions.get(j).getExplosion(explosions.get(j).explosionFrame), explosions.get(j).explosionX,
                    explosions.get(j).explosionY, null);
            explosions.get(j).explosionFrame++;
            if(explosions.get(j).explosionFrame > 0){
                explosions.remove(j);
            }
        }

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
                }
            }
        }
        return true;
    }


}

package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Explosion {

    Bitmap explosion;
    int x,y;

    public Explosion(Context context, Alien alien){
        Bitmap init = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
        explosion = Bitmap.createScaledBitmap(init, alien.width, alien.height, false);
        this.x = alien.x;
        this.y = alien.y;
    }
    public Bitmap getExplosion(int explosionFrame){
        return explosion;
    }
    public int getExplosionWidth(){
        return explosion.getWidth();
    }
    public int getExplosionHeight(){
        return explosion.getHeight();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(explosion, x, y, null);
    }

}

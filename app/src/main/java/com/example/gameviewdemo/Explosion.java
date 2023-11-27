package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    Bitmap explosion[] = new Bitmap[1];
    int explosionFrame=0;
    int explosionX, explosionY;

    public Explosion(Context context){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
    }
    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }
    public int getExplosionWidth(){
        return explosion[0].getWidth();
    }
    public int getExplosionHeight(){
        return explosion[0].getHeight();
    }
}

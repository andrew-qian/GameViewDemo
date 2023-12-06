package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlienMatrix {
    Alien[][] matrix = new Alien[3][5];
    int x, y, velocity, width, height, alienWidth, alienHeight;

    public AlienMatrix(Context context, int x, int y, int velocity, int alienWidth, int alienHeight) {
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                matrix[r][c] = new Alien(context, c* alienWidth , r * alienHeight, alienWidth, alienHeight, r);
            }
        }
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.width = alienWidth * 5;
        this.height = alienHeight * 3;
        this.alienWidth = alienWidth;
        this.alienHeight = alienHeight;
    }

    public void removeAlien(Alien alien) {
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] == alien){
                    matrix[r][c] = null;
                }
            }
        }
    }

    public Alien didCollide(Missile missile){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null && missileCollision(matrix[r][c], missile)){
                    return matrix[r][c];
                }
            }
        }
        return null;
    }

    private boolean missileCollision(Alien alien, Missile missile) {
        if (alien.y + alien.height < missile.y || alien.y > missile.y + missile.getMissileHeight()) {
            return false;
        }
        if (alien.x + alien.width < missile.x || alien.x > missile.x + missile.getMissileWidth()) {
            return false;
        }
        return true;
    }


    public void changeDirection(){
        velocity = -velocity;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null){
                    matrix[r][c].setPosition(x + c*alienWidth, y + r* alienHeight);
                }
            }
        }
    }

    public void draw(Canvas canvas){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null){
                    matrix[r][c].draw(canvas);
                }
            }
        }
    }

    public void updateFrame(){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null){
                    matrix[r][c].updateFrame();
                }
            }
        }
    }

    public void move(Canvas canvas, int dWidth) {
        int nextX = x + velocity;
        int nextY = y;
        if (nextX <= 0) {
            nextX = 0;
            changeDirection();
            nextY += alienHeight;
        }

        if (nextX > dWidth - width) {
            nextX = dWidth - width;
            changeDirection();
            nextY += alienHeight;
        }

        setPosition(nextX, nextY);
        updateFrame();
    }

    public boolean gameOverCheck(int height){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null){
                    if (matrix[r][c].y + alienHeight >= height){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isAllDead(){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 5; c++){
                if (matrix[r][c] != null){
                    return false;
                }
            }
        }
        return true;
    }
}

package com.example.gameviewdemo;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class AlienRow {

    ArrayList<Alien> alienArray = new ArrayList<>();

    int velocity, width, height, x, y, alienWidth, numAliens;


    public AlienRow(Context context, int numAliens, int velocity,int alienWidth, int alienHeight, int rowNum) {
        for (int i = 0; i < numAliens; i++){
            alienArray.add(new Alien(context, i* alienWidth , rowNum * alienHeight, alienWidth, alienHeight, i));
        }
        this.numAliens = numAliens;
        this.x = 0;
        this.y = rowNum * alienHeight;
        this.width = numAliens *alienWidth;
        this.height = alienHeight;

        this.velocity = velocity;
        this.alienWidth = alienWidth;
    }

    public boolean removeAlien(Alien alien) {
        boolean result =  alienArray.remove(alien);
        if (!alienArray.isEmpty()){
            width = alienArray.get(alienArray.size() - 1 ).getX() - alienArray.get(0).getX() + alienWidth;
            x = alienArray.get(0).getX();
        }

        return result;
    }

    public void changeDirection(){
        velocity = -velocity;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < alienArray.size(); i++){
            alienArray.get(i).setPosition(x+alienWidth*i, y);
        }
    }

    public void draw(Canvas canvas){
        for (Alien alien : alienArray){
            alien.draw(canvas);
        }
    }

    public void updateFrame(){
        for (Alien alien : alienArray) {
            alien.updateFrame();
        }
    }
}

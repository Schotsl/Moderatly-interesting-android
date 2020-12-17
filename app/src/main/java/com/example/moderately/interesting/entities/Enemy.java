package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.moderately.interesting.Util;
import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Enemy {
    private Bitmap bitmap;
    private Velocity velocity = new Velocity();
    private Position position = new Position();

    private int screenWidth;
    private int screenHeight;

    public Enemy(int screenWidth, int screenHeight, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.bitmap = bitmap;
        this.position.yRandom(-screenHeight, 0);
        this.position.xRandom(0, screenWidth);
        this.velocity.yRandom(7, 10);
    }

    public void draw(Canvas canvas) {
        Util.centerDraw(position, bitmap, canvas);
    }

    public Position update() {
        position.yPosition += velocity.yVelocity;

        if (position.yPosition - bitmap.getHeight() / 2 > screenHeight) {
            position.yPosition = -bitmap.getHeight() / 2;
            position.xPosition = (int) (Math.random() * screenWidth);
        }

        // Have yet to find a way to clone the position object
        Position position = new Position();
        position.yPosition = this.position.yPosition;
        position.xPosition = this.position.xPosition;
        return position;
    }
}

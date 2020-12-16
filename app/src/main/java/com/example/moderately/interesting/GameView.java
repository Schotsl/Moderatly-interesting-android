package com.example.moderately.interesting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.BitmapFactory;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private StarSprite[] starSprites = new StarSprite[1000];
    private EnemySprite[] enemySprites = new EnemySprite[10];

    private PlayerSprite playerSprite;

    private Integer xTouch;
    private Integer yTouch;

    public GameView(Context context) {
        super(context);

        setFocusable(true);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        xTouch = (int) event.getX();
        yTouch = (int) event.getY();

        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            xTouch = null;
            yTouch = null;
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        Bitmap enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        // Initialize the player and every single star
        playerSprite = new PlayerSprite(screenWidth, screenHeight, playerBitmap);
        for (int i = 0; i < starSprites.length; i ++) starSprites[i] = new StarSprite(screenWidth, screenHeight);
        for (int i = 0; i < enemySprites.length; i ++) enemySprites[i] = new EnemySprite(screenWidth, screenHeight, enemyBitmap);

        // Start the game logic
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
    }

    public void update() {
        // Update the position of the player and every single star
        for (int i = 0; i < starSprites.length; i ++) starSprites[i].update();
        for (int i = 0; i < enemySprites.length; i ++) enemySprites[i].update();
        playerSprite.update(xTouch);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (canvas != null) {
            // Draw the player and every single star
            for (int i = 0; i < starSprites.length; i ++) starSprites[i].draw(canvas);
            for (int i = 0; i < enemySprites.length; i ++) enemySprites[i].draw(canvas);
            playerSprite.draw(canvas);
        }
    }
}
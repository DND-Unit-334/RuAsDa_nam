package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.List;

public class Redestrian {
    private float x, y;
    private float speed;
    private Paint paint;
    private int screenWidth, screenHeight;
    private boolean movingForward;
    private int stopCounter;

    public Redestrian(float startX, float roadY, float speed, int screenWidth, int screenHeight) {
        this.x = startX;
        this.y = roadY;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.movingForward = true;
        this.stopCounter = 0;

        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    public void update(List<Car> cars) {
        if (stopCounter > 0) {
            stopCounter--;
            return;
        }


        for (Car car : cars) {
            if (x < car.getX() + 50 && x + 10 > car.getX() && y > car.getY() - 20 && y < car.getY() + 20) {
                stopCounter = 60;
                return;
            }
        }

        if (movingForward) {
            x += speed;
        } else {
            x -= speed;
        }

        if (x > screenWidth) {
            movingForward = false;
        } else if (x < 0) {
            movingForward = true;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y - 20, x + 10, y + 20, paint);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

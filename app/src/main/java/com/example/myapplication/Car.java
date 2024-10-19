package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Car {
    private float x, y;
    private float speed;
    private Paint paint;

    public Car(float startX, float startY, float speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;

        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public void update() {
        x += speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x, y - 20, x + 50, y + 20, paint);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

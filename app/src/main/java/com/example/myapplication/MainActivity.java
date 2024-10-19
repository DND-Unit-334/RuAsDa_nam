package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SurfaceView gameSurface;
    private List<Redestrian> pedestrians;
    private List<Car> cars;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private Thread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSurface = findViewById(R.id.gameSurface);
        surfaceHolder = gameSurface.getHolder();
        pedestrians = new ArrayList<>();
        cars = new ArrayList<>();

        gameSurface.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int screenWidth = gameSurface.getWidth();
                int screenHeight = gameSurface.getHeight();
                int roadY1 = screenHeight / 3;
                int roadY2 = 2 * screenHeight / 3;

                gameSurface.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                for (int i = 0; i < 10; i++) {
                    pedestrians.add(new Redestrian(randomX(screenWidth), roadY1, 2f, screenWidth, screenHeight));
                }

                // Создаем машины
                for (int i = 0; i < 5; i++) {
                    cars.add(new Car(randomX(screenWidth), roadY2, 3f));
                }

                startGameLoop();
            }
        });
    }

    private void startGameLoop() {
        running = true;
        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    update();
                    draw();
                    try {
                        Thread.sleep(16); // 60 FPS
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gameThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGameLoop();
    }

    private void stopGameLoop() {
        running = false;
        if (gameThread != null) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        for (Car car : cars) {
            car.update();
        }

        for (Redestrian pedestrian : pedestrians) {
            pedestrian.update(cars);
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.WHITE);
                for (Redestrian pedestrian : pedestrians) {
                    pedestrian.draw(canvas);
                }
                for (Car car : cars) {
                    car.draw(canvas);
                }
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private float randomX(int width) {
        return (float) (Math.random() * width);
    }

    private float randomY(int height) {
        return (float) (Math.random() * height);
    }
}

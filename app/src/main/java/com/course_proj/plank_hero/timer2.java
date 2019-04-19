package com.course_proj.plank_hero;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

public class timer2 extends Activity {
    private TextView countDownText;
    private Button countDownButton;
    private CountDownTimer timer;
    private long milliscd = 90000;
    private boolean timerRunning;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timer2);
        pb = (ProgressBar) findViewById(R.id.progressBarCircle);
        countDownText = findViewById(R.id.timer_content);
        countDownButton = findViewById(R.id.countDown_start);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
    }

    private void startStop() {
        if (timerRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(milliscd, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                milliscd = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
        countDownButton.setText("PAUSE");
    }

    private void stopTimer () {
        timer.cancel();
        timerRunning = false;
        countDownButton.setText("START");
    }

    private void updateTimer() {
        int minutes = (int) milliscd / 60000;
        int second = (int) milliscd % 60000 / 1000;
        String timeLeft = "" + minutes;
        timeLeft += ":";
        if (second < 10) timeLeft += "0";
        timeLeft += second;
        countDownText.setText(timeLeft);

    }
}

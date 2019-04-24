package com.course_proj.plank_hero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import java.io.*;

public class timer2 extends Activity {
    private TextView countDownText;
    private Button countDownButton;
    private CountDownTimer timer;
    private long milliscd;
    private boolean timerRunning;
    private ProgressBar pb;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timer2);
        pb = (ProgressBar) findViewById(R.id.progressBarCircle);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            this.time = extra.getString("timer");
        }
        this.milliscd = 1000 * Integer.valueOf(this.time);

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

    private void record_video(View v) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE)
    }

    private File getFile() {
        File folder = new File("sdcard/myfolder");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File videoFile = new File(folder, "plank_record.mp4");
        return videoFile;
    }
}

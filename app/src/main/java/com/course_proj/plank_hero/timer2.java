package com.course_proj.plank_hero;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class timer2 extends Activity implements SurfaceHolder.Callback {
    private TextView countDownText;
    private Button countDownButton;
    private CountDownTimer timer;
    private long milliscd;
    private boolean timerRunning;
    private ProgressBar pb;
    private String time;
    private static final String TAG = "Recorder";
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button finish;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timer2);
        if (!checkPermission()) {
            requestPermission();
        }

        if (!checkPermission2()) {
            requestPermission2();
        }


        mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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

        finish = (Button) findViewById(R.id.next);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timer2.this, VideoAlbum.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }
    private boolean checkPermission2() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);

    }

    private void requestPermission2() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(timer2.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void startStop() {
        if (timerRunning) {
            stopTimer();
            stopRecording();
        } else {
            startTimer();
            startRecording();
        }
    }

    private void startRecording() {
        Intent intent = new Intent(timer2.this, RecorderService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);
    }

    private void stopRecording() {
        stopService(new Intent(timer2.this, RecorderService.class));
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
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

}

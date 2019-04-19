package com.course_proj.plank_hero;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class plank_content extends Activity {
    private Button toTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.plank_content);

        toTimer = (Button) findViewById(R.id.set_timer);

        toTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetTimer();
            }
        });
    }

    private void openSetTimer() {
        Intent intent = new Intent(plank_content.this, timer2.class);
        startActivity(intent);
    }
}

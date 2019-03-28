package com.course_proj.plank_hero;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

public class plank_exercise extends Activity {

    private Button forearem_plank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.plank_exercise);
        forearem_plank = (Button) findViewById(R.id.forearem_plank);
        forearem_plank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForearmPlank();
            }
        });
    }

    public void openForearmPlank() {
        Intent intent = new Intent(plank_exercise.this, plank_content.class);
        startActivity(intent);
    }
}

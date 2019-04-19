package com.course_proj.plank_hero;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class settimer extends Activity {
    private EditText edtEditText;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timer);
        edtEditText = findViewById(R.id.input_time);
        start = (Button) findViewById(R.id.start_set_timer);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edtEditText.getText().toString();
                passValueToTimer(input);
            }
        });

    }

    private void passValueToTimer(String input) {
        Intent intent = new Intent(settimer.this, timer2.class);
        intent.putExtra("timer", input);
        startActivity(intent);
    }
}

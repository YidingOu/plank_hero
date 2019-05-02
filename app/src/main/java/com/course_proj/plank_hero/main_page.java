package com.course_proj.plank_hero;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Window;
import android.view.WindowManager;

public class main_page extends AppCompatActivity {

    private TextView mTextMessage;

    private Button start_planking;

    private Button start_battle;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_workout:
                    mTextMessage.setText("Workout");
                    return true;
                case R.id.navigation_challenge:
                    mTextMessage.setText("Challenge");
                    return true;
                case R.id.navigation_account:
                    mTextMessage.setText("Account");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_page);



        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(bmp);

        TextView greetingNext = (TextView) findViewById(R.id.greeting);
        String name = extras.getString("message_key");
        greetingNext.setText("Hello "+name);



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        start_planking = (Button) findViewById(R.id.button_start_planking);
        start_battle = (Button) findViewById(R.id.button_send_plank_challenge);
        start_planking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartPlanking();
            }
        });
        start_battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_page.this, start_battle.class);
                startActivity(intent);
            }
        });
    }

    public void openStartPlanking() {
        Intent intent = new Intent(main_page.this, plank_exercise.class);
        startActivity(intent);
    }
}

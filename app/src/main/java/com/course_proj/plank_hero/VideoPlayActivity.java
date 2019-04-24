package com.course_proj.plank_hero;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;
import android.net.Uri;

public class VideoPlayActivity extends AppCompatActivity {

    private VideoView mVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mVideo = findViewById(R.id.VideoView);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        mVideo.setVideoURI(videoUri);
        mVideo.start();
    }
}

package com.course_proj.plank_hero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;


public class start_battle extends Activity {

    private Button invite_battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.battle_2);
        invite_battle = (Button) findViewById(R.id.start_now);
        invite_battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInviteBattle();
            }
        });
    }

    public void openInviteBattle() {
        Intent intent = new Intent(start_battle.this, invite_ballte.class);
        startActivity(intent);
    }
}

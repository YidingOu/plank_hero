package com.course_proj.plank_hero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.app.Activity;


public class start_battle extends Activity {

    private Button invite_battle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        invite_battle = (Button) findViewById(R.id.start_battle_button);
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

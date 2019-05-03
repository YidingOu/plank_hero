package com.course_proj.plank_hero;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class replay extends AppCompatActivity {
    private Button setReminder;

    /** Retrieve data
     */
    private ListView listView;
    private FirebaseDatabase database;
    private  DatabaseReference myRef;
    private ArrayList<String> reminderInfo = new ArrayList<String>();

    /**
     * Pop up
     */
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.replay);

        /**
         * set content
         */





        start = (Button) findViewById(R.id.startNow);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainpage();
            }
        });


        /**
         * Set Reminder
         */
        setReminder = (Button) findViewById(R.id.set_reminder);
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReminder();
            }
        });

        /**
         * Set pop up
         */


    }

    public void openReminder() {
        Intent intent = new Intent(replay.this, reminder.class);
        startActivity(intent);
    }


//    public void showPopup() {
//
//        AlertDialog.Builder popup = new AlertDialog.Builder(replay.this);
//        popup.setTitle("Reminder");
//
//
//        popup
//                .setPositiveButton(
//                        android.R.string.yes,
//                        new DialogInterface
//                                .OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which)
//                            {
//
//                                openTimer();
//                            }
//                        });
//
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference().child("Reminder");
//        String cur = "Lower the hip!\nStrengthen Back!";
//
////        /**
////        if (myRef != null) {
////            cur = "who";
////        }
////         */
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren()) {
//                    reminderInfo.add(ds.getKey());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        for (String s: reminderInfo) {
//            cur = cur + s + "\t";
//        }
//
//        popup.setMessage(cur);
//
//        AlertDialog alertDialog = popup.create();
//        alertDialog.show();
//
//
//    }

    public void openMainpage() {
        Intent intent = new Intent(replay.this, plank_exercise.class);
        startActivity(intent);
    }
}

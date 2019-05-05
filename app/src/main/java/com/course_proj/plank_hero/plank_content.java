package com.course_proj.plank_hero;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class plank_content extends Activity {
    private Button toTimer;
    DatabaseReference databaseReference;
    PopupWindow popUp;
    LinearLayout layout;
    LinearLayout mainLayout;
    List<String> itemList;
    StringBuilder sb;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.plank_content);

        toTimer = (Button) findViewById(R.id.set_timer);
        itemList = new ArrayList<String>();

        sb = new StringBuilder();

        toTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetTimer();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();

        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);



    }

    private void openSetTimer() {
        openTimer();
        mAuth = FirebaseAuth.getInstance();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = mAuth.getUid();
                if (dataSnapshot.child(uid).hasChild("Reminder")) {
                    openPopup();
                } else{
                    openTimer();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void openPopup() {
        Intent intent = new Intent(plank_content.this, custompopup.class);
        startActivity(intent);
    }

    private void showPopup() {
        AlertDialog.Builder popup = new AlertDialog.Builder(plank_content.this);
        popup.setTitle("Reminder");

        popup
                .setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                openTimer();
                            }
                        });



//        /**
//        if (myRef != null) {
//            cur = "who";
//        }
//         */

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();

                for (DataSnapshot child: dataSnapshot.child("Reminder").getChildren()){
                    String message = child.getValue(String.class);
                    sb.append(message);

                    itemList.add("Remember to " + message);
                    Log.d("message", message);

                }
                /**
                 String message1 = dataSnapshot.child("Reminder").child("1").getValue(String.class);
                 Log.d("message1", message1);
                 String message2 = dataSnapshot.child("Reminder").child("2").getValue(String.class);
                 Log.d("message2", message2);
                 */


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });



        popup.setMessage(sb.toString());

        AlertDialog alertDialog = popup.create();
        alertDialog.show();


    }

    public void openTimer() {
        Intent intent = new Intent(plank_content.this, settimer.class);
        startActivity(intent);
    }
}

package com.course_proj.plank_hero;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reminder extends AppCompatActivity {

    CheckBox mlowerHipCheckBox;
    CheckBox mputForwardCheckBox;
    CheckBox mStrengthenThenCheckBox;
    CheckBox mLiftMyHipCheckBox;
    CheckBox mlowerMyHeadThirdCheckBox;
    Button saveButton;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.reminder);

        mlowerHipCheckBox = findViewById(R.id.lowerHipC);
        mputForwardCheckBox = findViewById(R.id.putForwardArmC);
        mStrengthenThenCheckBox = findViewById(R.id.strengthenBackC);
        mLiftMyHipCheckBox = findViewById(R.id.liftMyHipC);
        mlowerMyHeadThirdCheckBox = findViewById(R.id.lowerMyHeadC);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        saveButton = (Button) findViewById(R.id.saveChange);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = mAuth.getUid();
                DatabaseReference reminderDB = mDatabase.child(uid).child("Reminder");

                if(mlowerHipCheckBox.isChecked()) {
                    reminderDB.child("m1_lower_hip").setValue("Lower Hip");
                } else {
                    reminderDB.child("m1_lower_hip").setValue(null);

                }

                if(mputForwardCheckBox.isChecked()) {
                    reminderDB.child("m2_put_forward_arm").setValue("Put Forward Arm");
                } else{
                    reminderDB.child("m2_put_forward_arm").setValue(null);

                }

                if(mStrengthenThenCheckBox.isChecked()) {
                    reminderDB.child("m3_strengthhen_back").setValue("Strengthen Back");
                } else {
                    reminderDB.child("m3_strengthhen_back").setValue(null);

                }

                if(mLiftMyHipCheckBox.isChecked()) {
                    reminderDB.child("m4_lifthip").setValue("Lift Hip");
                } else {
                    reminderDB.child("m4_lifthip").setValue(null);

                }

                if(mlowerMyHeadThirdCheckBox.isChecked()) {
                    reminderDB.child("5").setValue("Lower Head");
                } else {
                    reminderDB.child("5").setValue(null);
                }

                openReplay();

            }
        });

    }

    public void openReplay() {
        Intent intent = new Intent(reminder.this, replay.class);
        startActivity(intent);
    }
}


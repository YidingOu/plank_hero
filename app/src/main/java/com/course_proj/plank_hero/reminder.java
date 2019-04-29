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
        setContentView(R.layout.fill_info);
        mlowerHipCheckBox = findViewById(R.id.lowerHipC);
        mputForwardCheckBox = findViewById(R.id.putForwardArmC);
        mStrengthenThenCheckBox = findViewById(R.id.strengthenBackC);
        mLiftMyHipCheckBox = findViewById(R.id.liftMyHipC);
        mlowerMyHeadThirdCheckBox = findViewById(R.id.lowerMyHeadC);
        saveButton = findViewById(R.id.saveChange);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reminderInfo = mAuth.getCurrentUser().getUid();
                DatabaseReference reminderDB = mDatabase.child(reminderInfo);

                if(mlowerHipCheckBox.isChecked()) {
                    reminderDB.child("1").setValue("Lower Hip");
                }

                if(mputForwardCheckBox.isChecked()) {
                    reminderDB.child("2").setValue("Put Forward Arm");
                }

                if(mStrengthenThenCheckBox.isChecked()) {
                    reminderDB.child("3").setValue("Strengthen Back");
                }

                if(mLiftMyHipCheckBox.isChecked()) {
                    reminderDB.child("4").setValue("Lift Hip");
                }

                if(mlowerMyHeadThirdCheckBox.isChecked()) {
                    reminderDB.child("5").setValue("Lower Head");
                }

                Intent interestIntent = new Intent(reminder.this, settimer.class);
                interestIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(interestIntent);
            }
        });

    }

}


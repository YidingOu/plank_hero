package com.course_proj.plank_hero;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class custompopup extends AppCompatActivity {

    ListView l1;
    ArrayAdapter<String> adapter;
    String[] default_item = new String[] {"1","2","3","4"};
    DatabaseReference databaseReference;
    List<String> itemList;
    Button close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.custompopup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        l1 = (ListView) findViewById(R.id.newListView);
        itemList = new ArrayList<String>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot child: dataSnapshot.child("Reminder").getChildren()){
                    String message = child.getValue(String.class);
                    itemList.add("Remember to " + message);
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

        for (String str:itemList) {
            Log.d("str",str);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        l1.setAdapter(arrayAdapter);
        close = (Button)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimer();
            }
        });

    }

    public void openTimer() {
        Intent intent = new Intent(custompopup.this, settimer.class);
        startActivity(intent);
    }
}

package com.course_proj.plank_hero;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;

public class fill_info extends AppCompatActivity {

    /* *************************************
     *              Upload Image           *
     ***************************************/

    private Button btnChoose;
    private Button btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    private Button mLogoutBtn;
    private Button mNext;
    private FirebaseAuth mAuth;
    private boolean flagmale = false;
    private boolean flagfemale = false;
    private RadioButton rdbtnMale;
    private RadioButton rdbtnFemale;
    private EditText height;
    private EditText weight;
    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fill_info);

        //height.setText(mEdit.getText().toString());
        //weight.setText(mEdit.getText().toString());
        //name.setText(mEdit.getText().toString());

        /* *************************************
         *              Upload Image                *
         ***************************************/

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        mLogoutBtn = (Button) findViewById(R.id.log_out);
        mNext = (Button) findViewById(R.id.next);
        imageView = (ImageView) findViewById(R.id.imageView2);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rdbtnMale = (RadioButton) findViewById(R.id.Male);
        rdbtnFemale = (RadioButton) findViewById(R.id.Female);

        height = (EditText) findViewById(R.id.inputHeight);
        weight = (EditText) findViewById(R.id.inputWeight);
        name = (EditText) findViewById(R.id.inputName);

        String nameString = height.getText().toString();
        showText(nameString);
        rdbtnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdbtnMale.isChecked()) {
                    if (!flagmale) {
                        rdbtnMale.setChecked(true);
                        rdbtnFemale.setChecked(false);
                        flagmale = true;
                        flagfemale = false;
                    } else {
                        flagmale = false;
                        rdbtnMale.setChecked(false);
                        rdbtnFemale.setChecked(false);
                    }
                }
            }
        });


        rdbtnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdbtnFemale.isChecked()) {
                    if (!flagfemale) {
                        rdbtnFemale.setChecked(true);
                        rdbtnMale.setChecked(false);
                        flagfemale = true;
                        flagmale = false;
                    } else {
                        flagfemale = false;
                        rdbtnFemale.setChecked(false);
                        rdbtnMale.setChecked(false);
                    }
                }
            }
        });
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        /* *************************************
         *              Log Out               *
         ***************************************/
        mAuth = FirebaseAuth.getInstance();


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                updateUI();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartMainPage();
            }
        });


    }

    public void showText(String text) {
        Toast.makeText(fill_info.this, text, Toast.LENGTH_SHORT).show();
    }

    public void openStartMainPage() {
        Intent intent = new Intent(fill_info.this, main_page.class);
        startActivity(intent);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        bitmap, 100, 100, false);
                imageView.setImageBitmap(resizedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(fill_info.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(fill_info.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        /**
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
         .requestEmail()
         .build();
         mGoogleApiClient = new GoogleApiClient.Builder(this)
         .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
         .build();
         mGoogleApiClient.connect();
         */

        if (currentUser == null) {
            updateUI();
        }
    }

    private void updateUI() {
        Toast.makeText(fill_info.this, "You are logged out", Toast.LENGTH_LONG).show();
        Intent accountIntent = new Intent(fill_info.this, MainActivity.class);
        startActivity(accountIntent);
        finish();
    }

    public void openStartPlanking() {
        Intent intent = new Intent(fill_info.this, MainActivity.class);

    }
}


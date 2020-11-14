package com.mrsoftbd.moneyadding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

   
    private EditText inputEmail, inputPassword,refferellink,name,phone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference databaseReference,dbRefer;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        //get firebase auth instance
        auth = FirebaseAuth.getInstance();



        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        refferellink = (EditText) findViewById(R.id.parentUser);
        inputPassword = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                             //  saverefferel();

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                     saverefferel();
                                    //setRefferel();
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void saverefferel() {

        final String userRefferel = refferellink.getText().toString().trim();
        final String userName = name.getText().toString().trim();
        final String userPhone = phone.getText().toString().trim();
        final String userPassword = inputPassword.getText().toString().trim();
        final String emailid = inputEmail.getText().toString().trim();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        dbRefer = FirebaseDatabase.getInstance().getReference("Referel");

        //get current user
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //firebaseUser.getEmail();

        final String accStatus = "Pending";

        try {
            UserModel myProfile = new UserModel(userName,userPhone,userPassword,accStatus);

            UserModel myRef = new UserModel(userRefferel);
            final String userKey = firebaseUser.getUid();

            Log.d("tuser",userKey);

            databaseReference.child(userKey).setValue(myProfile);

            dbRefer.child(userKey).setValue(myRef);

            Toast.makeText(SignupActivity.this, "Refferel Add Successfully", Toast.LENGTH_SHORT).show();

            final  String key = dbRefer.push().getKey();
            //////////////
            dbRefer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Boolean found;
                    String search = userRefferel;
                    String thisEmail = emailid;
                    String userKey = firebaseUser.getUid();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String parent = ds.getKey();

                            String movieName = ds.child("refferel").getValue(String.class);

                           if(found = movieName.contains(search)) {
                               UserModel myRef = new UserModel(emailid);
                               dbRefer.child(parent).child("Client").child(key).setValue(myRef);
                           }else {
                               Log.d("Faild", movieName + " / " + found);
                             

                           }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

                //////////
        }
        catch (Exception e){
            Toast.makeText(this, "Profile Update Field.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

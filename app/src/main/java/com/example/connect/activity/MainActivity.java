package com.example.connect.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //Login
    EditText inputEmailLogin, inputPasswordLogin;


    //Signup
    EditText inputEmailSignUp, inputPasswordSignUp, inputFullNameSignUp, inputVerifyPassword;

    //Firebase Auth
    private FirebaseAuth mAuth;


    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_login_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    startActivity(new Intent(MainActivity.this, ContentActivity.class));
                    return;
                }
                showLoginDialog();

            }
        });

        findViewById(R.id.btn_registar_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void showLoginDialog() {
        View customView = getLayoutInflater().inflate(R.layout.login_layout, null, false);

        inputEmailLogin = customView.findViewById(R.id.input_email_login);
        inputPasswordLogin = customView.findViewById(R.id.input_password_login);

        customView.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signin(inputEmailLogin.getText().toString(), inputPasswordLogin.getText().toString());

            }
        });


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        //alertBuilder.setTitle("Recarga");
        alertBuilder.setView(customView);
        AlertDialog alertDialog = alertBuilder.create();
        //saldoIntroduzido = Double.parseDouble(editTextValorRec.getText().toString());

        alertDialog.show();

    }

    void showSignUpDialog() {
        View customView = getLayoutInflater().inflate(R.layout.sign_up_layout, null, false);
        inputFullNameSignUp = customView.findViewById(R.id.input_full_name);
        inputEmailSignUp = customView.findViewById(R.id.input_email_signup);
        inputPasswordSignUp = customView.findViewById(R.id.input_password_signup);
        inputVerifyPassword = customView.findViewById(R.id.input_verify_password);

        customView.findViewById(R.id.btn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = inputPasswordSignUp.getText().toString();
                String passwordVerifyed = inputVerifyPassword.getText().toString();

                if (verifyPassword(password, passwordVerifyed) == true) {
                    signUp(inputEmailSignUp.getText().toString(), inputPasswordSignUp.getText().toString(), inputFullNameSignUp.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Certifique a palavra passe", Toast.LENGTH_SHORT).show();
                    inputVerifyPassword.setText("");

                }


            }
        });

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        //alertBuilder.setTitle("Recarga");
        alertBuilder.setView(customView);
        AlertDialog alertDialog = alertBuilder.create();
        //saldoIntroduzido = Double.parseDouble(editTextValorRec.getText().toString());

        alertDialog.show();

    }

    void signin(final String email, String password) {
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MainActivity", "signInWithEmail:success");
                            //Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, ContentActivity.class));
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            Toast.makeText(MainActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }


    void signUp(final String email, String password, final String name) {
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User currentUser = new User(name, email);
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest userProfileChangeRequest  = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            firebaseUser.updateProfile(userProfileChangeRequest);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();
                            myRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(currentUser);

                            // Write a message to the database


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            startActivity(new Intent(MainActivity.this, ContentActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    void showProgressDialog() {
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                pd.cancel();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2500);

        mAuth = FirebaseAuth.getInstance();


    }

    public boolean verifyPassword(String oldone, String newone) {

        if (oldone.equals(newone))
            return true;

        else return false;


    }

}


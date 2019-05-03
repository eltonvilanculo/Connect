package com.example.connect;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //Login
    EditText inputEmailLogin,inputPasswordLogin;


    //Signup
    EditText inputEmailSignUp,inputPasswordSignUp;

    //Firebase Auth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_login_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    void showLoginDialog(){
        View customView = getLayoutInflater().inflate(R.layout.login_layout, null, false);

        inputEmailLogin = customView.findViewById(R.id.input_email_login);
        inputPasswordLogin = customView.findViewById(R.id.input_password_login);

        customView.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              signin(inputEmailLogin.getText().toString(),inputPasswordLogin.getText().toString());

            }
        });





        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        //alertBuilder.setTitle("Recarga");
        alertBuilder.setView(customView);
        AlertDialog alertDialog = alertBuilder.create();
        //saldoIntroduzido = Double.parseDouble(editTextValorRec.getText().toString());

        alertDialog.show();

    }

    void showSignUpDialog(){
        View customView = getLayoutInflater().inflate(R.layout.sign_up_layout, null, false);

        inputEmailSignUp = customView.findViewById(R.id.input_email_signup);
        inputPasswordSignUp = customView.findViewById(R.id.input_password_signup);
        customView.findViewById(R.id.btn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(inputEmailSignUp.getText().toString(),inputPasswordSignUp.getText().toString());
            }
        });

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        //alertBuilder.setTitle("Recarga");
        alertBuilder.setView(customView);
        AlertDialog alertDialog = alertBuilder.create();
        //saldoIntroduzido = Double.parseDouble(editTextValorRec.getText().toString());

        alertDialog.show();

    }

    void signin(String email , String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MainActivity", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
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


    void signUp (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
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


    }


package com.cy.smartsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

public class Login_java_Activity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginBtn;


    private FirebaseAuth authProfile;
    ProgressBar progressBar;
    private ProgressBar mProgressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TextView mRegisterText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mUserName = findViewById(R.id.User_Name);
        mPassword = findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.LoginBtn);


        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mRegisterText = findViewById(R.id.register_text);
        mRegisterText.setOnClickListener(v -> {
            Intent intent = new Intent(Login_java_Activity.this, Register_javaActivity.class);
            startActivity(intent);
        });

        String userMail;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userMail= null;
            } else {
                userMail= extras.getString("usermail");
            }
        } else {
            userMail= (String) savedInstanceState.getSerializable("usermail");
        }

        String userPw;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userPw= null;
            } else {
                userPw= extras.getString("userPw");
            }
        } else {
            userPw= (String) savedInstanceState.getSerializable("userPw");
        }


        mUserName.setText(userMail);
        mPassword.setText(userPw);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();



        mLoginBtn.setOnClickListener(view -> {

            String TextEmail = mUserName.getText().toString();
            String  textPwd = mPassword.getText().toString();

            if (TextUtils.isEmpty(TextEmail)){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                mUserName.requestFocus();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(TextEmail).matches()) {
                Toast.makeText(this, "Please re- enter your email", Toast.LENGTH_SHORT).show();
                mUserName.setError("Valid email is required");
                mUserName.requestFocus();
            }else if (TextUtils.isEmpty(textPwd)) {
                Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                mPassword.setError("Password is required");
                mPassword.requestFocus();
            } else {

                mProgressBar.setVisibility(View.VISIBLE);
                loginUser(TextEmail ,textPwd);
            }

        });

    }

    private void loginUser(String textEmail, String textPwd) {

        authProfile.signInWithEmailAndPassword(textEmail,textPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent intent = new Intent(Login_java_Activity.this, Water0Activity.class);
                    startActivity(intent);


                        editor.putBoolean("flogin", false);
                        editor.apply();


                }else {
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e){
                        mUserName.setError("User does not exist or is no longer valid. Please register again.");
                        mUserName.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        mPassword.setError("check and re-enter.");
                        mPassword.requestFocus();

                    }catch (Exception e){
                        Log.e("tag",e.getMessage());
                    }


                   // TastyToast.makeText(getApplicationContext(), "Something went wrong!", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                }
                mProgressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isFirstOpen = sharedPreferences.getBoolean("flogin", true);
        if (isFirstOpen) {

        } else {

            if (authProfile.getCurrentUser() != null) {
                // Toast.makeText(this, "Alredy loged In", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_java_Activity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } else {
                //  Toast.makeText(this, "not  loged In", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
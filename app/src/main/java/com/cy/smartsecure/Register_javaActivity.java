 package com.cy.smartsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


 public class Register_javaActivity extends AppCompatActivity {

     private EditText mFirstName;
     private EditText mLastName;
     private EditText mNICNumber;
     private EditText mMobileNumber;
     private EditText mEmailAddress;
     private EditText mPassword;
     private Button mSubmit;


     StorageReference storageReference;
     FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstName = findViewById(R.id.First_Name);
        mLastName = findViewById(R.id.Last_Name);
        mNICNumber = findViewById(R.id.NIC_Number);
        mMobileNumber = findViewById(R.id.Mobile_Number);
        mEmailAddress = findViewById(R.id.Email_Address);
        mPassword = findViewById(R.id.Password);
        mSubmit = findViewById(R.id.submit);






        mSubmit.setOnClickListener(v -> {

            String FirstName = mFirstName.getText().toString();
            String LastName = mLastName.getText().toString();
            String NICNumber = mNICNumber.getText().toString();
            String MobileNumber = mMobileNumber.getText().toString();
            String EmailAddress = mEmailAddress.getText().toString();
            String Password = mPassword.getText().toString();



           registerUser(FirstName ,LastName, NICNumber, MobileNumber ,EmailAddress,Password);

            //Toast.makeText(this, FirstName, Toast.LENGTH_SHORT).show();


        });


    }

     private void registerUser(String firstName, String lastName, String nicNumber, String mobileNumber, String emailAddress, String password) {
         Toast.makeText(Register_javaActivity.this, emailAddress, Toast.LENGTH_SHORT).show();

         FirebaseAuth auth = FirebaseAuth.getInstance();
         auth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(Register_javaActivity.this,


                 new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {

                            // TastyToast.makeText(getApplicationContext(), "User Registered Successful ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                             Toast.makeText(Register_javaActivity.this, "User Registered Successful", Toast.LENGTH_SHORT).show();
                             FirebaseUser firebaseUser = auth.getCurrentUser();


                             ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(firstName, lastName, nicNumber, mobileNumber);
                             DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                             referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {

                                     if (task.isSuccessful()) {

                                         //TastyToast.makeText(getApplicationContext(), "User Registered Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                         Toast.makeText(Register_javaActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                                         // firebaseUser.sendEmailVerification();


                                         Intent intent = new Intent(Register_javaActivity.this, Water0Activity.class);
                                         intent.putExtra("usermail", mEmailAddress.getText().toString());
                                         intent.putExtra("userPw", mPassword.getText().toString());
                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                 | Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(intent);
                                         finish();


                                     } else {

                                         Toast.makeText(Register_javaActivity.this, "User Registered failed . Please try again", Toast.LENGTH_SHORT).show();
                                         //TastyToast.makeText(getApplicationContext(), "User Registered failed . Please try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                     }
                                     // mProgesBar.setVisibility(View.GONE);

                                 }
                             });

                         } else {
                             try {
                                 throw task.getException();
                             } catch (FirebaseAuthWeakPasswordException e) {
                                 mPassword.setError("Your Password is too weak");
                                 mPassword.requestFocus();

                             } catch (FirebaseAuthInvalidCredentialsException e) {
                                 mEmailAddress.setError("Your email is invalid or already in use");
                                 mEmailAddress.requestFocus();
                             } catch (FirebaseAuthUserCollisionException e) {
                                 mEmailAddress.setError("User is already registered with this email");
                                 mEmailAddress.requestFocus();
                             } catch (Exception e) {
                                 Log.e("elama", e.getMessage());

                             }
                             //mProgesBar.setVisibility(View.GONE);
                         }


                     }
                 });



     }
 }
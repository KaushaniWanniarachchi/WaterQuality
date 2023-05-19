package com.cy.smartsecure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Save_location extends AppCompatActivity {


    private EditText mHowToSave;
    private EditText mAddress;
    private EditText mLocation;
    private Button mSubmit;

    String Tds ;
    String Turbidity ;
    String WaterQuality ;


    private String genarate_token(int length) {
        char[] chars = "palahuttigegotayannabasilbasfdhilkapu56tukagpkkak55556652fhg3ew5adfsafDSADKHKJDOEKkkSjwjqDSD2345226E762888356111KKFKJFDK".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);}
        return stringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        mHowToSave = findViewById(R.id.how_to_save);
        mAddress = findViewById(R.id.address);
        mLocation = findViewById(R.id.location);
        mSubmit = findViewById(R.id.Submit);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
             Tds =(String) b.get("mTDSValue");
             Turbidity =(String) b.get("mTurbidityValue");
             WaterQuality =(String) b.get("mFinalRe");

            // Textv.setText(j);
        }



        mSubmit.setOnClickListener(v -> {


            String android_id = Settings.Secure.getString(Save_location.this.getContentResolver(), Settings.Secure.ANDROID_ID);


            ReadWriteSave writeUserDetails = new ReadWriteSave(android_id,Tds, Turbidity , WaterQuality,mHowToSave.getText().toString(),mAddress.getText().toString(),mLocation.getText().toString());
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Save");
            referenceProfile.child(genarate_token(5)).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        //TastyToast.makeText(getApplicationContext(), "User Registered Successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        Toast.makeText(Save_location.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                        // firebaseUser.sendEmailVerification();

                        Intent intent = new Intent(Save_location.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                    } else {

                        Toast.makeText(Save_location.this, "User Registered failed . Please try again", Toast.LENGTH_SHORT).show();
                        //TastyToast.makeText(getApplicationContext(), "User Registered failed . Please try again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                    // mProgesBar.setVisibility(View.GONE);

                }
            });

        });
    }


}
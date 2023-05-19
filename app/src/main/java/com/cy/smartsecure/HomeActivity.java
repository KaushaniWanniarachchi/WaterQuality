package com.cy.smartsecure;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import taimoor.sultani.sweetalert2.Sweetalert;

public class HomeActivity extends AppCompatActivity {

    private TextView mResult;
    private TextView mViewHistory;
    private LinearLayout mFinalreLayout;
    private LinearLayout mHistorylayout;
    private FirebaseAuth authProfile;
    private Sweetalert pDialog;
    String turbidity;
    String WaterQuality;
    String tds;
    private AppCompatButton mSaveLocationBtn;


    private EditText mFinalRe;
    private TextView mTDSValue;
    private TextView mTurbidityValue;

    private RecyclerView mRecyclerView;
    mAdapter mAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pDialog = new Sweetalert(this, Sweetalert.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        mResult = findViewById(R.id.Result);
        mViewHistory = findViewById(R.id.viewHistory);
        mFinalreLayout = findViewById(R.id.FinalreLayout);
        mHistorylayout = findViewById(R.id.Historylayout);
        mSaveLocationBtn = findViewById(R.id.save_location_btn);


        mFinalRe = findViewById(R.id.final_re);
        mTDSValue = findViewById(R.id.tds);
        mTurbidityValue = findViewById(R.id.turbidity);

        mResult.setOnClickListener(v -> {
            mHistorylayout.setVisibility(View.GONE);
            mFinalreLayout.setVisibility(View.VISIBLE);
            mViewHistory.setBackground(null);
            //mViewHistory.setTextColor(R.color.BlueColor);
            mResult.setBackgroundResource(R.drawable.switch_trcks);
            mViewHistory.setTextColor(getColor(R.color.BlueColor));
            mResult.setTextColor(Color.WHITE);
        });

        mViewHistory.setOnClickListener(v -> {
            mHistorylayout.setVisibility(View.VISIBLE);
            mFinalreLayout.setVisibility(View.GONE);
            mResult.setBackground(null);
            mViewHistory.setBackgroundResource(R.drawable.switch_trcks);
            mResult.setBackgroundResource(R.drawable.switch_tumbs);
            mViewHistory.setTextColor(Color.WHITE);
            mResult.setTextColor(getColor(R.color.BlueColor));
        });

        mSaveLocationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this,Save_location.class);
            intent.putExtra("mFinalRe", mFinalRe.getText().toString());
            intent.putExtra("mTDSValue", mTDSValue.getText().toString());
            intent.putExtra("mTurbidityValue", mTurbidityValue.getText().toString());
            startActivity(intent);

        });




        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<mModel> options =
                new FirebaseRecyclerOptions.Builder<mModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Save"),mModel.class)
                        .build();

        mAdapter = new mAdapter(options);
        mRecyclerView.setAdapter(mAdapter);




        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

       //showProfile(firebaseUser, pDialog);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();



      reference.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //model class assign
                    ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);


                    turbidity = readUserDetails.Turbidity;
                    WaterQuality = readUserDetails.WaterQuality;
                    tds = readUserDetails.Tds;



                    mFinalRe.setText(WaterQuality);
                    mTDSValue.setText(tds);
                    mTurbidityValue.setText(turbidity);

                   // Toast.makeText(HomeActivity.this, turbidity+"", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void showProfile(FirebaseUser firebaseUser, Sweetalert pDialog) {

        String userID = "Data";

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference();
        // mProgesBar.setVisibility(View.VISIBLE);
        pDialog.show();
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null){

                    turbidity = readUserDetails.Turbidity;
                    WaterQuality = readUserDetails.WaterQuality;
                    //tds = readUserDetails.tds;



                    mFinalRe.setText(turbidity);
                    mTDSValue.setText(tds);
                    mTurbidityValue.setText(WaterQuality);

                    Toast.makeText(HomeActivity.this, WaterQuality+"", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(HomeActivity.this, "Something went wrong! ", Toast.LENGTH_SHORT).show();
                }

                // mProgesBar.setVisibility(View.GONE);
                pDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Something went wrong! ", Toast.LENGTH_SHORT).show();
                // mProgesBar.setVisibility(View.GONE);
                pDialog.dismiss();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.startListening();
    }
}
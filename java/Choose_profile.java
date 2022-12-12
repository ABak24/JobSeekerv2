package com.example.jobseeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jobseeker.Authentication.Register;
import com.example.jobseeker.Employer.Employer_Create_profile1;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Choose_profile extends AppCompatActivity {

    Button worker;
    Button employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile);

        Intent i = getIntent();
        String email = i.getStringExtra("Email");
        String UserID = i.getStringExtra("UserID");
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
        DatabaseReference myRef = database.getReference().child("Users");
        //DatabaseReference workerRef = myRef.child("Workers").child(UserID);
        //DatabaseReference employerRef = myRef.child("Employers").child(UserID);


        worker= findViewById(R.id.btnWorker);
        employer= findViewById(R.id.btnEmployer);

        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_profile.this, Register.class));
                //workerRef.child("ID").setValue(UserID);
                //workerRef.child("Email").setValue(email);
            }
        });

        employer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choose_profile.this, register_employer.class));
                //employerRef.child("ID").setValue(UserID);
                //employerRef.child("Email").setValue(email);
            }
        });

    }
}
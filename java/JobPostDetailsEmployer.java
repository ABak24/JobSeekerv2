package com.example.jobseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jobseeker.Employer.EmployerFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobPostDetailsEmployer extends AppCompatActivity {



    TextView title, location, category, description, phoneNumber, email, companyName, aboutEmployer;

    FirebaseAuth employerAuth;

    MenuItem favorite;

    String jobTitle, jobLocation, jobCategory, jobDescription, jobPhoneNumber, jobEmail, employerDescription;

    ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_details_employer);



        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        title = findViewById(R.id.PostDetailTitleEmployer);
        location = findViewById(R.id.PostDetailLocationEmployer);
        category = findViewById(R.id.PostDetailCategoryEmployer);
        description = findViewById(R.id.PostDetailDescriptionEmployer);
        phoneNumber = findViewById(R.id.PostDetailPhoneEmployer);
        email = findViewById(R.id.PostDetailEmailEmployer);
        companyName = findViewById(R.id.PostDetailFirmNameEmployer);
        aboutEmployer = findViewById(R.id.PostDetailAboutEmployerEmployer);
        deleteButton = findViewById(R.id.imageDeleteJobPost);


        //Baza podataka

        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
        employerAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(getApplicationContext()));
        FirebaseUser firebaseUser = employerAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        // Dohvacamo referencu od oglasa , id smo dobili preko intenta iz Profile.java
        DatabaseReference mainRef = database.getReference();

        String userId = firebaseUser.getUid();

        DatabaseReference reference = mainRef.child("Employers").child(uid).child("JobPosts").child(id);


        DatabaseReference jobPostEmployer = mainRef.child("Employers").child(uid).child("JobPosts").child(id);

        DatabaseReference jobPost = mainRef.child("JobPosts").child(id);


        // Dodijelimo vrijednosti viewovima iz baze

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                title.setText(snapshot.child("JobTitle").getValue().toString());
                location.setText(snapshot.child("Location").getValue().toString());
                category.setText(snapshot.child("Category").getValue().toString());
                description.setText(snapshot.child("Description").getValue().toString());
                final String employerID = snapshot.child("EmployerID").getValue(String.class);

                DatabaseReference employerReference = database.getReference().child("Employers").child(uid);
                employerReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        jobPhoneNumber = snapshot.child("PhoneNumber").getValue(String.class);
                        jobEmail = snapshot.child("Email").getValue(String.class);
                        employerDescription = snapshot.child("Description").getValue(String.class);


                        companyName.setText(snapshot.child("CompanyName").getValue(String.class));
                        email.setText(snapshot.child("Email").getValue(String.class));
                        phoneNumber.setText(snapshot.child("PhoneNumber").getValue(String.class));
                        aboutEmployer.setText(employerDescription);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobPostEmployer.removeValue();
                jobPost.removeValue();

            }
        });

    }






}

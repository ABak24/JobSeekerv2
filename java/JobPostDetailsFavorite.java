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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobPostDetailsFavorite extends AppCompatActivity {

    TextView title, location, category, description, phoneNumber, email, companyName, aboutEmployer;

    FirebaseAuth mAuth;

    MenuItem favorite;

    String jobTitle, jobLocation, jobCategory, jobDescription, jobPhoneNumber, jobEmail, employerDescription;

    ImageButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_details_favorite);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        title = findViewById(R.id.PostDetailTitleFavorite);
        location = findViewById(R.id.PostDetailLocationFavorite);
        category = findViewById(R.id.PostDetailCategoryFavorite);
        description = findViewById(R.id.PostDetailDescriptionFavorite);
        phoneNumber = findViewById(R.id.PostDetailPhoneFavorite);
        email = findViewById(R.id.PostDetailEmailFavorite);
        favoriteButton = findViewById(R.id.favoriteButtonJobPostFavorite);
        companyName = findViewById(R.id.PostDetailFirmNameFavorite);
        aboutEmployer = findViewById(R.id.PostDetailAboutEmployerFavorite);

        //Baza podataka

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        // Dohvacamo referencu od oglasa , id smo dobili preko intenta iz Profile.java
        DatabaseReference reference = database.getReference().child("JobPosts").child(id);
        String userId = firebaseUser.getUid();


        DatabaseReference favoriteRef = database.getReference().child("Favorites").child(userId).child(id);

        // Dodijelimo vrijednosti viewovima iz baze

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                title.setText(snapshot.child("JobTitle").getValue().toString());
                location.setText(snapshot.child("Location").getValue().toString());
                category.setText(snapshot.child("Category").getValue().toString());
                description.setText(snapshot.child("Description").getValue().toString());
                final String employerID = snapshot.child("EmployerID").getValue(String.class);

                DatabaseReference employerReference = database.getReference().child("Employers").child(employerID);
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

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favoriteRef.removeValue();
                Toast.makeText(JobPostDetailsFavorite.this, "Removed from saved job posts", Toast.LENGTH_LONG).show();

                favoriteButton.setImageResource(R.drawable.favourite_empty);

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, title.getText().toString());
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        makePhoneCall();


    }

    private void makePhoneCall() {
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                callPhoneNumber();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(JobPostDetailsFavorite.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
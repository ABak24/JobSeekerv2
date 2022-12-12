package com.example.jobseeker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.jobseeker.Authentication.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobPostDetails extends AppCompatActivity {

    TextView title, location, category, description, phoneNumber, email, companyName, aboutEmployer;

    FirebaseAuth mAuth;

    MenuItem favorite;

    String jobTitle, jobLocation, jobCategory, jobDescription, jobPhoneNumber, jobEmail, employerDescription;

    ImageButton favoriteButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_details);



        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        title = findViewById(R.id.PostDetailTitle);
        location = findViewById(R.id.PostDetailLocation);
        category = findViewById(R.id.PostDetailCategory);
        description = findViewById(R.id.PostDetailDescription);
        phoneNumber = findViewById(R.id.PostDetailPhone);
        email = findViewById(R.id.PostDetailEmail);
        favoriteButton = findViewById(R.id.favoriteButtonJobPost);
        companyName = findViewById(R.id.PostDetailFirmName);
        aboutEmployer = findViewById(R.id.PostDetailAboutEmployer);

        //Baza podataka

        mAuth=FirebaseAuth.getInstance();
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
                favoriteRef.child("Email").setValue(jobEmail);
                favoriteRef.child("PhoneNumber").setValue(jobPhoneNumber);
                favoriteRef.child("JobTitle").setValue(title.getText().toString());
                favoriteRef.child("Location").setValue(location.getText().toString());
                favoriteRef.child("Description").setValue(description.getText().toString());
                favoriteRef.child("Category").setValue(category.getText().toString());
                favoriteRef.child("EmployerDescription").setValue(employerDescription);
                favoriteRef.child("FirmName").setValue(companyName.getText().toString());

                favoriteButton.setImageResource(R.drawable.favorite_full);
                Toast.makeText(getApplicationContext(),"Post saved.", Toast.LENGTH_LONG).show();

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

    private void makePhoneCall(){
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

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(JobPostDetails.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
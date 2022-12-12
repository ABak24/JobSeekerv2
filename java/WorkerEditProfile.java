package com.example.jobseeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jobseeker.WorkerUI.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class WorkerEditProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText workerProfession, workerLocation, workerPhoneNumber, workerWorkExperience, workerLanguages, workerEducation;
    TextView workerDateOfBirth, workerEmail, workerNameSurname;
    CircularImageView workerPhoto;
    Button saveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_edit_profile);


        workerEducation = findViewById(R.id.educationWorkerProfileEdit);
        workerLanguages = findViewById(R.id.workerLanguageProfileEdit);
        workerWorkExperience = findViewById(R.id.workerExperienceProfileEdit);
        workerPhoneNumber = findViewById(R.id.phoneWorkerProfileEdit);
        workerLocation = findViewById(R.id.workerLocationProfileEdit);
        workerProfession = findViewById(R.id.workerProfessionProfileEdit);
        workerPhoto = findViewById(R.id.workerEditPictureProfile);

        workerDateOfBirth = findViewById(R.id.dateWorkerProfileEdit);
        workerEmail = findViewById(R.id.workerEmailProfileEdit);
        workerNameSurname = findViewById(R.id.workerNameProfileEdit);

        saveProfile = findViewById(R.id.buttonSaveEditProfile);


        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
       FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();


        DatabaseReference myRef = database.getReference().child("Users").child("Workers").child(uid);
        DatabaseReference workExpRef = database.getReference().child("WorkExperience").child(uid);
        DatabaseReference profilePicRef = myRef.child("Picture");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                workerLocation.setText(snapshot.child("Location").getValue(String.class));
                workerDateOfBirth.setText(snapshot.child("Date of birth").getValue(String.class));
                workerPhoneNumber.setText(snapshot.child("Phone number").getValue(String.class));
                workerEducation.setText(snapshot.child("Education").getValue(String.class));
                workerNameSurname.setText(snapshot.child("First Name").getValue(String.class) + " "+ snapshot.child("Last name").getValue(String.class));
                workerProfession.setText(snapshot.child("Profession").getValue(String.class));
                workerLanguages.setText(snapshot.child("Language").getValue(String.class));
                // workerEmailProfile.setText(snapshot.child("Email").getValue(String.class));

                workerEmail.setText(snapshot.child("Email").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        workExpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                workerWorkExperience.setText(snapshot.getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilePicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);

                Picasso.get().load(link).into(workerPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profession = workerProfession.getText().toString().trim();
                String location = workerLocation.getText().toString().trim();
                String phoneNumber = workerPhoneNumber.getText().toString().trim();
                String workerExperience = workerWorkExperience.getText().toString().trim();
                String workerLanguage = workerLanguages.getText().toString().trim();
                String education =  workerEducation.getText().toString().trim();

                myRef.child("Location").setValue(location);
                myRef.child("Profession").setValue(profession);
                myRef.child("Phone number").setValue(phoneNumber);
                myRef.child("Language").setValue(workerLanguage);
                myRef.child("Education").setValue(education);
                workExpRef.setValue(workerExperience);

                Intent intent = new Intent(WorkerEditProfile.this, Menu_worker.class);
                startActivity(intent);

            }
        });

    }
}
package com.example.jobseeker.WorkerUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.jobseeker.Authentication.Login;
import com.example.jobseeker.Menu_worker;
import com.example.jobseeker.R;
import com.example.jobseeker.WorkerEditProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


public class Profile extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    TextView workerLocation_profile, dateWorkerProfile, phoneWorkerProfile, educationWorkerProfile, workerNameProfile, workerProfessionProfile;
    TextView workerExperienceProfile;
    TextView workerLanguageProfile;
    TextView workerEmailProfile;
    MenuItem logout;
    CircularImageView workerPicture;
    Button editProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        workerLocation_profile = view.findViewById(R.id.workerLocationProfile);
        dateWorkerProfile = view.findViewById(R.id.dateWorkerProfile);
        phoneWorkerProfile = view.findViewById(R.id.phoneWorkerProfile);
        educationWorkerProfile = view.findViewById(R.id.educationWorkerProfile);
        workerNameProfile = view.findViewById(R.id.workerNameProfile);
        workerProfessionProfile = view.findViewById(R.id.workerProfessionProfile);
        workerExperienceProfile = view.findViewById(R.id.workerExperienceProfile);
        workerLanguageProfile = view.findViewById(R.id.workerLanguageProfile);
        workerEmailProfile = view.findViewById(R.id.workerEmailProfile);
        workerPicture = view.findViewById(R.id.workerPictureProfile);
        editProfile = view.findViewById(R.id.buttonEditProfile);


        DatabaseReference myRef = database.getReference().child("Users").child("Workers").child(uid);
        DatabaseReference workExpRef = database.getReference().child("WorkExperience").child(uid);
        DatabaseReference profilePicRef = myRef.child("Picture");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    workerLocation_profile.setText(snapshot.child("Location").getValue(String.class));
                    dateWorkerProfile.setText(snapshot.child("Date of birth").getValue(String.class));
                    phoneWorkerProfile.setText(snapshot.child("Phone number").getValue(String.class));
                    educationWorkerProfile.setText(snapshot.child("Education").getValue(String.class));
                    workerNameProfile.setText(snapshot.child("First Name").getValue(String.class) + " "+ snapshot.child("Last name").getValue(String.class));
                    workerProfessionProfile.setText(snapshot.child("Profession").getValue(String.class));
                    workerLanguageProfile.setText(snapshot.child("Language").getValue(String.class));
                   // workerEmailProfile.setText(snapshot.child("Email").getValue(String.class));

                workerEmailProfile.setText(snapshot.child("Email").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        workExpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                workerExperienceProfile.setText(snapshot.getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilePicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);

                Picasso.get().load(link).into(workerPicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // na klik buttona ponavljamo proces registracije ali treba i povući sve podatke od prije
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), WorkerEditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout_item, menu);
        logout = menu.findItem(R.id.logoutMenu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}


package com.example.jobseeker.EmployerUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jobseeker.Authentication.Login;
import com.example.jobseeker.Employer.EmployerFirebase;
import com.example.jobseeker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


public class employer_fragment_profile extends Fragment {

    FirebaseAuth employerAuth;
    CircularImageView employerPicture;
    MenuItem logout;
    TextView firmName, owner, location, phoneNumber, email, description;
    String employerFirmName, employerOwner, employerPhoneNumber, employerEmail, employerDescription, employerLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employer_profile, container, false);

        String urlMainRef = getString(R.string.urlMainRef);
        //main baza podataka
        employerAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(getContext()));
        FirebaseDatabase databaseEmployer = FirebaseDatabase.getInstance(EmployerFirebase.getInstance(getContext()));
        FirebaseUser firebaseUser = employerAuth.getCurrentUser();
        String uid = firebaseUser.getUid();


        //find views

        firmName = view.findViewById(R.id.employerCompanyNameProfile);
        owner = view.findViewById(R.id.employerFirstLastNameProfile);
        location = view.findViewById(R.id.locationEmployerProfile);
        email = view.findViewById(R.id.emailEmployerProfile);
        phoneNumber = view.findViewById(R.id.employerPhoneNumberProfile);
        description = view.findViewById(R.id.aboutEmployerProfile);
        employerPicture = view.findViewById(R.id.employerPictureProfile);


        employerFirmName = firmName.getText().toString().trim();
        employerOwner = owner.getText().toString().trim();
        employerEmail = email.getText().toString().trim();
        employerLocation = location.getText().toString().trim();
        employerPhoneNumber = phoneNumber.getText().toString().trim();
        employerDescription = description.getText().toString().trim();



        DatabaseReference mainRef = FirebaseDatabase.getInstance(urlMainRef).getReference();
        DatabaseReference employerReference = mainRef.child("Employers").child(uid);
        DatabaseReference profilePicRef = employerReference.child("Picture");

        employerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //findViewIDs();
                //getEmployerDetails();
                firmName.setText(snapshot.child("CompanyName").getValue(String.class));
                owner.setText(snapshot.child("FirstName").getValue(String.class)+ " " + snapshot.child("LastName").getValue(String.class));
                location.setText(snapshot.child("Location").getValue(String.class));
                email.setText(snapshot.child("Email").getValue(String.class));
                phoneNumber.setText(snapshot.child("PhoneNumber").getValue(String.class));
                description.setText(snapshot.child("Description").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profilePicRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);

                Picasso.get().load(link).into(employerPicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
package com.example.jobseeker.Employer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobseeker.EmployerClass;
import com.example.jobseeker.EmployerProfilePicture;
import com.example.jobseeker.Menu_employer;
import com.example.jobseeker.R;
import com.example.jobseeker.Worker.Worker_CreateProfile1;
import com.example.jobseeker.WorkerAddPicture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Employer_Create_profile1 extends AppCompatActivity {

    Button next;
    FirebaseAuth mAuth;
    EditText firstName, lastName, companyName, phoneNumber, location, description;
    private String employerName, employerLastName, employerCompanyName, employerPhoneNumber, employerLocation, employerDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_create_profile1);

        String urlMainRef = getResources().getString(R.string.urlMainRef);
        //main baza podataka
        DatabaseReference mainRef = FirebaseDatabase.getInstance(urlMainRef).getReference();


//employer baza podataka
        mAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance(EmployerFirebase.getInstance(this));
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();

        DatabaseReference myRef = database.getReference().child("Employers").child(uid);






        next = findViewById(R.id.BtnNextEmployerProfile1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewbyIds();
                getEmployerDetails();

                HashMap<String, String> info = new HashMap<>();

                info.put("FirstName", employerName);
                info.put("LastName", employerLastName);
                // info.put("DateOfBirth", employer);
                info.put("CompanyName", employerCompanyName);
                info.put("Location", employerLocation);
                info.put("PhoneNumber", employerPhoneNumber);
                info.put("Email", email);
                info.put("Description", employerDescription);
                // myRef.setValue(info);

                if(TextUtils.isEmpty(employerName) || TextUtils.isEmpty(employerLastName) || TextUtils.isEmpty(employerCompanyName) || TextUtils.isEmpty(employerLocation) || TextUtils.isEmpty(employerDescription)){
                    makeToast("Please fill required information.");
                }else{
                    {
                        mainRef.child("Employers").child(uid).setValue(info);
                        makeToast("Added succesfully.");
                    }

                    Intent intent = new Intent(Employer_Create_profile1.this, EmployerProfilePicture.class);
                    startActivity(intent);

                }






            }
        });



    }


    private void findViewbyIds(){
        firstName=(EditText)findViewById(R.id.firstNameEmployer);
        firstName=(EditText)findViewById(R.id.firstNameEmployer);
        lastName =(EditText) findViewById(R.id.lastNameEmployer);
        companyName = (EditText) findViewById(R.id.companyNameEmployer);
        phoneNumber = (EditText)findViewById(R.id.phoneNumberEmployer);
        location = (EditText) findViewById(R.id.locationEmployer);
        description = (EditText)findViewById(R.id.descriptionEmployer);
    }

    private void getEmployerDetails(){
        employerName = firstName.getText().toString().trim();
        employerLastName = lastName.getText().toString().trim();
        employerCompanyName = companyName.getText().toString().trim();
        employerPhoneNumber = phoneNumber.getText().toString().trim();
        employerLocation = location.getText().toString().trim();
        employerDescription = description.getText().toString().trim();
    }
    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
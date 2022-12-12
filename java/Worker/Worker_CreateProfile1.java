package com.example.jobseeker.Worker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobseeker.R;
import com.example.jobseeker.WorkerAddPicture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class Worker_CreateProfile1 extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button next;
    private EditText firstName,lastName, location, phoneNumber, profession;
    private String workerName,workerLastName,workerLocation,workerPhoneNumber,workerProfession;
    public String dateOfBirth;
    public String datum;
    FirebaseUser firebaseUser;

    //novo
    FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_create_profile1);

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        initDatePicker();



        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.urlMainRef));
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();


       // String datum = dateButton.getText().toString();




        next = findViewById(R.id.BtnNextWorkerProfile1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //


                findViews();
                getWorkerDetails();

                String uid = firebaseUser.getUid();
                String email = firebaseUser.getEmail();
            //    String key = getIntent().getStringExtra("Key");




                DatabaseReference myRef = database.getReference().child("Users").child("Workers").child(uid);



                HashMap<String, String> info = new HashMap<>();

                info.put("Email", email);
                info.put("Date of birth", datum);
                info.put("First Name", workerName);
                info.put("Last name", workerLastName);
                info.put("Location", workerLocation);
                info.put("Phone number", workerPhoneNumber);
                info.put("Profession", workerProfession);

                if(TextUtils.isEmpty(workerName) || TextUtils.isEmpty(workerLastName) || TextUtils.isEmpty(workerLocation) || TextUtils.isEmpty(datum) || TextUtils.isEmpty(workerPhoneNumber) || TextUtils.isEmpty(workerProfession)){
                    makeToast("Please fill required information");
                }else{
                    {
                        myRef.setValue(info);
                        makeToast("Added successfully.");
                    }

                    Intent intent = new Intent(Worker_CreateProfile1.this, WorkerAddPicture.class);
                    startActivity(intent);

                }

            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Toast.makeText(Worker_CreateProfile1.this, date, Toast.LENGTH_SHORT).show();
                datum = dateButton.getText().toString();

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default
        return "Jan";
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    private void findViews() {
        firstName = (EditText)findViewById(R.id.firstName_worker);
        lastName = (EditText)findViewById(R.id.lastName_worker);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber_worker);
        location = (EditText)findViewById(R.id.workerLocation);
        profession = (EditText)findViewById(R.id.professionWorker);
    }

    private void getWorkerDetails(){
        workerName = firstName.getText().toString().trim();
        workerLastName = lastName.getText().toString().trim();
        workerLocation = location.getText().toString().trim();
        workerPhoneNumber = phoneNumber.getText().toString().trim();
        workerProfession = profession.getText().toString().trim();

    }
    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            firebaseUser.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                            }
                        }
                    });
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit, account will be deleted.", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
package com.example.jobseeker.Worker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobseeker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class worker_work_experience extends AppCompatActivity {

    Button add;
    AlertDialog dialog;
    LinearLayout layout;

    String s="";
    Button next;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_work_experience);

        add = findViewById(R.id.add);
        layout = findViewById(R.id.container);
        next = findViewById(R.id.btnNext);

        buildDialog();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(worker_work_experience.this, worker_education.class);
                startActivity(intent);
            }
        });
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.worker_new_experience, null);

        EditText name = view.findViewById(R.id.nameEdit);
        final EditText company = view.findViewById(R.id.CompanyID);
        final EditText duration = view.findViewById(R.id.durationOfWork);


        //baza
        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.urlMainRef));
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child("Users").child("Work Experience").child(uid);
        DatabaseReference novaRef = database.getReference().child("WorkExperience").child(uid);



        builder.setView(view);
        builder.setTitle("Enter position")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    private Object WorkerExperience;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(name.getText().toString())|| TextUtils.isEmpty(company.getText().toString()) || TextUtils.isEmpty(duration.getText().toString()) ) {
                            makeToast("Please fill missing information.");
                            name.requestFocus();
                        }
                        else{
                            addCard(name.getText().toString(), company.getText().toString(), duration.getText().toString());


                            String positionExperience = name.getText().toString().trim();
                            String durationExperience = duration.getText().toString().trim();
                            String companyExperience = company.getText().toString().trim();

                            DatabaseReference expRef = myRef.child(positionExperience);
                            WorkerExperience = new WorkerExperience(positionExperience, durationExperience, companyExperience);
                            expRef.setValue(WorkerExperience);


                            s += positionExperience + " - " + durationExperience + " - " + companyExperience + "\n";
                            novaRef.setValue(s);

                            name.setText("");
                            duration.setText("");
                            company.setText("");


                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }


    private void addCard(String name,String company,String duration) {
        final View view = getLayoutInflater().inflate(R.layout.worker_card_experience, null);

        TextView workingPeriod = view.findViewById(R.id.duration);
        TextView nameOfCompany = view.findViewById(R.id.nameOfCompany);
        TextView nameView = view.findViewById(R.id.work_position);
        Button delete = view.findViewById(R.id.btnDeleteWorkExperience);


        nameView.setText(name);
        nameOfCompany.setText(company);
        workingPeriod.setText(duration);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.urlMainRef));
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String uid = firebaseUser.getUid();
                DatabaseReference expRef = database.getReference().child("Users").child("Work Experience").child(uid).child(name);
                expRef.removeValue();
                layout.removeView(view);
            }
        });

        layout.addView(view);
    }
    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

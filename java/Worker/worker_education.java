package com.example.jobseeker.Worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobseeker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class worker_education extends AppCompatActivity {

    private EditText education;
    public String edu;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_education);





        Button next;
        next = findViewById(R.id.btnNextEducation);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText education = (EditText)findViewById(R.id.workerEducation);
                edu = education.getText().toString().trim();

                //Novo

                mAuth= FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.urlMainRef));
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String uid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child("Users").child("Workers").child(uid);

                if(TextUtils.isEmpty(edu)){
                    makeToast("Please fill required information.");
                    education.requestFocus();
                }else {
                        myRef.child("Education").setValue(edu);
                        makeToast("Added succesfully.");
                    Intent intent = new Intent(worker_education.this, worker_language_new.class);
                    startActivity(intent);
                    }



            }
        });
    }
    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
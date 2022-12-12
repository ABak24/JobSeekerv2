package com.example.jobseeker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobseeker.Employer.EmployerFirebase;
import com.example.jobseeker.Employer.Employer_Create_profile1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_employer extends AppCompatActivity {

    FirebaseAuth employerAuth;
    Button signIn;
    EditText username, password1;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employer);

        signIn = findViewById(R.id.signEmployer);
        username = findViewById(R.id.usernameEmployer);
        password1 = findViewById(R.id.passwordEmployer);
        employerAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(this));

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString().trim();
                String password= password1.getText().toString().trim();
                if(email.isEmpty())
                {
                    username.setError("Email is empty");
                    username.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    username.setError("Enter the valid email address");
                    username.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    password1.setError("Enter the password");
                    password1.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    password1.setError("Length of the password should be more than 6");
                    password1.requestFocus();
                    return;
                }
                employerAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(register_employer.this,"Continue with your registration.", Toast.LENGTH_SHORT).show();
                            //Novo ubaceno
                            String email = username.getText().toString().trim();
                            userID = employerAuth.getCurrentUser().getUid();
                            //HashMap<String, String> users = new HashMap<>();
                            //users.put("UserID", userID);
                            //users.put("Email", email);
                            //myRef.child(userID).push().setValue(users);

                            //String key = myRef.child(userID).getKey();
                            // do ovde



                            username.setText("");
                            password1.setText("");
                            Intent intent = new Intent(register_employer.this, Employer_Create_profile1.class);
                            intent.putExtra("Email", email);
                            intent.putExtra("UserID", userID);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(register_employer.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
package com.example.jobseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobseeker.Authentication.Login;
import com.example.jobseeker.Employer.EmployerFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class Employer_login extends AppCompatActivity {


    EditText username, passwordEmployer;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);



        username = findViewById(R.id.emailEmployer);
        passwordEmployer = findViewById(R.id.passwordEmployer);
        login  =  findViewById(R.id.btn_loginEmployer);


        mAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(this));

        login.setOnClickListener(v -> {
            String email= username.getText().toString().trim();
            //String password=password.getText().toString().trim();
            String password = passwordEmployer.getText().toString().trim();
            if(email.isEmpty())
            {
                username.setError("Email is empty");
                username.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                username.setError("Enter the valid email");
                username.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                passwordEmployer.setError("Password is empty");
                passwordEmployer.requestFocus();

                return;
            }
            if(password.length()<6)
            {
                passwordEmployer.setError("Length of password is more than 6");
                passwordEmployer.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    username.setText("");
                    passwordEmployer.setText("");
                    startActivity(new Intent(Employer_login.this, Menu_employer.class));
                }
                else
                {
                    Toast.makeText(Employer_login.this,
                            "Please Check Your login Credentials",
                            Toast.LENGTH_SHORT).show();
                }

            });
        });

}
    }



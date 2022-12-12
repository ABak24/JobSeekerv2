package com.example.jobseeker.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jobseeker.Choose_profile;
import com.example.jobseeker.Employer_login;
import com.example.jobseeker.Menu_worker;
import com.example.jobseeker.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText user_name, pass_word;
    private Button btn_login, btn_sign,employerLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        user_name= findViewById(R.id.email);
        pass_word= findViewById(R.id.password);
        btn_login =  findViewById(R.id.btn_login);
        btn_sign = findViewById(R.id.btn_signup);
        employerLogin = findViewById(R.id.employerLogin);

        mAuth=FirebaseAuth.getInstance();

        btn_login.setOnClickListener(v -> {
            String email= user_name.getText().toString().trim();
            String password=pass_word.getText().toString().trim();
            if(email.isEmpty())
            {
                user_name.setError("Email is empty");
                user_name.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                user_name.setError("Enter the valid email");
                user_name.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                pass_word.setError("Password is empty");
                pass_word.requestFocus();
                return;
            }
            if(password.length()<6)
            {
                pass_word.setError("Length of password is more than 6");
                pass_word.requestFocus();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    user_name.setText("");
                    pass_word.setText("");
                    startActivity(new Intent(Login.this, Menu_worker.class));
                }
                else
                {
                    Toast.makeText(Login.this,
                            "Please Check Your login Credentials",
                            Toast.LENGTH_SHORT).show();
                }

            });
        });
        btn_sign.setOnClickListener(v -> startActivity(new Intent(Login.this, Choose_profile.class )));

        employerLogin.setOnClickListener(v -> startActivity(new Intent(Login.this, Employer_login.class )));
    }



}
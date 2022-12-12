package com.example.jobseeker.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jobseeker.Choose_profile;
import com.example.jobseeker.R;
import com.example.jobseeker.Worker.Worker_CreateProfile1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    Button btn2_signup;
    EditText user_name, pass_word;
    FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_name=findViewById(R.id.username);
        pass_word=findViewById(R.id.password1);
        btn2_signup=findViewById(R.id.sign);

        // Database reference

        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
        DatabaseReference myRef = database.getReference().child("Users");

        // do ovde


        btn2_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_name.getText().toString().trim();
                String password= pass_word.getText().toString().trim();
                if(email.isEmpty())
                {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Enter the valid email address, this user already exists");
                    user_name.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass_word.setError("Enter the password");
                    pass_word.requestFocus();
                    return;
                }
                if(password.length()<8)
                {
                    pass_word.setError("Length of the password should be more than 8");
                    pass_word.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"Continue with your registration.", Toast.LENGTH_SHORT).show();
                            //Novo ubaceno
                           String email = user_name.getText().toString().trim();
                           userID = mAuth.getCurrentUser().getUid();


                            user_name.setText("");
                            pass_word.setText("");
                            Intent intent = new Intent(Register.this, Worker_CreateProfile1.class);
                            intent.putExtra("Email", email);
                            intent.putExtra("UserID", userID);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(Register.this,"You are not Registered! Try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
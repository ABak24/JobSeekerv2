package com.example.jobseeker.Worker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.jobseeker.Menu_worker;
import com.example.jobseeker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class worker_language_new extends AppCompatActivity {

    Button add, next;
    LinearLayout layout;
    AlertDialog dialog;
    FirebaseAuth mAuth;
    String jezik = "";

    //novo pokusaj priko spinnera
    List<String> Language_level= new ArrayList<>();
    private Object LanguageClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_language_new);
        //novo
        Language_level.add("Begginer");
        Language_level.add("Intermediate");
        Language_level.add("Advanced");
        Language_level.add("Native");
        //

        add = findViewById(R.id.addNewLanguage);
        next = findViewById(R.id.btnNextNewLanguage);
        layout = findViewById(R.id.containerLanguageNew);

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
                Intent intent = new Intent(worker_language_new.this, Menu_worker.class);
                startActivity(intent);
            }
        });

    }

    private void buildDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.worker_new_language, null);

        EditText language = view.findViewById(R.id.languageName);
        //final EditText languageLevel = view.findViewById(R.id.LanguageLevel);
        AppCompatSpinner spinner = view.findViewById(R.id.SpinnerLanguage);


        // novo dodatno


        language.setText("");


        //novo
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,Language_level);
        spinner.setAdapter(arrayAdapter);

        mAuth=FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child("Users").child("Workers").child(uid).child("Language");

        builder.setView(view);
        builder.setTitle("Enter language")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //private Object WorkerExperience;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nameLanguage = language.getText().toString().trim();
                        // String levelLanguage = languageLevel.getText().toString().trim();
                        String levelLanguage = String.valueOf(spinner.getSelectedItem());

                        if(TextUtils.isEmpty(nameLanguage)){
                            makeToast("Please enter language");
                            language.requestFocus();
                        }else{
                            // addCard(language.getText().toString(),languageLevel.getText().toString());
                            addCard(language.getText().toString(),spinner.getSelectedItem().toString());


                            // languageClass.setLevel(String.valueOf((languageList. get(spinnerLevel.getSelectedItemPosition()))));

                            DatabaseReference langRef = myRef.child(nameLanguage);
                            //LanguageClass = new LanguageClass(nameLanguage,levelLanguage);
                            jezik += nameLanguage + " -> " + levelLanguage + "\n";
                            makeToast("Added to languages");
                            myRef.setValue(jezik);

                            language.setText(" ");
                        }


                       //  langRef.setValue(levelLanguage);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }
    //private void addCard(String language,String languageLevel)
    private void addCard(String language,String languageLevel){
        final View view = getLayoutInflater().inflate(R.layout.worker_card_language, null);

        TextView languageName = view.findViewById(R.id.language);
        TextView languageLvl = view.findViewById(R.id.languageLvl);
        ImageView delete = view.findViewById(R.id.deleteLanguage);

        //languageLvl.setText(languageLevel);
        languageLvl.setText(languageLevel);
        languageName.setText(language);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth=FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String uid = firebaseUser.getUid();
                DatabaseReference langRef = database.getReference().child("Users").child("Workers").child(uid).child("Language").child(language);
                langRef.removeValue();

                layout.removeView(view);
            }
        });

        layout.addView(view);
    }

    private void makeToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

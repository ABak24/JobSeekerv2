package com.example.jobseeker.EmployerUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseeker.Employer.EmployerFirebase;
import com.example.jobseeker.JobPost;
import com.example.jobseeker.JobPostAdapterTest;
import com.example.jobseeker.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class employer_fragment_add_new extends Fragment {


    FirebaseUser firebaseUser;
    Button addNew;
    LinearLayout layout;
    AlertDialog dialog;
    FirebaseAuth mAuth,employerAuth;
    FirebaseDatabase database;
    private View EmployerNew;
    private RecyclerView EmployerNewView;
    JobPostAdapterTest adapter;
    private JobPostAdapterTest adapterTest;

    public  employer_fragment_add_new() {}

    List<String> categoryList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EmployerNew = inflater.inflate(R.layout.fragment_employer_add_new, container, false);

        addNew = (Button) EmployerNew.findViewById(R.id.addNewJob);

        categoryList.add("Administration");
        categoryList.add("Agriculture");
        categoryList.add("Economy");
        categoryList.add("Education");
        categoryList.add("Finance");
        categoryList.add("IT");
        categoryList.add("Medicine");
        categoryList.add("Services");
        categoryList.add("Tourism");
        categoryList.add("Transport");
        categoryList.add("Other");

        buildDialog();



        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });








        employerAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(getContext()));
        FirebaseUser firebaseUser = employerAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance(getContext().getString(R.string.urlMainRef));
        DatabaseReference reference = database.getReference().child("JobPosts");
        DatabaseReference nova = database.getReference().child("JobPosts").child(uid);

        EmployerNewView = (RecyclerView) EmployerNew.findViewById(R.id.employer_add_new_recycler);
        EmployerNewView.setHasFixedSize(true);
        EmployerNewView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference rootReference = database.getReference();
        DatabaseReference employerPostIDsReference = rootReference.child("Employers").child(uid).child("JobPosts");
        DatabaseReference jobPostsbyReference = rootReference.child("JobPosts");




//firebase querry je bio ovdje
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
          FirebaseRecyclerOptions<JobPost> options
                = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(employerPostIDsReference, JobPost.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
          adapter = new JobPostAdapterTest(options);
        // Connecting Adapter class with the Recycler view*/
        EmployerNewView.setAdapter(adapter);

        return EmployerNew;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();


    }


    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.employer_new_post_dialog, null);



        final EditText jobTitle = view.findViewById(R.id.jobTitleDialogID);
        final EditText jobLocation = view.findViewById(R.id.jobLocationDialogID);
        //final AppCompatSpinner spinner = view.findViewById(R.id.SpinnerLanguage);
        final AppCompatSpinner spinner = view.findViewById(R.id.jobCategoryDialogID);
        final EditText jobDescription = view.findViewById(R.id.jobDescriptionDialogID);


        ArrayAdapter<String> categoryAddapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryList);
        categoryAddapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(categoryAddapter);



        employerAuth = FirebaseAuth.getInstance(EmployerFirebase.getInstance(getContext()));
        FirebaseDatabase database = FirebaseDatabase.getInstance(EmployerFirebase.getInstance(getContext()));
        FirebaseUser firebaseUser = employerAuth.getCurrentUser();
        String uid = firebaseUser.getUid();


        builder.setView(view);
        builder.setTitle("Enter job title")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //private Object WorkerExperience;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String title = jobTitle.getText().toString().trim();
                        String location = jobLocation.getText().toString().trim();
                        String category = String.valueOf(spinner.getSelectedItem());
                        String description = jobDescription.getText().toString().trim();


                        //Adresa za bazu
                        String urlMainRef = getString(R.string.urlMainRef);

                        //upisujemo u Employer bazu id jobposta kojem korisniku pripada

                        DatabaseReference myRef = FirebaseDatabase.getInstance(urlMainRef).getReference().child("Employers").child(uid).child("JobPosts");
                        //Unique id koji smo generirali za oglas
                        String key = myRef.push().getKey();


                        DatabaseReference jobPostKey = myRef.child(key);
                        jobPostKey.child("JobTitle").setValue(title);
                        jobPostKey.child("Location").setValue(location);
                        jobPostKey.child("Category").setValue(category);
                        jobPostKey.child("Description").setValue(description);

                        //jobPostKey.child("EmployerID").setValue(uid);



                        //main baza podataka
                        DatabaseReference mainRef = FirebaseDatabase.getInstance(urlMainRef).getReference().child("JobPosts").child(key);

                        // Dodajemo oglas za posao u bazu JobPosts pod dijete id (key), isti id je zabiljezen
                        //kod employera pod dijete JobPosts
                        mainRef.child("JobTitle").setValue(title);
                        mainRef.child("Location").setValue(location);
                       mainRef.child("Category").setValue(category);
                       mainRef.child("Description").setValue(description);
                       mainRef.child("EmployerID").setValue(uid);
                        mainRef.child("Email").setValue(firebaseUser.getEmail());



                        jobTitle.setText("");
                        jobDescription.setText("");
                        jobLocation.setText("");

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }

    }

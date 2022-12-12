package com.example.jobseeker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class JobPostAdapterTest extends FirebaseRecyclerAdapter<JobPost, JobPostAdapterTest.personsViewholder> {

    public JobPostAdapterTest(
            @NonNull FirebaseRecyclerOptions<JobPost> options)
    {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull JobPostAdapterTest.personsViewholder holder, int position, @NonNull JobPost model) {

        ///
        DatabaseReference mainRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = userAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        JobPost jobPost = new JobPost(model.getJobTitle(), model.getCategory(), model.getLocation(), model.getDescription(), model.getEmployerId());

        DatabaseReference jobPostRef = mainRef.child("Employers").child(uid).child("JobPosts");

        DatabaseReference favouriteRef = mainRef.child("Favourites").child(uid);
        String postId = getRef(holder.getAbsoluteAdapterPosition()).getKey();


        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.title.setText(model.getJobTitle());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.category.setText(model.getCategory());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.location.setText(model.getLocation());

        holder.profilePicture.setImageResource(R.drawable.job_seeker);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getRef(holder.getAbsoluteAdapterPosition()).getKey();

                Intent newActivity = new Intent(view.getContext(),JobPostDetailsEmployer.class);
                newActivity.putExtra("id",id);


                view.getContext().startActivity(newActivity);
            }
        });



    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")


    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public JobPostAdapterTest.personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_row_with_delete, parent, false);
        return new JobPostAdapterTest.personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView title, category, location;
        CircularImageView profilePicture;

        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.jobTitleDelete);
            category = itemView.findViewById(R.id.jobCategoryDelete);
            location = itemView.findViewById(R.id.jobPostLocationDelete);
            profilePicture = itemView.findViewById(R.id.ImageProfileDelete);

        }


    }


}
package com.example.jobseeker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseeker.Authentication.Login;
import com.example.jobseeker.WorkerUI.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

public class JobPostAdapter extends FirebaseRecyclerAdapter<JobPost, JobPostAdapter.personsViewholder> {

    public JobPostAdapter(
            @NonNull FirebaseRecyclerOptions<JobPost> options)
    {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull JobPost model) {

        ///
        DatabaseReference mainRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = userAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        JobPost jobPost = new JobPost(model.getJobTitle(), model.getCategory(), model.getLocation(), model.getDescription(), model.getEmployerId());


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

                Intent newActivity = new Intent(view.getContext(),JobPostDetails.class);
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
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_row, parent, false);
        return new JobPostAdapter.personsViewholder(view);
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

            title = itemView.findViewById(R.id.jobTitle);
            category = itemView.findViewById(R.id.jobCategory);
            location = itemView.findViewById(R.id.jobPostLocation);
            profilePicture = itemView.findViewById(R.id.ImageProfile);
        }
    }


}
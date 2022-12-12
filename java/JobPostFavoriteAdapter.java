package com.example.jobseeker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

public class JobPostFavoriteAdapter extends FirebaseRecyclerAdapter<JobPost, JobPostFavoriteAdapter.personsViewholder> {

    public JobPostFavoriteAdapter(
            @NonNull FirebaseRecyclerOptions<JobPost> options)
    {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull JobPostFavoriteAdapter.personsViewholder holder, int position, @NonNull JobPost model) {

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

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postId = getRef(holder.getAbsoluteAdapterPosition()).getKey();
                 DatabaseReference deleteFavorite = favouriteRef.child(postId);
                 deleteFavorite.removeValue();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getRef(holder.getAbsoluteAdapterPosition()).getKey();

                Intent newActivity = new Intent(view.getContext(),JobPostDetailsFavorite.class);
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
    public JobPostFavoriteAdapter.personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_row_favorite, parent, false);
        return new JobPostFavoriteAdapter.personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder{
        TextView title, category, location;
        CircularImageView profilePicture;
        ImageButton favorite;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.jobTitleFavorite);
            category = itemView.findViewById(R.id.jobCategoryFavorite);
            location = itemView.findViewById(R.id.jobPostLocationFavorite);
            profilePicture = itemView.findViewById(R.id.ImageProfileFavorite);
            favorite = itemView.findViewById(R.id.jobPostFavorite);
        }


    }


}

package com.example.jobseeker.WorkerUI;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.jobseeker.JobPost;
import com.example.jobseeker.JobPostAdapter;
import com.example.jobseeker.JobPostFavoriteAdapter;
import com.example.jobseeker.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Saved_posts extends Fragment {

    private View WorkerPostsView;
    private RecyclerView WorkerPosts;
    JobPostFavoriteAdapter adapter;
    DatabaseReference reference;
    MenuItem menuItem;
    SearchView searchView;

    public Saved_posts() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        WorkerPostsView = inflater.inflate(R.layout.fragment_saved_posts, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance(getResources().getString(R.string.urlMainRef));

        reference = database.getReference() .child("JobPosts");
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        String userId = userAuth.getUid();
        DatabaseReference favorites = database.getReference().child("Favorites").child(userId);
        WorkerPosts = (RecyclerView) WorkerPostsView.findViewById(R.id.worker_saved_recycler);
        WorkerPosts.setLayoutManager(new LinearLayoutManager(getContext()));


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<JobPost> options = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(favorites, JobPost.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new JobPostFavoriteAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        WorkerPosts.setAdapter(adapter);



        return WorkerPostsView;

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onStart()
    {
        super.onStart();

        adapter.startListening();
        WorkerPosts.setAdapter(adapter);


    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}
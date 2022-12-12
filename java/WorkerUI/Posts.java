package com.example.jobseeker.WorkerUI;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobseeker.JobPost;
import com.example.jobseeker.JobPostAdapter;
import com.example.jobseeker.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Posts extends Fragment {


    private View WorkerPostsView;
    private RecyclerView WorkerPosts;
    JobPostAdapter adapter;
    DatabaseReference reference;
    MenuItem menuItem;
    SearchView searchView;
    JobPost jobPost;

    public Posts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        WorkerPostsView = inflater.inflate(R.layout.fragment_posts, container, false);



        FirebaseDatabase database = FirebaseDatabase.getInstance(getString(R.string.urlMainRef));
        reference = database.getReference() .child("JobPosts");

        WorkerPosts = (RecyclerView) WorkerPostsView.findViewById(R.id.worker_posts_recycler);
        WorkerPosts.setLayoutManager(new LinearLayoutManager(getContext()));


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<JobPost> options = new FirebaseRecyclerOptions.Builder<JobPost>()
               .setQuery(reference, JobPost.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
      adapter = new JobPostAdapter(options);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_item, menu);
        menuItem = menu.findItem(R.id.seachId);
        searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mySearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mySearch(s);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mySearch(String s) {

        FirebaseRecyclerOptions<JobPost> options
                = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(reference.orderByChild("JobTitle").startAt(s).endAt(s + "\uf8ff"), JobPost.class)
                .build();

        adapter = new JobPostAdapter(options);
        adapter.startListening();
        WorkerPosts.setAdapter(adapter);

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
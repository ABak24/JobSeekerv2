package com.example.jobseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu_employer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_employer);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewEmployer);
        NavController navController = Navigation.findNavController(this,R.id.fragment_employer);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.employer_fragment_add_new,R.id.employer_fragment_profile).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}
package com.example.jobseeker.Employer;


import android.content.Context;

import com.example.jobseeker.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class EmployerFirebase {
    private static FirebaseApp INSTANCE;



    public static FirebaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getSecondProject(context);
        }
        return INSTANCE;
    }

    private static FirebaseApp getSecondProject(Context context) {
        String setURL = context.getString(R.string.employerURL);

        FirebaseOptions options1 = new FirebaseOptions.Builder()
                .setApiKey(context.getString(R.string.employerApiKeyEmployer))
                .setApplicationId(context.getString(R.string.applicationIDEmployer))
                .setProjectId(context.getString(R.string.projectIDemployer))
                .setDatabaseUrl(setURL)
                //.setDatabaseURL(setURL)      // in case you need firebase Realtime database
                //setStorageBucket(...)    // in case you need firebase storage MySecondaryProject
                .build();



        FirebaseApp.initializeApp(context, options1, "employer");
        return FirebaseApp.getInstance("employer");
    }
}

package com.project2.banhangmypham;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class OrchidApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

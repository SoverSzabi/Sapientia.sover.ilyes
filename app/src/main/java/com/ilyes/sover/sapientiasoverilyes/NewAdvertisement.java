package com.ilyes.sover.sapientiasoverilyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAdvertisement extends AppCompatActivity {

    DatabaseReference databaseAdvertisments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_advertisment);

        databaseAdvertisments = FirebaseDatabase.getInstance().getReference("advertisments");
    }
}

package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_ADVID;
import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_DESCRIPTION;
import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_ID;
import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_LOCATION;
import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_TITLE;
import static com.ilyes.sover.sapientiasoverilyes.ListScreenActivity.EXTRA_URL;

public class DetailsActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private final String DATABASE_PATH = "advertisments";
    public static final String EXTRA_ITEM = "update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiser_detail);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        Intent intent = getIntent();

        final String imageURL = intent.getStringExtra(EXTRA_URL);
        final String title = intent.getStringExtra(EXTRA_TITLE);
        final String description = intent.getStringExtra(EXTRA_DESCRIPTION);
        final String location = intent.getStringExtra(EXTRA_LOCATION);

        ImageView imageView = findViewById(R.id.imageView);
        TextView titleText = findViewById(R.id.titleTextView);
        TextView descriptionText = findViewById(R.id.descriptionTextView);
        TextView locationText = findViewById(R.id.locationTextView);

        Glide.with(this).load(imageURL).into(imageView);
        titleText.setText(title);
        descriptionText.setText(description);
        locationText.setText(location);

        final String id           = intent.getStringExtra(EXTRA_ID);
        final String advertiserId = intent.getStringExtra(EXTRA_ADVID);

        final Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (advertiserId.equals(user.getUid())) {
                updateButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Advertisment advertisment = new Advertisment(id, advertiserId, title, description, location, imageURL);
                        deleteAdvertisment(advertisment);
                    }
                });

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailIntent = new Intent(DetailsActivity.this, NewAdvertisementActivity.class);

                        detailIntent.putExtra(EXTRA_ID, id);
                        detailIntent.putExtra(EXTRA_ADVID, advertiserId);
                        detailIntent.putExtra(EXTRA_TITLE, title);
                        detailIntent.putExtra(EXTRA_DESCRIPTION, description);
                        detailIntent.putExtra(EXTRA_LOCATION, location);
                        detailIntent.putExtra(EXTRA_URL, imageURL);
                        detailIntent.putExtra(EXTRA_ITEM, "update");

                        startActivity(detailIntent);
                    }
                });
            }

        }
    }

    public void deleteAdvertisment(Advertisment advertisment) {
        String key = advertisment.getId();
        advertisment.setIsDeleted(1);
        mDatabaseReference.child(key).setValue(advertisment);

        Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DetailsActivity.this, ListScreenActivity.class);
        intent.putExtra("id", 1);
        startActivity(intent);

    }
}

package com.ilyes.sover.sapientiasoverilyes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAdvertisement extends AppCompatActivity {

    EditText editTextTitle, editTextDescription, editTextLocation;
    Button buttonAdd;

    DatabaseReference databaseAdvertisments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_advertisment);

        databaseAdvertisments = FirebaseDatabase.getInstance().getReference("advertisments");

        editTextTitle       = findViewById(R.id.title);
        editTextDescription = findViewById(R.id.description);
        editTextLocation    = findViewById(R.id.location);

        buttonAdd   = (Button) findViewById(R.id.add_button);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick( View view )
           {
               addAdvertisment();
           }
        });
    }

    private void addAdvertisment() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        if (!TextUtils.isEmpty(title)) {
            String id = databaseAdvertisments.push().getKey();

            Advertisment advertisment = new Advertisment(id, "1", title, description, location);

            databaseAdvertisments.child(id).setValue(advertisment);
        }
        else {
            Toast.makeText(this, "Missing", Toast.LENGTH_LONG).show();
        }
    }
}

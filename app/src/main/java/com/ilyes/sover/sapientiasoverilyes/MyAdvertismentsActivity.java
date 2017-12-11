package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdvertismentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Advertisment> list;
    private AdvertismentAdapter adapter;

    private DatabaseReference database;

    private FirebaseUser loggedInUser;
    private String currentUserId;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_advertisments);

        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),NewAdvertisementActivity.class));
            }
        });

        loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId= loggedInUser.getUid();

        database = FirebaseDatabase.getInstance().getReference("advertisments");

        list = new ArrayList<>();

        recyclerView = findViewById(R.id.adv_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);

        adapter = new AdvertismentAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);

        updateList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void updateList() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot advertisment : dataSnapshot.getChildren()) {
                    String advertiserId = (String) advertisment.child("advertiserId").getValue();

                    if(advertiserId.equals(currentUserId)) {
                        list.add(dataSnapshot.getValue(Advertisment.class));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

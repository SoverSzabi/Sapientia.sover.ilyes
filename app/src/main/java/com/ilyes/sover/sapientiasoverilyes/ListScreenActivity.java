package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Advertisment> list;
    private AdvertismentAdapter adapter;

    private DatabaseReference database;

    private Button myAdvertisments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);

        Button myAdvertisments = findViewById(R.id.my_advertisments);

        myAdvertisments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MyAdvertismentsActivity.class));
            }
        });

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

    private void updateList() {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list.add(dataSnapshot.getValue(Advertisment.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Advertisment advertisment = dataSnapshot.getValue(Advertisment.class);

                int index = getItemIndex(advertisment);

                list.set(index, advertisment);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Advertisment advertisment = dataSnapshot.getValue(Advertisment.class);

                int index = getItemIndex(advertisment);

                list.remove(index);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getItemIndex(Advertisment advertisment) {
        int index = 0;

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).id.equals(advertisment.id)){
                index = i;
                break;
            }
        }

        return index;
    }
}

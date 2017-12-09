package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class ListScreenActivity extends AppCompatActivity {
    /*private static final String TAG = "ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "before");
        setContentView(R.layout.login);
        Log.d(TAG, "after");
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        // Action View

        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        switch(item.getItemId()){
            case R.id.searchId:
                startActivity(new Intent(this,SearchAdvertisement.class));
                return true;
            case R.id.addId:
                startActivity(new Intent(this,NewAdvertisement.class));
                return true;
            case R.id.profileId:
                startActivity(new Intent(this,ProfileActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    private RecyclerView recyclerView;
    private List<Advertisment> list;
    private AdvertismentAdapter adapter;


    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_screen);

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

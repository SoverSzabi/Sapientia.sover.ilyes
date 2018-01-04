package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.List;

public class ListScreenActivity extends AppCompatActivity implements AdvertismentAdapter.onItemClickListener {
    private RecyclerView recyclerView;
    private List<Advertisment> list;
    private AdvertismentAdapter adapter;

    private DatabaseReference database;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_ADVID = "adv_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_URL = "imagURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance().getReference("advertisments");
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.adv_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);

        adapter = new AdvertismentAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(ListScreenActivity.this);

        if (user != null) {
            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                int id = bundle.getInt("id");

                if (id == 2) {
                    if (user.getUid() != null) {
                        myAdvertisments();
                    } else {
                        Intent intent = new Intent(ListScreenActivity.this, EmailPasswordActivity.class);
                        ListScreenActivity.this.startActivity(intent);
                    }
                }
                else {
                    listAll();
                }
            }
            else {
                listAll();
            }
        }
        else {
            listAll();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        getMenuInflater().inflate(R.menu.mymenu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchId);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list:
                startActivity(new Intent(getBaseContext(), ListScreenActivity.class));
                break;
            case R.id.addId:
                if( user != null) {
                    if (user.getUid() != null) {
                        Intent intent = new Intent(ListScreenActivity.this, NewAdvertisementActivity.class);
                        intent.putExtra("id", 1);
                        startActivity(intent);
                    }
                }
                else {
                    startActivity(new Intent(getBaseContext(), EmailPasswordActivity.class));
                }
                break;
            case R.id.profileId:
                if( user != null) {
                    if (user.getUid() != null) {
                        startActivity(new Intent(getBaseContext(),ProfileActivity.class));
                    }
                }
                else {
                    startActivity(new Intent(getBaseContext(), EmailPasswordActivity.class));
                }
                break;
            case R.id.searchId:
                break;
            case R.id.logout:
                Intent intent = new Intent(ListScreenActivity.this, EmailPasswordActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
            case R.id.login:
                startActivity(new Intent(getBaseContext(), EmailPasswordActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    private void listAll() {
        Query query = database.orderByChild("isDeleted").equalTo(0);
        query.addChildEventListener(new ChildEventListener() {
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

    public void myAdvertisments() {
        Query query = database.orderByChild("advertiserId").equalTo(user.getUid());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Advertisment.class).getIsDeleted() == 0) {
                    list.add(dataSnapshot.getValue(Advertisment.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Advertisment.class).getIsDeleted() == 0) {
                    Advertisment advertisment = dataSnapshot.getValue(Advertisment.class);

                    int index = getItemIndex(advertisment);

                    list.set(index, advertisment);
                    adapter.notifyItemChanged(index);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Advertisment.class).getIsDeleted() == 0) {
                    Advertisment advertisment = dataSnapshot.getValue(Advertisment.class);

                    int index = getItemIndex(advertisment);

                    list.remove(index);
                    adapter.notifyItemChanged(index);
                }
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

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,DetailsActivity.class);
        Advertisment clickedItem = list.get(position);

        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_ADVID, clickedItem.getAdvertiserId());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_DESCRIPTION, clickedItem.getDescription());
        detailIntent.putExtra(EXTRA_LOCATION, clickedItem.getLocation());
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageURL());

        startActivity(detailIntent);
    }
}

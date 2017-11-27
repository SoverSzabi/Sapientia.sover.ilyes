package com.ilyes.sover.sapientiasoverilyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ListScreen extends AppCompatActivity {
    private static final String TAG = "Profile";
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
                startActivity(new Intent(this,Profile.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

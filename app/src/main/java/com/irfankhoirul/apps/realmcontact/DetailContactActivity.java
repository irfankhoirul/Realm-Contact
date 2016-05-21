package com.irfankhoirul.apps.realmcontact;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.irfankhoirul.apps.realmcontact.model.Contact;

public class DetailContactActivity extends AppCompatActivity {

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Gson gson = new Gson();
        Log.v("ContactString", getIntent().getStringExtra("contact"));
        contact = gson.fromJson(getIntent().getStringExtra("contact"), Contact.class);
        Log.v("Contact", contact.toString());
    }
}

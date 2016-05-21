package com.irfankhoirul.apps.realmcontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.irfankhoirul.apps.realmcontact.adapter.ContactAdapter;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class ContactListActivity extends AppCompatActivity {

    List<Contact> contacs = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactListActivity.this, AddContactActivity.class);
                startActivity(i);
            }
        });

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmQuery<Contact> query = realm.where(Contact.class);
        contacs = query.findAll();
        Log.v("ContacSize", String.valueOf(contacs.size()));

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        ContactAdapter mAdapter = new ContactAdapter(contacs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        assert rvContacts != null;
        rvContacts.setLayoutManager(mLayoutManager);
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        rvContacts.setAdapter(mAdapter);

    }

}

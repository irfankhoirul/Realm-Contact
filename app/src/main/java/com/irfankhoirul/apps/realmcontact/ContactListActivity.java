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

import com.google.gson.Gson;
import com.irfankhoirul.apps.realmcontact.adapter.ContactAdapter;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.itemInteractionListener {

    List<Contact> contacs = new ArrayList<>();

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactListActivity.this, AddContactActivity.class);
                startActivityForResult(i, 0);
            }
        });

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);

        RealmQuery<Contact> query = realm.where(Contact.class);
        contacs = query.findAll();
        Log.v("ContacSize", String.valueOf(contacs.size()));

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        ContactAdapter mAdapter = new ContactAdapter(contacs, ContactListActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        assert rvContacts != null;
        rvContacts.setLayoutManager(mLayoutManager);
        rvContacts.setItemAnimator(new DefaultItemAnimator());
        rvContacts.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }

    @Override
    public void onItemClickListener(int position) {
        Contact tmp = new Contact();
        tmp.setFirstName(contacs.get(position).getFirstName());
        tmp.setMiddleName(contacs.get(position).getMiddleName());
        tmp.setLastName(contacs.get(position).getLastName());

        tmp.setMobilePhone(contacs.get(position).getMobilePhone());
        tmp.setHomePhone(contacs.get(position).getHomePhone());
        tmp.setWorkPhone(contacs.get(position).getWorkPhone());

        tmp.setGroup(contacs.get(position).getGroup());

        tmp.setPersonalEmail(contacs.get(position).getPersonalEmail());
        tmp.setWorkEmail(contacs.get(position).getWorkEmail());

        tmp.setHomeAddress(contacs.get(position).getHomeAddress());
        tmp.setWorkAddress(contacs.get(position).getWorkAddress());

        tmp.setCompanyName(contacs.get(position).getCompanyName());
        tmp.setCompanyPosition(contacs.get(position).getCompanyPosition());

        tmp.setWebsite(contacs.get(position).getWebsite());

        Gson gson = new Gson();
        String stringJson = gson.toJson(tmp, Contact.class);
        Intent intent = new Intent(ContactListActivity.this, DetailContactActivity.class);
        intent.putExtra("contact", stringJson);
        startActivityForResult(intent, 10);
    }

    @Override
    public void onLongClickListener(int position) {

    }

}

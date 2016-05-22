package com.irfankhoirul.apps.realmcontact;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.irfankhoirul.apps.realmcontact.adapter.ContactAdapter;
import com.irfankhoirul.apps.realmcontact.adapter.UserActionListAdapter;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.itemInteractionListener {

    RealmResults<Contact> contacs;

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
        contacs = contacs.sort("firstName", Sort.ASCENDING);
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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    finish();
                    startActivity(getIntent());
                } else recreate();
            }
        }, 1);
    }

    @Override
    public void onItemClickListener(final int position) {
        final Contact contact = contacs.get(position);
        try {
            AlertDialog.Builder actionChoice = new AlertDialog.Builder(ContactListActivity.this);
            List<String> contactItem = new ArrayList<>();

            if (contact.getMobilePhone() != null && contact.getMobilePhone().length() > 0) {
                contactItem.add("Call Mobile");
                contactItem.add("Message");
            }

            if (contact.getHomePhone() != null && contact.getHomePhone().length() > 0) {
                contactItem.add("Call Home");
            }

            if (contact.getWorkPhone() != null && contact.getWorkPhone().length() > 0) {
                contactItem.add("Call Work Office");
            }

            if (contact.getPersonalEmail() != null && contact.getPersonalEmail().length() > 0) {
                contactItem.add("Send to Personal Email");
            }

            if (contact.getWorkEmail() != null && contact.getWorkEmail().length() > 0) {
                contactItem.add("Send to Work Email");
            }

            if (contact.getHomeAddress() != null && contact.getHomeAddress().length() > 0) {
                contactItem.add("Show Home on Map");
            }

            if (contact.getWorkAddress() != null && contact.getWorkAddress().length() > 0) {
                contactItem.add("Show Work Office on Map");
            }

            if (contact.getWebsite() != null && contact.getWebsite().length() > 0) {
                contactItem.add("Visit Website");
            }

            contactItem.add("Show Contact Detail");

            if (contactItem.size() > 0) {
                final UserActionListAdapter menuItem = new UserActionListAdapter(this,
                        R.layout.user_action_list, contactItem, 1);

                actionChoice.setAdapter(menuItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        switch (menuItem.getItem(which)) {

                            case "Message":
                                Uri uri = Uri.parse("smsto:" + contact.getMobilePhone());
                                intent = new Intent(Intent.ACTION_SENDTO, uri);
                                startActivity(intent);
                                break;
                            case "Call Mobile":
                                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                                        contact.getMobilePhone()));
                                startActivity(intent);
                                break;
                            case "Call Home":
                                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                                        contact.getHomePhone()));
                                startActivity(intent);
                                break;
                            case "Call Work Office":
                                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
                                        contact.getWorkPhone()));
                                startActivity(intent);
                                break;
                            case "Send to Personal Email":
                                intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:" + contact.getPersonalEmail()));
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getPersonalEmail()});
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            case "Send to Work Email":
                                intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:" + contact.getPersonalEmail()));
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact.getPersonalEmail()});
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                                break;
                            case "Show Home on Map":
                                intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("google.navigation:q=" + contact.getHomeAddress()));
                                startActivity(intent);
                                break;
                            case "Show Work Office on Map":
                                intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("google.navigation:q=" + contact.getWorkAddress()));
                                startActivity(intent);
                                break;
                            case "Visit Website":
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(contact.getWebsite()));
                                startActivity(intent);
                                break;
                            case "Show Contact Detail":
                                showContactDetail(position);
                                break;
                        }
                    }
                });
                actionChoice.create().show();
            } else {
                showContactDetail(position);
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void onLongClickListener(int position) {
        showContactDetail(position);
    }

    private void showContactDetail(int position) {
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
}
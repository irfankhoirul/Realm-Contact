package com.irfankhoirul.apps.realmcontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;

public class DetailContactActivity extends AppCompatActivity {

    Contact contact;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbar_layout;
    @BindView(R.id.llMobilePhone)
    LinearLayout llMobilePhone;
    @BindView(R.id.tvMobilePhone)
    TextView tvMobilePhone;
    @BindView(R.id.llHomePhone)
    LinearLayout llHomePhone;
    @BindView(R.id.tvHomePhone)
    TextView tvHomePhone;
    @BindView(R.id.llWorkPhone)
    LinearLayout llWorkPhone;
    @BindView(R.id.tvWorkPhone)
    TextView tvWorkPhone;
    @BindView(R.id.llPersonalEmail)
    LinearLayout llPersonalEmail;
    @BindView(R.id.tvPersonalEmail)
    TextView tvPersonalEmail;
    @BindView(R.id.llWorkEmail)
    LinearLayout llWorkEmail;
    @BindView(R.id.tvWorkEmail)
    TextView tvWorkEmail;
    @BindView(R.id.llHomeAddress)
    LinearLayout llHomeAddress;
    @BindView(R.id.tvHomeAddress)
    TextView tvHomeAddress;
    @BindView(R.id.llWorkAddress)
    LinearLayout llWorkAddress;
    @BindView(R.id.tvWorkAddress)
    TextView tvWorkAddress;
    @BindView(R.id.llCompanyName)
    LinearLayout llCompanyName;
    @BindView(R.id.tvCompanyName)
    TextView tvCompanyName;
    @BindView(R.id.llCompanyPosition)
    LinearLayout llCompanyPosition;
    @BindView(R.id.tvCompanyPosition)
    TextView tvCompanyPosition;
    @BindView(R.id.llWebsite)
    LinearLayout llWebsite;
    @BindView(R.id.tvWebsite)
    TextView tvWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Gson gson = new Gson();
        contact = gson.fromJson(getIntent().getStringExtra("contact"), Contact.class);

        showContactData();

    }

    private void showContactData() {
        String prefix = "";
        if (contact.getPrefix() != null)
            prefix = contact.getPrefix() + " ";
        setTitle(prefix + contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName());

        if (contact.getMobilePhone() != null && contact.getMobilePhone().length() > 0) {
            tvMobilePhone.setText(contact.getMobilePhone());
        } else {
            llMobilePhone.setVisibility(View.GONE);
        }

        if (contact.getHomePhone() != null && contact.getHomePhone().length() > 0) {
            tvHomePhone.setText(contact.getHomePhone());
        } else {
            llHomePhone.setVisibility(View.GONE);
        }

        if (contact.getWorkPhone() != null && contact.getWorkPhone().length() > 0) {
            tvWorkPhone.setText(contact.getWorkPhone());
        } else {
            llWorkPhone.setVisibility(View.GONE);
        }

        if (contact.getPersonalEmail() != null && contact.getPersonalEmail().length() > 0) {
            tvPersonalEmail.setText(contact.getPersonalEmail());
        } else {
            llPersonalEmail.setVisibility(View.GONE);
        }

        if (contact.getWorkEmail() != null && contact.getWorkEmail().length() > 0) {
            tvWorkEmail.setText(contact.getWorkEmail());
        } else {
            llWorkEmail.setVisibility(View.GONE);
        }

        if (contact.getHomeAddress() != null && contact.getHomeAddress().length() > 0) {
            tvHomeAddress.setText(contact.getHomeAddress());
        } else {
            llHomeAddress.setVisibility(View.GONE);
        }

        if (contact.getWorkAddress() != null && contact.getWorkAddress().length() > 0) {
            tvWorkAddress.setText(contact.getWorkAddress());
        } else {
            llWorkAddress.setVisibility(View.GONE);
        }

        if (contact.getCompanyName() != null && contact.getCompanyName().length() > 0) {
            tvCompanyName.setText(contact.getCompanyName());
        } else {
            llCompanyName.setVisibility(View.GONE);
        }

        if (contact.getCompanyPosition() != null && contact.getCompanyPosition().length() > 0) {
            tvCompanyPosition.setText(contact.getCompanyPosition());
        } else {
            llCompanyPosition.setVisibility(View.GONE);
        }

        if (contact.getWebsite() != null && contact.getWebsite().length() > 0) {
            tvWebsite.setText(contact.getWebsite());
        } else {
            llWebsite.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if (id == R.id.action_delete) {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
            Realm realm = Realm.getInstance(realmConfig);

            RealmQuery<Contact> query = realm.where(Contact.class);
            Contact result = query.findFirst();

            realm.beginTransaction();
            RealmObject.deleteFromRealm(result);
            realm.commitTransaction();

            finish();

        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(DetailContactActivity.this, AddContactActivity.class);
            intent.putExtra("contact", getIntent().getStringExtra("contact"));
            startActivity(intent);
            finish();
        } else if (id == R.id.action_share) {

        }

        return true;
    }
}

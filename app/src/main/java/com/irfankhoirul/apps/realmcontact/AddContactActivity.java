package com.irfankhoirul.apps.realmcontact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.spPrefix)
    Spinner spPrefix;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etMiddleName)
    EditText etMiddleName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.ivPhoto)

    ImageView ivPhoto;
    @BindView(R.id.btChangePhoto)
    Button btChangePhoto;
    @BindView(R.id.etMobilePhone)

    EditText etMobilePhone;
    @BindView(R.id.etHomePhone)
    EditText etHomePhone;
    @BindView(R.id.etWorkPhone)
    EditText etWorkPhone;

    @BindView(R.id.spGroup)
    Spinner spGroup;

    @BindView(R.id.etPersonalEmail)
    EditText etPersonalEmail;
    @BindView(R.id.etWorkEmail)
    EditText etWorkEmail;

    @BindView(R.id.etHomeAddress)
    EditText etHomeAddress;
    @BindView(R.id.etWorkAddress)
    EditText etWorkAddress;

    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.etCompanyPosition)
    EditText etCompanyPosition;

    @BindView(R.id.etWebsite)
    EditText etWebsite;

    private List<String> groups = new ArrayList<>();
    private List<String> prefixs = new ArrayList<>();

    private Contact oldContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        setTitle("Add New Contact");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        groups.add("no group");
        groups.add("Family");
        groups.add("Friend");
        groups.add("Business");
        groups.add("Emergency");
        groups.add("Other");
        ArrayAdapter<String> groupsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGroup.setAdapter(groupsAdapter);

        prefixs.add("no prefix");
        prefixs.add("Mr.");
        prefixs.add("Mrs.");
        prefixs.add("Ms.");
        ArrayAdapter<String> prefixsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, prefixs);
        prefixsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrefix.setAdapter(prefixsAdapter);

        if (getIntent().getStringExtra("contact") != null) {
            Gson gson = new Gson();
            oldContact = gson.fromJson(getIntent().getStringExtra("contact"), Contact.class);
            showContactData(oldContact);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_save) {
            saveContact();
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void saveContact() {
        Contact newContact = new Contact();
        newContact.setFirstName(etFirstName.getText().toString());
        newContact.setMiddleName(etMiddleName.getText().toString());
        newContact.setLastName(etLastName.getText().toString());

        newContact.setMobilePhone(etMobilePhone.getText().toString());
        newContact.setHomePhone(etHomePhone.getText().toString());
        newContact.setWorkPhone(etWorkPhone.getText().toString());

        newContact.setGroup(groups.get(spGroup.getSelectedItemPosition()));

        newContact.setPersonalEmail(etPersonalEmail.getText().toString());
        newContact.setWorkEmail(etWorkEmail.getText().toString());

        newContact.setHomeAddress(etHomeAddress.getText().toString());
        newContact.setWorkAddress(etWorkAddress.getText().toString());

        newContact.setCompanyName(etCompanyName.getText().toString());
        newContact.setCompanyPosition(etCompanyPosition.getText().toString());

        newContact.setWebsite(etWebsite.getText().toString());

        if (getIntent().getStringExtra("contact") != null) {
            updateContact(newContact, oldContact);
        } else {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(AddContactActivity.this).build();
            Realm realm = Realm.getInstance(realmConfig);
            realm.beginTransaction();
            realm.copyToRealm(newContact);
            realm.commitTransaction();
        }

        finish();
    }

    private void showContactData(Contact contact) {
        etFirstName.setText(contact.getFirstName());
        etMiddleName.setText(contact.getMiddleName());
        etLastName.setText(contact.getLastName());

        etMobilePhone.setText(contact.getMobilePhone());
        etHomePhone.setText(contact.getHomePhone());
        etWorkPhone.setText(contact.getWorkPhone());

        try {
            spGroup.setSelection(groups.indexOf(contact.getGroup()));
        } catch (Exception e) {
            spGroup.setSelection(0);
        }

        etPersonalEmail.setText(contact.getPersonalEmail());
        etWorkEmail.setText(contact.getWorkEmail());

        etHomeAddress.setText(contact.getHomeAddress());
        etWorkAddress.setText(contact.getWorkAddress());

        etCompanyName.setText(contact.getCompanyName());
        etCompanyPosition.setText(contact.getCompanyPosition());

        etWebsite.setText(contact.getWebsite());
    }

    private void updateContact(Contact newContact, Contact oldContact) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(AddContactActivity.this).build();
        Realm realm = Realm.getInstance(realmConfig);
        Contact contactToEdit = realm.where(Contact.class)
                .equalTo("firstName", oldContact.getFirstName())
                .equalTo("middleName", oldContact.getMiddleName())
                .equalTo("lastName", oldContact.getLastName()).findFirst();

        realm.beginTransaction();
        contactToEdit.setPrefix(newContact.getPrefix());
        contactToEdit.setFirstName(newContact.getFirstName());
        contactToEdit.setMiddleName(newContact.getMiddleName());
        contactToEdit.setLastName(newContact.getLastName());
        contactToEdit.setMobilePhone(newContact.getMobilePhone());
        contactToEdit.setHomePhone(newContact.getHomePhone());
        contactToEdit.setWorkPhone(newContact.getWorkPhone());
        contactToEdit.setPersonalEmail(newContact.getWorkEmail());
        contactToEdit.setWorkEmail(newContact.getWorkEmail());
        contactToEdit.setHomeAddress(newContact.getHomeAddress());
        contactToEdit.setWorkAddress(newContact.getWorkAddress());
        contactToEdit.setCompanyName(newContact.getCompanyName());
        contactToEdit.setCompanyPosition(newContact.getCompanyPosition());
        contactToEdit.setWebsite(newContact.getWebsite());
        contactToEdit.setPhotoUri(newContact.getPhotoUri());
        contactToEdit.setWebsite(newContact.getWebsite());

        realm.commitTransaction();

        finish();
    }

}

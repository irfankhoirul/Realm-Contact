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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);

        setTitle("Add New Contact");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

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
        Contact contact = new Contact();
        contact.setFirstName(etFirstName.getText().toString());
        contact.setMiddleName(etMiddleName.getText().toString());
        contact.setLastName(etLastName.getText().toString());

        contact.setMobilePhone(etMobilePhone.getText().toString());
        contact.setHomePhone(etHomePhone.getText().toString());
        contact.setWorkPhone(etWorkPhone.getText().toString());

        contact.setGroup(groups.get(spGroup.getSelectedItemPosition()));

        contact.setPersonalEmail(etPersonalEmail.getText().toString());
        contact.setWorkEmail(etWorkEmail.getText().toString());

        contact.setHomeAddress(etHomeAddress.getText().toString());
        contact.setWorkAddress(etWorkAddress.getText().toString());

        contact.setCompanyName(etCompanyName.getText().toString());
        contact.setCompanyPosition(etCompanyPosition.getText().toString());

        contact.setWebsite(etWebsite.getText().toString());

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(AddContactActivity.this).build();
        Realm realm = Realm.getInstance(realmConfig);
        realm.beginTransaction();
        realm.copyToRealm(contact);
        realm.commitTransaction();

        finish();
    }
}

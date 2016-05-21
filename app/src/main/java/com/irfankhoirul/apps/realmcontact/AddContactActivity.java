package com.irfankhoirul.apps.realmcontact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.irfankhoirul.apps.realmcontact.model.Contact;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddContactActivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail, etAddress;
    Button btSaveContact;

    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        btSaveContact = (Button) findViewById(R.id.btSaveContact);

        btSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                contact.setName(etName.getText().toString());
                contact.setPhone(etPhone.getText().toString());
                contact.setEmail(etEmail.getText().toString());
                contact.setAddress(etAddress.getText().toString());

                RealmConfiguration realmConfig = new RealmConfiguration.Builder(AddContactActivity.this).build();
                Realm realm = Realm.getInstance(realmConfig);

                realm.beginTransaction();
                realm.copyToRealm(contact);
                realm.commitTransaction();

                finish();
            }
        });
    }
}

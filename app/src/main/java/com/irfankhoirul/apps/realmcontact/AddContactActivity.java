package com.irfankhoirul.apps.realmcontact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.irfankhoirul.apps.realmcontact.model.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.btSaveContact)
    Button btSaveContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

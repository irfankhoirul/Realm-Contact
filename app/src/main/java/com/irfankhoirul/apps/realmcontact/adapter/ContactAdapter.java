package com.irfankhoirul.apps.realmcontact.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irfankhoirul.apps.realmcontact.R;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.List;

/**
 * Created by Irfan Khoirul on 21/05/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final Contact contact = contacts.get(position);

        char initial = contact.getName().charAt(0);
        holder.tvInitial.setText(Character.toString(initial));
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
        holder.tvEmail.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView tvInitial, tvName, tvPhone, tvEmail;

        public ContactHolder(View view) {
            super(view);
            tvInitial = (TextView) view.findViewById(R.id.tvInitial);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPhone = (TextView) view.findViewById(R.id.tvPhone);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        }
    }
}

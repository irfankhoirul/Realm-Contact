package com.irfankhoirul.apps.realmcontact.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.irfankhoirul.apps.realmcontact.ContactListActivity;
import com.irfankhoirul.apps.realmcontact.R;
import com.irfankhoirul.apps.realmcontact.model.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Irfan Khoirul on 21/05/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts;
    itemInteractionListener mListener;

    public interface itemInteractionListener {
        void onItemClickListener(int position);

        void onLongClickListener(int position);
    }

    public ContactAdapter(List<Contact> contacts, ContactListActivity activity) {
        this.contacts = contacts;
        this.mListener = activity;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int position) {
        final Contact contact = contacts.get(position);

        char initial;
        if (contact.getFirstName() != null && contact.getFirstName().length() > 0) {
            initial = contact.getFirstName().charAt(0);
        } else {
            initial = '?';
        }
        holder.tvInitial.setText(Character.toString(initial));
        String prefix = "";
        if (contact.getPrefix() != null && contact.getPrefix().length() > 0) {
            prefix = contact.getPrefix() + " ";
        }
        holder.tvName.setText(prefix + contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName());
        holder.tvPhone.setText(contact.getMobilePhone());
        holder.tvEmail.setText(contact.getPersonalEmail());

        holder.llItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(position);
            }
        });

        holder.llItemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onLongClickListener(position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.llItemContainer)
        LinearLayout llItemContainer;
        @BindView(R.id.tvInitial)
        TextView tvInitial;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvEmail)
        TextView tvEmail;

        public ContactHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

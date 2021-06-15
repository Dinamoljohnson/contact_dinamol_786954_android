package com.example.contact_dinamol_786954_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    LayoutInflater mInflator;
    List<Contact> contactList;

    public ContactListAdapter(Context context, List<Contact> contactList) {
        this.mInflator = LayoutInflater.from(context);
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contactView = mInflator.inflate(R.layout.activity_main, parent, false);
        return new ContactViewHolder(contactView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = this.contactList.get(position);
        holder.textViewFirstName.setText(contact.getFirstName());
        holder.textViewLastName.setText(contact.getLastName());
        holder.textViewEmail.setText(contact.getEmail());
        holder.textViewPhoneNumber.setText(contact.getPhoneNumber());
        holder.textViewAddress.setText(contact.getAddress());
    }

    @Override
    public int getItemCount() {
        return this.contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        ContactListAdapter contactListAdapter;
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewEmail;
        TextView textViewPhoneNumber;
        TextView textViewAddress;

        public ContactViewHolder(@NonNull View itemView,
                                 ContactListAdapter contactListAdapter) {
            super(itemView);
            this.textViewFirstName = itemView.findViewById(R.id.etFirstName);
            this.textViewLastName = itemView.findViewById(R.id.etLastName);
            this.textViewEmail = itemView.findViewById(R.id.etEmail);
            this.textViewPhoneNumber = itemView.findViewById(R.id.etPhoneNumber);
            this.textViewAddress = itemView.findViewById(R.id.etAddress);

            this.contactListAdapter = contactListAdapter;
        }
    }

}




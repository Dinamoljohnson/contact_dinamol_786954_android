package com.example.contact_dinamol_786954_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> implements Filterable {

    LayoutInflater mInflator;
    List<Contact> contactList;
    private List<Contact> contactListFull;

    public ContactListAdapter(Context context, List<Contact> contactList) {
        this.mInflator = LayoutInflater.from(context);
        this.contactList = contactList;
        this.contactListFull = new ArrayList<>(contactList);
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



    @Override
    public Filter getFilter() {
        return contactFilter;
    }
    Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact item : contactListFull) {
                    if (item.getFirstName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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




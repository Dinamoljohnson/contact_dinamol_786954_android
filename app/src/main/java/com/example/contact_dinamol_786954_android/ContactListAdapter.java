package com.example.contact_dinamol_786954_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<Contact> contactList;
    private Context context;
    private OnContactLongPressListener onContactLongPressListener;

    public ContactListAdapter(List<Contact> contactList, Context context, OnContactLongPressListener onContactLongPressListener) {
        this.contactList = contactList;
        this.context = context;
        this.onContactLongPressListener = onContactLongPressListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }
    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Contact> filterList) {
        // add filtered list in adapter list
        contactList = filterList;
        // notify adapter changes in list
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        // Set data on recycler row according to position
        holder.txtFirstName.setText(contact.getFirstName()+" "+contact.getLastName());
        holder.txtPhoneNumber.setText(contact.getPhoneNumber()+"");

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView txtFirstName, txtPhoneNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFirstName=itemView.findViewById(R.id.FirstName_row);
            txtPhoneNumber=itemView.findViewById(R.id.etPhoneNumber);



            // Long Press on recycler row
            itemView.setOnLongClickListener(this);


        }

        @Override
        public boolean onLongClick(View v) {
            onContactLongPressListener.onContactLongPress(getAdapterPosition());
            return true;
        }
    }

    public interface OnContactLongPressListener {
        void onContactLongPress(int position);
    }

}











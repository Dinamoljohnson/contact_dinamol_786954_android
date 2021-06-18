package com.example.contact_dinamol_786954_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddNewContact extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private EditText edtFirstName, edtLastName, edtEmail, edtPhoneNumber, edtAddress;
    private boolean isEditing = false;
    private int contactId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_contact);
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactViewModel.class);

        edtFirstName = findViewById(R.id.etFirstName);
        edtLastName = findViewById(R.id.etLastName);
        edtEmail = findViewById(R.id.etEmail);
        edtPhoneNumber = findViewById(R.id.etPhoneNumber);
        edtAddress = findViewById(R.id.etAddress);
        TextView txtLblTitle = findViewById(R.id.label);
        Button btnAdd = findViewById(R.id.btnSave);

        if (getIntent().hasExtra(ContactsActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(ContactsActivity.CONTACT_ID, 0);
            Log.d("TAG", "onCreate: " + contactId);

            contactViewModel.getContact(contactId).observe(this, contact -> {
                if (contact != null) {
                    edtFirstName.setText(contact.getFirstName());
                    edtLastName.setText(contact.getLastName());
                    edtEmail.setText(contact.getEmail());
                    edtPhoneNumber.setText(contact.getPhoneNumber() + "");
                    edtAddress.setText(contact.getAddress());
                }
            });


            isEditing = true;
            txtLblTitle.setText(R.string.update_contact);
            btnAdd.setText(R.string.update);

        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {

        addEditContact();



        });

    }

    private Boolean addEditContact() {



        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();


        if (firstName.isEmpty()) {
            edtFirstName.setError("Please enter First Name");
            edtFirstName.requestFocus();
            return  false;

        }
        if (lastName.isEmpty()) {
            edtLastName.setError("Please enter Last Name");
            edtLastName.requestFocus();
            return  false;

        }
        if (email.isEmpty()) {
            edtEmail.setError("Please enter Email");
            edtEmail.requestFocus();
            return  false;

        }
        if (phoneNumber.isEmpty()) {
            edtPhoneNumber.setError("Please enter Phone Number");
            edtPhoneNumber.requestFocus();
            return  false;
        }
        if (address.isEmpty()) {
            edtAddress.setError("Please enter address");
            edtAddress.requestFocus();
            return  false;
        }


        if (isEditing) {
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setFirstName(edtFirstName.getText().toString().trim());
            contact.setLastName(edtLastName.getText().toString().trim());
            contact.setEmail(edtEmail.getText().toString().trim());
            contact.setPhoneNumber(edtPhoneNumber.getText().toString().trim());
            contact.setAddress(edtAddress.getText().toString().trim());
            contactViewModel.update(contact);
        } else {

            Contact contact = new Contact(
                    edtFirstName.getText().toString().trim(),
                    edtLastName.getText().toString().trim(),
                    edtEmail.getText().toString().trim(),
                    edtPhoneNumber.getText().toString().trim(),
                    edtAddress.getText().toString().trim()
            );
            contactViewModel.insert(contact);
            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }

        finish();
        return true;
    }
}
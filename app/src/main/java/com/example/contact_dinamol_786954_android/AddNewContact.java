package com.example.contact_dinamol_786954_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

        import android.content.Intent;
        import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
        import android.widget.EditText;
import android.widget.TextView;
        import android.widget.Toast;

public class AddNewContact extends AppCompatActivity {


    private static final int CONTACT_ID =0 ;
    private static final String PHONENUMBER_REPLY = "";
    private static final String FIRST_REPLY = "" ;
    private static final String LAST_REPLY = "";
    private static final String EMAIL_REPLY = "";
    private static final String ADDRESS_REPLY = "";

    private EditText etFirstName, etLastName, etEmail, etPhoneNumber, etAddress;

    private boolean isEditing = false;
    private int contactId = 0;
    private Contact contactTobeUpdated;

    private ContactViewModel contactViewModel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_contact);

        contactViewModel1 = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactViewModel.class);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);


        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            btnSaveContact();
        });

        if (getIntent().hasExtra(ContactsActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(ContactsActivity.CONTACT_ID, 0);
            Log.d("TAG", "onCreate: " + contactId);

            contactViewModel1.getContact(contactId).observe(this, contact -> {
                if (contact != null) {
                    etFirstName.setText(contact.getFirstName());
                    etLastName.setText(String.valueOf(contact.getLastName()));
                    etEmail.setText(String.valueOf(contact.getEmail()));
                    etPhoneNumber.setText(String.valueOf(contact.getPhoneNumber()));
                    etAddress.setText(String.valueOf(contact.getAddress()));
                    contactTobeUpdated = contact;
                }
            });

            TextView label = findViewById(R.id.label);
            isEditing = true;
            label.setText(R.string.update_label);
            btnSave.setText(R.string.btnSave);
        }
    }

    private void btnSaveContact() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String Email = etEmail.getText().toString().trim();
        String PhoneNumber = etPhoneNumber.getText().toString().trim();
        String Address = etAddress.getText().toString().trim();

        if (firstName.isEmpty()) {
            etFirstName.setError("firstName field cannot be empty");
            etFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            etLastName.setError("lastName field cannot be empty");
            etLastName.requestFocus();
            return;
        }
        if (PhoneNumber.isEmpty()) {
            etPhoneNumber.setError("PhoneNumber field cannot be empty");
            etPhoneNumber.requestFocus();
            return;
        }

        if (isEditing) {
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setEmail(Email);
            contact.setPhoneNumber(PhoneNumber);
            contact.setAddress(Address);
            contactViewModel1.update(contact);
        } else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(FIRST_REPLY, firstName);
            replyIntent.putExtra(LAST_REPLY, lastName);
            replyIntent.putExtra(EMAIL_REPLY, Email);
            replyIntent.putExtra(PHONENUMBER_REPLY, PhoneNumber);
            replyIntent.putExtra(ADDRESS_REPLY, Address);
            setResult(RESULT_OK, replyIntent);

            Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
    }
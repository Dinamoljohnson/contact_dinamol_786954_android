package com.example.contact_dinamol_786954_android;

import android.app.Application;

        import androidx.lifecycle.LiveData;

        import java.util.List;

public class ContactRepository {
    private ContactDAO contactDAO;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        ContactDatabase db = ContactDatabase.getInstance(application);
        contactDAO = db.contactDAO();
        allContacts = contactDAO.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<Contact> getContact(int id) {return contactDAO.getContact(id);}

    public void insert(Contact contact) {
        ContactDatabase.databaseWriteExecutor.execute(() -> contactDAO.insert(contact));
    }

    public void update(Contact contact) {
        ContactDatabase.databaseWriteExecutor.execute(() -> contactDAO.update(contact));
    }

    public void delete(Contact contact) {
        ContactDatabase.databaseWriteExecutor.execute(() -> contactDAO.delete(contact));
    }

}




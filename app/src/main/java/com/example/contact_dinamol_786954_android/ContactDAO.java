package com.example.contact_dinamol_786954_android;

import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;
        import androidx.room.Update;

        import java.util.List;

@Dao
public interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id == :id")
    LiveData<Contact> getContact(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);


}

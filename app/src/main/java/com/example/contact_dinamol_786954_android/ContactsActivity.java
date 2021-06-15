package com.example.contact_dinamol_786954_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;



public class ContactsActivity extends AppCompatActivity {


    public static final int ADD_CONTACT_REQUEST_CODE = 1;
    public static final String CONTACT_ID = "contact_id";
    private static final String TAG = "MainActivity";
    // declaration of contactViewModel
    private ContactViewModel contactViewModel1;
    // the following approach instead of onActivityResult as startActivityForResult is deprecated
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();

                   // contactViewModel1.insert(contact);
                }
            });
    private RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private Contact deletedContact;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactViewModel1 = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);


        searchView = findViewById(R.id.searchItems);


    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Contact contact = contactViewModel1.getAllContacts().getValue().get(position);
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        deletedContact = contact;
                        contactViewModel1.delete(contact);
                        Snackbar.make(recyclerView, deletedContact.getFirstName() + " is deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> contactViewModel1.insert(deletedContact)).show();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> contactListAdapter.notifyDataSetChanged());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                case ItemTouchHelper.RIGHT:
                    Intent intent = new Intent(ContactsActivity.this, AddNewContact.class);
                    intent.putExtra(CONTACT_ID, contact.getId());
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .setIconHorizontalMargin(1, 1)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightActionIcon(R.drawable.ic_update)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


  /*  @Override
    public void onContactClick(int position) {
        Log.d(TAG, "onContactClick: " + position);
        Contact contact = contactViewModel1.getAllContacts().getValue().get(position);
        Intent intent = new Intent(MainActivity.this, AddNewContact.class);
        intent.putExtra(CONTACT_ID, contact.getId());
        startActivity(intent);
    }*/
}



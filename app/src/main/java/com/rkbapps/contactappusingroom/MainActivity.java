package com.rkbapps.contactappusingroom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rkbapps.contactappusingroom.db.Contact;
import com.rkbapps.contactappusingroom.db.ContactDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    ContactDatabase contactDatabase;
    List<Contact> contactList = new ArrayList<>();
    MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.addBtn);
        contactDatabase = Room.databaseBuilder(getApplicationContext(), ContactDatabase.class, "ContactDB")
                .allowMainThreadQueries()
                .build();

        contactList.addAll(contactDatabase.getContactDao().getAllContacts());
        adapter = new MyAdapter(this, contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(MainActivity.this);
                d.setContentView(R.layout.add_contact_dialog);
                Button cancel = d.findViewById(R.id.btnCancel);
                Button save = d.findViewById(R.id.btnSave);
                EditText name = d.findViewById(R.id.etName);
                EditText email = d.findViewById(R.id.etEmail);
                EditText mobileNumber = d.findViewById(R.id.etMobileNumber);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputValidation(name, email, mobileNumber)) {
                            createContact(name.getText().toString().trim(), mobileNumber.getText().toString().trim(), email.getText().toString().trim());
                            d.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });


    }

    private void createContact(String name, String mobileNumber, String email) {

        long id = contactDatabase.getContactDao()
                .addContact(new Contact(name, mobileNumber, email));
        Contact contact = contactDatabase.getContactDao().getContact(id);
        if (contact != null) {
            contactList.add(0, contact);
            adapter.notifyDataSetChanged();
        }

    }

    private boolean inputValidation(EditText name, EditText email, EditText mobileNumber) {
        if (name.getText().toString().trim().equals("")) {
            name.setError("Enter Name");
            return false;
        }
        if (email.getText().toString().trim().equals("")) {
            email.setError("Enter email");
            return false;
        }
        if (mobileNumber.getText().toString().trim().equals("")) {
            mobileNumber.setError("Enter mobile number");
            return false;
        }
        return true;
    }
}
package com.rkbapps.contactappusingroom;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.rkbapps.contactappusingroom.db.Contact;
import com.rkbapps.contactappusingroom.db.ContactDatabase;

public class ContactViewActivity extends AppCompatActivity {

    ContactDatabase cd;

    TextView contactAvtar;
    ImageButton btnCall, btnMassage, btnVideoCall;
    Button btnEditContact;
    TextView txtName, txtEmail, txtPhoneNumber;
    String strName;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
        long id = getIntent().getLongExtra("id", 0);

        contactAvtar = findViewById(R.id.contactAvtar);
        btnCall = findViewById(R.id.btnCall);
        btnMassage = findViewById(R.id.btnMassage);
        btnVideoCall = findViewById(R.id.btnVideoCall);
        btnEditContact = findViewById(R.id.btnEditContact);
        txtName = findViewById(R.id.textName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtEmail = findViewById(R.id.txtMailAdress);

        Toolbar toolbar = findViewById(R.id.toolBarContactView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Details");

        cd = Room.databaseBuilder(this, ContactDatabase.class, "ContactDB").allowMainThreadQueries().build();

        Contact contact = cd.getContactDao().getContact(id);

        setData(contact);

        btnEditContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(ContactViewActivity.this);
                d.setContentView(R.layout.add_contact_dialog);
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                Button cancel = d.findViewById(R.id.btnCancel);
                Button save = d.findViewById(R.id.btnSave);
                EditText name = d.findViewById(R.id.etName);
                EditText email = d.findViewById(R.id.etEmail);
                EditText mobileNumber = d.findViewById(R.id.etMobileNumber);
                name.setText(strName);
                email.setText(contact.getEmailId());
                mobileNumber.setText(contact.getMobileNumber());
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contact.setName(name.getText().toString().trim());
                        contact.setMobileNumber(mobileNumber.getText().toString().trim());
                        contact.setEmailId(email.getText().toString().trim());

                        cd.getContactDao().updateContact(contact);
                        setData(contact);
                        d.dismiss();

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

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91"+contact.getMobileNumber()));
                startActivity(Intent.createChooser(i, "Call"));
            }
        });

        btnMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("sms:+91"+contact.getMobileNumber()));
                startActivity(Intent.createChooser(i,"Sent massage"));
            }
        });

        btnVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+91"+contact.getMobileNumber()));
                startActivity(Intent.createChooser(i, "Call"));
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setData(Contact contact){
        strName = contact.getName();
        char c[] = strName.toCharArray();
        contactAvtar.setText("" + c[0]);
        txtName.setText(strName);
        txtPhoneNumber.setText(contact.getMobileNumber());
        txtEmail.setText(contact.getEmailId());
    }
}
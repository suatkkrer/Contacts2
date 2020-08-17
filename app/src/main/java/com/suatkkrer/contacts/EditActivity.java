package com.suatkkrer.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    TextInputLayout name_id,phone_id;
    private FirebaseAuth mAuthorize;
    String validUser;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name_id = findViewById(R.id.dialog_name_id);
        phone_id = findViewById(R.id.dialog_phone_id);
        mAuthorize = FirebaseAuth.getInstance();
        validUser = mAuthorize.getCurrentUser().getUid();


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        id = intent.getStringExtra("id");

        name_id.getEditText().setText(name);
        phone_id.getEditText().setText(phone);

    }

    public void deleteContact(View view) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(validUser).child("contacts").child(id);
        Intent intent = new Intent(getApplicationContext(),ContactList.class);

        System.out.println(id);
        
        reference.removeValue();

        Toast.makeText(this, "Contact is deleted", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void updateData(View view) {

        if (TextUtils.isEmpty(name_id.getEditText().getText().toString()) || TextUtils.isEmpty(phone_id.getEditText().getText().toString()))
        {
            Toast.makeText(this, "Please enter name and phone number", Toast.LENGTH_SHORT).show();
        } else
        {
            update(id,name_id.getEditText().getText().toString(),phone_id.getEditText().getText().toString());
        }

    }
    public boolean update(String id,String name, String phone){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(validUser).child("contacts").child(id);

        Contact contact = new Contact(id,name,phone);

        databaseReference.setValue(contact);

        Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show();

        return true;
    }
}
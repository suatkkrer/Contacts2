package com.suatkkrer.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DuplicateFragment extends Fragment {


    RecylerViewAdapter recylerViewAdapter;
    ArrayList<String> userName;
    ArrayList<String> userPhone;
    ArrayList<String> userID;
    private FirebaseAuth mAuthorize;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> userNameDuplicated;
    ArrayList<String> userPhoneDuplicated;
    ArrayList<String> userIdDuplicated;
    ArrayList<String> userNameDuplicated2;
    ArrayList<String> userPhoneDuplicated2;
    ArrayList<String> userIdDuplicated2;
    String validUser;
    View v;
    Context thisContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        thisContext = container.getContext();
        return inflater.inflate(R.layout.fragment_duplicate,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userName = new ArrayList<>();
        userPhone = new ArrayList<>();
        userID = new ArrayList<>();
        userIdDuplicated = new ArrayList<>();
        userNameDuplicated = new ArrayList<>();
        userPhoneDuplicated = new ArrayList<>();
        userIdDuplicated2 = new ArrayList<>();
        userNameDuplicated2 = new ArrayList<>();
        userPhoneDuplicated2 = new ArrayList<>();


        mAuthorize = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        validUser = mAuthorize.getCurrentUser().getUid();
        reference = firebaseDatabase.getReference("Users");

        getDataFirebase();
    }

    private void getDataFirebase() {

        DatabaseReference reference = firebaseDatabase.getReference("Users");
        reference.child(validUser).child("contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.clear();
                userPhone.clear();
                userID.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Contact contact = snapshot1.getValue(Contact.class);
                    if (contact.getName() != null && contact.getPhone() != null) {
                        String txt = contact.getName();
                        String txtphone = contact.getPhone();
                        String userid = contact.getId();
                        userName.add(txt);
                        userPhone.add(txtphone);
                        userID.add(userid);
                    }
                }
                System.out.println(userName);

                for (int i = 0; i < userName.size() ; i++) {
                    if (!userNameDuplicated.contains(userName.get(i))){
                        userNameDuplicated.add((userName.get(i)));
                        userPhoneDuplicated.add(userPhone.get(i));
                    } else {
                        userNameDuplicated2.add(userName.get(i));
                        userPhoneDuplicated2.add(userPhone.get(i));
                    }
                }
                for (int i = 0; i < userPhone.size() ; i++) {
                    if (!userPhoneDuplicated.contains(userPhone.get(i))){
                        userPhoneDuplicated.add(userPhone.get(i));
                        userNameDuplicated.add(userName.get(i));
                    } else {
                        userPhoneDuplicated2.add(userPhone.get(i));
                        userNameDuplicated2.add(userName.get(i));
                    }
                }
                System.out.println(userNameDuplicated);
                System.out.println(userNameDuplicated2);
                System.out.println(userPhoneDuplicated);
                System.out.println(userPhoneDuplicated2);
//                recylerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

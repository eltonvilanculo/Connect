package com.example.connect;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactosFragment extends Fragment {

    RecyclerView recyclerView;
    UserListAdapter adapter;

    List<User> userList;


    DatabaseReference ref;
    private LinearLayoutManager layout;


    public ContactosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);

        if (savedInstanceState == null) {
            userList = new ArrayList<>();
            adapter = new UserListAdapter(this.getContext(), userList);
            recyclerView = view.findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            layout = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapter);
        }


        String userId = FirebaseAuth.getInstance().getUid();
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = ref.child("users");

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    addTolist(user);
                } catch (NullPointerException | NoSuchElementException ex) {

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void addTolist(User user) {
        userList.add(user);
        adapter.notifyDataSetChanged();
    }

}

package com.example.connect.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.activity.ChatRoomActivity;
import com.example.connect.activity.MainActivity;
import com.example.connect.model.User;
import com.example.connect.adapter.UserListAdapter;
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
    public String TAG = getClass().getSimpleName();
    RecyclerView recyclerView;
    UserListAdapter adapter;

    List<User> userList;


    DatabaseReference ref;
    private LinearLayoutManager layout;
    private User user;


    public ContactosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);


        userList = new ArrayList<>();

        adapter = new UserListAdapter(getActivity(), this::handleAction);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);


        String userId = FirebaseAuth.getInstance().getUid();
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = ref.child("users");

        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    user = dataSnapshot.getValue(User.class);
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

    private void handleAction(User user) {
        Toast.makeText(getActivity(), user.getUsername(), Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
        intent.putExtra("user_name_clicked",user.getUsername());
        startActivity(intent);*/

        //Toast.makeText(getActivity(), "Clicked Now", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "handleAction: " + String.valueOf(user));
    }

    private void addTolist(User user) {
        adapter.add(user);
        adapter.notifyDataSetChanged();
    }

}

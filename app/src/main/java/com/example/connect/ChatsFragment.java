package com.example.connect;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    EditText inputAddChat ;
    Button btnAddChat ;

    ListView listViewchatList;

    ArrayList<String> listRooms = new ArrayList<>();

    String senderName;

private DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chats, container, false);



        senderName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        inputAddChat = view.findViewById(R.id.input_add_chat);
        btnAddChat= view.findViewById(R.id.btn_add_chat);
        listViewchatList = view.findViewById(R.id.list_view_chat_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listRooms);
        listViewchatList.setAdapter(adapter);

        btnAddChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> map = new HashMap<String, Object>();
                //Nao colocamos o valor do objecto pk so precisamos do nome do chat
                map.put(inputAddChat.getText().toString(),"");
                root.updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Para pegar nome de todas as raizes
                //Le linha por linha
                Set<String>set = new HashSet<String>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

           while (iterator.hasNext()){

               set.add(((DataSnapshot) iterator.next()).getKey());

           }
           listRooms.clear();
           listRooms.addAll(set);
           adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewchatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ChatRoomActivity.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",senderName);
                startActivity(intent);

            }
        });

        return view;
    }

}

package com.example.connect.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.activity.ChatRoomActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
FloatingActionButton btnAddChat ;

    ListView listViewchatList;

    ArrayList<String> listRooms = new ArrayList<>();

    String senderName;

    ProgressDialog progressDialog;

private DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();



    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chats, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Carregando conversas...");
        progressDialog.show();
        senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        btnAddChat= view.findViewById(R.id.btn_add_chat);
        listViewchatList = view.findViewById(R.id.list_view_chat_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listRooms);
        listViewchatList.setAdapter(adapter);

        btnAddChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "clik", Toast.LENGTH_SHORT).show();
                showInputRoomName();


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

                for (int i = 0; i <listRooms.size() ; i++) {

                    if(listRooms.get(i).contains("users")){
                        listRooms.remove(i);
                    }
                }
               progressDialog.cancel();
           adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewchatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatRoomActivity.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",senderName);
                startActivity(intent);

            }
        });

        return view;
    }

    private void showInputRoomName() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Room name");

// Set up the input
        final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );

        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String roomName = input.getText().toString();
                if(roomName.trim().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "O nome nao pode ser vazio", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String,Object> map = new HashMap<String, Object>();
                    //Nao colocamos o valor do objecto pk so precisamos do nome do chat
                    map.put(roomName,"");
                    root.updateChildren(map);

                }
                 }
        });
        builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

}

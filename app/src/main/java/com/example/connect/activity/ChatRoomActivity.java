package com.example.connect.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChatRoomActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    TextView textViewMsg;
    EditText inputSendMsg;

    String senderName, chatName;

    String chatMessage, chatUsername;

    DatabaseReference root;
    private String tempKey;
    private boolean isSubscribed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        textViewMsg = findViewById(R.id.text_msg_id);
        inputSendMsg = findViewById(R.id.input_send_chat);
        Toolbar toolbar = findViewById(R.id.toolbar_id);


        try {
            senderName = getIntent().getExtras().get("user_name").toString();
            chatName = getIntent().getExtras().get("room_name").toString();
        } catch (Exception e) {

            User user = getIntent().getExtras().getParcelable("key");
            senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            chatName=user.getUsername();
            e.printStackTrace();
        }


        toolbar.setTitle(chatName);
        setSupportActionBar(toolbar);

         User userData = getUserData();

     FirebaseMessaging.getInstance()
                .subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            isSubscribed = true;
                        } else {
                            isSubscribed = false;
                        }
                    }
                });


        root = FirebaseDatabase.getInstance().getReference().child(chatName);

        findViewById(R.id.btn_send_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String, Object> map = new HashMap<String, Object>();
                tempKey = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference msgRoot = root.child(tempKey);

                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", senderName);
                map2.put("msg", inputSendMsg.getText().toString());
                msgRoot.updateChildren(map2);
                inputSendMsg.setText("");


            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                retrivingConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                retrivingConversation(dataSnapshot);
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

    }

    private void retrivingConversation(DataSnapshot dataSnapshot) {

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()) {

            //iterator pega o valor
            chatMessage = ((DataSnapshot) iterator.next()).getValue().toString();
            chatUsername = ((DataSnapshot) iterator.next()).getValue().toString();

            //Mensagens whatever
            textViewMsg.append(chatUsername + ":" + chatMessage + "\n");
        }

    }

    public User getUserData() {
        try {
            return (User)getIntent().getParcelableExtra("user");
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getUsernameClicked(){

        return (String) getIntent().getExtras().get("user_name_clicked");



    }
}

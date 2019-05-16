package com.example.connect.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.connect.R;
import com.example.connect.activity.ChatRoomActivity;
import com.example.connect.activity.ContentActivity;
import com.example.connect.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> userList;
    String TAG = getClass().getSimpleName();

    private  OnContactClicked onContactClicked;
    public UserListAdapter(Context mContext, OnContactClicked onContactClicked) {
        if (onContactClicked != null) {
            this.onContactClicked = onContactClicked;
        }


        this.mContext = mContext;
        this.userList = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }


    public void add(User user){
        userList.add(user);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.emailTextView.setText(user.getEmail());
        holder.usernameTextView.setText(user.getUsername());
        /*holder.userImg.setImageDrawable(mContext.getResources().getDrawable(user.getImgURL(),null));*/ //TODO: COLOCAR IMAGEM DO UTILIZADOR

        Log.d(TAG,"onBindViewHolder of: " +String.valueOf(onContactClicked));

        holder.itemView.setOnClickListener(v -> {

            Log.d(TAG,"Value of: " +String.valueOf(onContactClicked));

            Toast.makeText(mContext, "ola mundo", Toast.LENGTH_SHORT).show();

            if (onContactClicked != null) {
                onContactClicked.handle(user);

            }


        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public void setOnContactClicked(OnContactClicked onContactClicked) {
        this.onContactClicked = onContactClicked;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ImageView userImg;
        TextView usernameTextView, emailTextView;

       public UserViewHolder(@NonNull View itemView) {
           super(itemView);

           userImg = itemView.findViewById(R.id.profile_image);
           usernameTextView = itemView.findViewById(R.id.username_textView);
           emailTextView = itemView.findViewById(R.id.email_textView);
       }
   }

   public  interface  OnContactClicked{
        void handle(User user);
    }

}

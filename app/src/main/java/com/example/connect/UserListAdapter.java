package com.example.connect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> userList;

    public UserListAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.user_item,null);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.emailTextView.setText(user.getEmail());
        holder.usernameTextView.setText(user.getUsername());
        /*holder.userImg.setImageDrawable(mContext.getResources().getDrawable(user.getImgURL(),null));*/ //TODO: COLOCAR IMAGEM DO UTILIZADOR



    }

    @Override
    public int getItemCount() {
        return userList.size();
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



}

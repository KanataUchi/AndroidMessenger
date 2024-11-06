package sleep.kontora.androidmessenger.users;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import sleep.kontora.androidmessenger.ChatActivity;
import sleep.kontora.androidmessenger.MainActivity;
import sleep.kontora.androidmessenger.R;
import sleep.kontora.androidmessenger.bottomnav.chats.ChatsFragment;
import sleep.kontora.androidmessenger.bottomnav.new_chat.NewChatFragment;
import sleep.kontora.androidmessenger.chats.Chat;
import sleep.kontora.androidmessenger.utils.ChatUtil;

public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private ArrayList<User> users = new ArrayList<>();

    public UsersAdapter(ArrayList<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_rv, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.username_tv.setText(user.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("")
                            .setMessage("Do you want add this person in package chats?")
                            .setCancelable(false)
                            .setPositiveButton("yeah", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i){
                                    ChatUtil.createChat(user);
                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}

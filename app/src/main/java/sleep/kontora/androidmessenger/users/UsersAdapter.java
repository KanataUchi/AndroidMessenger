package sleep.kontora.androidmessenger.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import sleep.kontora.androidmessenger.R;
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
                ChatUtil.createChat(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}

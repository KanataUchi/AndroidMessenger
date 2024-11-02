package sleep.kontora.androidmessenger.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sleep.kontora.androidmessenger.R;

public class ChatsAdapter extends RecyclerView.Adapter<ChatViewHolder> {


    private ArrayList<Chat> chats;

    public ChatsAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_rv, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.chat_name_tv.setText(chats.get(position).getChat_name());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}

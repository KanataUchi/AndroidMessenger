package sleep.kontora.androidmessenger.chats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sleep.kontora.androidmessenger.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    ImageView chats_iv;

    TextView chat_name_tv;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        chats_iv = itemView.findViewById(R.id.profile_iv);
        chat_name_tv = itemView.findViewById(R.id.username_tv);
    }
}

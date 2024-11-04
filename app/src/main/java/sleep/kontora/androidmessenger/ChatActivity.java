package sleep.kontora.androidmessenger;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sleep.kontora.androidmessenger.chats.ChatsAdapter;
import sleep.kontora.androidmessenger.databinding.ActivityChatBinding;
import sleep.kontora.androidmessenger.message.Message;
import sleep.kontora.androidmessenger.message.MessagesAdapter;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadMessages();
    }
    private void loadMessages(){
        String chatId = getIntent().getStringExtra("chatId");
        if(chatId==null){
            return;
        }
        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()) return;

                        List<Message> messages = new ArrayList<>();
                        for(DataSnapshot messageSnapshot: snapshot.getChildren()){
                            String messageId = messageSnapshot.getKey();
                            String ownerId = messageSnapshot.child("ownerId").getValue().toString();
                            String text = messageSnapshot.child("text").getValue().toString();
                            String data = messageSnapshot.child("data").getValue().toString();

                            messages.add(new Message(messageId,ownerId,text,data));
                        }

                        binding.massagesRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        binding.massagesRv.setAdapter(new MessagesAdapter(messages));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
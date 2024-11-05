package sleep.kontora.androidmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import sleep.kontora.androidmessenger.chats.ChatsAdapter;
import sleep.kontora.androidmessenger.databinding.ActivityChatBinding;
import sleep.kontora.androidmessenger.databinding.FragmentChatsBinding;
import sleep.kontora.androidmessenger.message.Message;
import sleep.kontora.androidmessenger.message.MessagesAdapter;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String chatId = getIntent().getStringExtra("chatId");

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userId1 = snapshot.child("Chats").child(chatId).child("user1").getValue().toString();

                String userId2 = snapshot.child("Chats").child(chatId).child("user2").getValue().toString();

                if(userId1.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    binding.nicknameTv.setText(snapshot.child("Users").child(userId2).child("username").getValue().toString());
                } else {
                    binding.nicknameTv.setText(snapshot.child("Users").child(userId1).child("username").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        loadMessages(chatId);

        binding.sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageEt.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "message field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String data = simpleDateFormat.format(new Date());

                binding.messageEt.setText("");
                sendMessage(chatId, message, data);
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }

    private void sendMessage(String chatId, String message, String data){

        if(chatId==null){
            return;
        }

        HashMap<String, String> messageInfo = new HashMap<>();
        messageInfo.put("text", message);
        messageInfo.put("ownerId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        messageInfo.put("data", data);

        FirebaseDatabase.getInstance().getReference().child("Chats").child(chatId)
                .child("messages").push().setValue(messageInfo);
    }

    private void loadMessages(String chatId){

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
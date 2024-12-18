package sleep.kontora.androidmessenger.bottomnav.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import sleep.kontora.androidmessenger.chats.Chat;
import sleep.kontora.androidmessenger.chats.ChatsAdapter;
import sleep.kontora.androidmessenger.databinding.FragmentChatsBinding;

public class ChatsFragment extends Fragment {
    private FragmentChatsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);

        loadChats();

        return binding.getRoot();
    }

    private void loadChats(){
        ArrayList<Chat> chats = new ArrayList<>();

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chatsStr = Objects.requireNonNull(snapshot.child("Users").child(uid).child("chats").getValue()).toString();

                String[] chatsId = chatsStr.split(",");
                if(!chatsStr.isEmpty()) {
                    for (String chatId : chatsId) {
                        DataSnapshot chatSnapshot = snapshot.child("Chats").child(chatId);

                        String userId1 = Objects.requireNonNull(chatSnapshot.child("user1").getValue()).toString();
                        String userId2 = Objects.requireNonNull(chatSnapshot.child("user2").getValue()).toString();

                        String chatUserId;
                        if (uid.equals(userId1)) {
                            chatUserId = userId2;
                        } else {
                            chatUserId = userId1;
                        }
                        String chat_name = Objects.requireNonNull(snapshot.child("Users").child(chatUserId).child("username").getValue()).toString();

                        Chat chat = new Chat(chat_name, chatId, userId1, userId2);
                        chats.add(chat);
                    }
                }
                binding.chatsRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.chatsRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                binding.chatsRv.setAdapter(new ChatsAdapter(chats));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to user chats", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

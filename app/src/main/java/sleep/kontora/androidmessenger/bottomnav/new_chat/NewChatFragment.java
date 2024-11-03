package sleep.kontora.androidmessenger.bottomnav.new_chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import sleep.kontora.androidmessenger.databinding.FragmentChatsBinding;
import sleep.kontora.androidmessenger.databinding.FragmentNewChatBinding;
import sleep.kontora.androidmessenger.users.User;
import sleep.kontora.androidmessenger.users.UsersAdapter;

public class NewChatFragment extends Fragment {
    private FragmentNewChatBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewChatBinding.inflate(inflater, container, false);

        loadUsers();

        return binding.getRoot();
    }

    private void loadUsers(){

        ArrayList<User> users = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if(Objects.equals(userSnapshot.getKey(), Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())){
                        continue;
                    }

                    String uid = userSnapshot.getKey();
                    String username = Objects.requireNonNull(userSnapshot.child("username").getValue()).toString();

                    users.add(new User(uid ,username));
                }

                binding.usersRv.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.usersRv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                binding.usersRv.setAdapter(new UsersAdapter(users));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
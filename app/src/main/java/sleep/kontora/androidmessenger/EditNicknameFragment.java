package sleep.kontora.androidmessenger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import sleep.kontora.androidmessenger.databinding.FragmentEditNicknameBinding;
import sleep.kontora.androidmessenger.databinding.FragmentProfileBinding;

public class EditNicknameFragment extends Fragment {
    private FragmentEditNicknameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditNicknameBinding.inflate(inflater, container, false);

        binding.editNicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.fieldEditEt.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "fields cannot be empty ", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username")
                            .setValue(binding.fieldEditEt.getText().toString());
                }
            }
        });

        return binding.getRoot();
    }
}
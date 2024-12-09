package sleep.kontora.androidmessenger;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sleep.kontora.androidmessenger.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            boolean error = false;
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Users")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    if (!binding.emailEt.getText().toString().equals(snapshot.child("email").toString())
                                            || !binding.passwordEt.getText().toString().equals(snapshot.child("password").toString())) {
                                        error = true;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                if(binding.emailEt.getText().toString().isEmpty() || binding.passwordEt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(error){
                    Toast.makeText(getApplicationContext(), "uncorrect input", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.emailEt.getText().toString(), binding.passwordEt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isComplete()){
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                }
                            });
                }
            }
        });

        binding.goToRegisterActivityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrActivity.class));
            }
        });
    }
}
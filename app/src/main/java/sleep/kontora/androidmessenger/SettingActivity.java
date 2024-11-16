package sleep.kontora.androidmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import sleep.kontora.androidmessenger.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(SettingActivity.this , LoginActivity.class));
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
            }
        });

        binding.egitNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNicknameFragment editNicknameFragment = new EditNicknameFragment();
                changeFragment(editNicknameFragment);
            }
        });

    }

    private void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.change_field, fragment);
        fragmentTransaction.commit();
    }
}
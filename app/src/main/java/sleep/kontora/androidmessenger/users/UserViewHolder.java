package sleep.kontora.androidmessenger.users;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sleep.kontora.androidmessenger.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView profileImage_iv;

    TextView username_tv;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage_iv = itemView.findViewById(R.id.profile_iv);
        username_tv = itemView.findViewById(R.id.username_tv);
    }
}

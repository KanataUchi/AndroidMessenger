package sleep.kontora.androidmessenger.groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sleep.kontora.androidmessenger.R;
import sleep.kontora.androidmessenger.chats.Chat;

public class AddMembersAdapter extends RecyclerView.Adapter<AddMembersAdapter.AddMembersViewHolder> {

    private ArrayList<Chat> chats;


    public AddMembersAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public AddMembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_choice_rv, parent, false);
        return new AddMembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddMembersViewHolder holder, int position) {
        holder.add_name_member_tv.setText(chats.get(position).getChat_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class AddMembersViewHolder extends RecyclerView.ViewHolder{

        ImageView add_member_iv;

        TextView add_name_member_tv;

        CheckBox member_choice_cb;

        public AddMembersViewHolder(@NonNull View itemView) {
            super(itemView);

            add_member_iv = itemView.findViewById(R.id.profile_iv);
            add_name_member_tv = itemView.findViewById(R.id.username_tv);
            member_choice_cb = itemView.findViewById(R.id.choice_member_cb);
        }
    }
}

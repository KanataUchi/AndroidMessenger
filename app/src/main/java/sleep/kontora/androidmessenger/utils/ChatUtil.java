package sleep.kontora.androidmessenger.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import sleep.kontora.androidmessenger.users.User;

public class ChatUtil {

    public static void createChat(User user){
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        HashMap<String, String> chatInfo = new HashMap<>();
        chatInfo.put("user1", uid);
        chatInfo.put("user2", user.getUid());

        String chatId = generateChatId(uid, user.getUid());
        FirebaseDatabase.getInstance().getReference().child("Chats").child(chatId)
                .setValue(chatInfo);

        addChatIdToUser(uid, chatId);
        addChatIdToUser(user.getUid(), chatId);
    }

    private static String generateChatId(String userId1, String userId2){
        String sumUser1User2 = userId1 + userId2;
        char[] charArray = sumUser1User2.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    private static void addChatIdToUser(String uid, String chatId){
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                .child("chats").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String chats = Objects.requireNonNull(task.getResult().getValue()).toString();
                            boolean isRetry = false;
                            if(!chats.isEmpty()) {
                                String[] str1 = chats.split(",");
                                for (int i = 0; i < str1.length; i++) {
                                    if (chatId.equals(str1[i])) {
                                        isRetry = true;
                                    }
                                }
                            }

                                String chatsUpd;

                                if (!isRetry) {
                                    chatsUpd = addIdToStr(chats, chatId);
                                } else {
                                    chatsUpd = chats;
                                }

                                FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                                        .child("chats").setValue(chatsUpd);
                            
                        }
                    }
                });
    }

    private static String addIdToStr(String str, String chatId){

        if(str.isEmpty()){
            str += chatId;
        }
        else str+= "," + chatId;

        return str;
    }
}

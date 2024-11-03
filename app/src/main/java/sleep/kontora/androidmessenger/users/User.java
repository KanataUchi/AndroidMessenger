package sleep.kontora.androidmessenger.users;

public class User {
    private String uid, username;

    public User(String uid,String username) {
        this.uid = uid;
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }
}

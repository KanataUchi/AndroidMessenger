package sleep.kontora.androidmessenger.message;

public class Message {
    private String id, ownerId, text, data;

    public Message(String id, String ownerId, String text, String data) {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

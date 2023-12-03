package app.user;

public class NormalUser extends User {
    private boolean isOnline;
    public NormalUser(String username, int age, String city) {
        super(username, age, city);
        this.isOnline = true;
    }
    public void switchOnlineStatus() {
        isOnline = !isOnline;
    }
    public boolean isOnline() {
        return isOnline;
    }
}

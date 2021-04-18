package model;

public class Usuario implements Entity{
    private int ID;
    private String username;
    private String password;
    private boolean enabled;

    public Usuario() {
    }

    public Usuario(int ID, String username, String password, boolean enabled) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void Charge(Object[] info) {
        this.ID = (int) info[0];
        this.username = (String) info[1];
        this.password = (String) info[2];
        this.enabled = (boolean) info[3];
    }
    
}

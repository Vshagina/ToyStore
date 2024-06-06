package sample;

public class User {
    private int id_user;
    private String user_login;
    private String user_password;
    private String user_age;
    private String user_email;


    public User(String user_login, String user_password, String user_age, String user_email) {
        this.id_user = id_user;
        this.user_login = user_login;
        this.user_password = user_password;
        this.user_age = user_age;
        this.user_email = user_email;
    }
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}

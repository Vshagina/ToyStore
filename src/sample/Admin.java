package sample;

public class Admin {
    private int id_administrator;
    private String admin_login;
    private String admin_password;
    private String admin_age;
    private String admin_email;

    public Admin(int id_administrator, String admin_login, String admin_password, String admin_age, String admin_email) {
        this.id_administrator = id_administrator;
        this.admin_login = admin_login;
        this.admin_password = admin_password;
        this.admin_age = admin_age;
        this.admin_email = admin_email;
    }

    public int getId_administrator() {
        return id_administrator;
    }

    public void setId_administrator(int id_administrator) {
        this.id_administrator = id_administrator;
    }

    public String getAdmin_login() {
        return admin_login;
    }

    public void setAdmin_login(String admin_login) {
        this.admin_login = admin_login;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_age() {
        return admin_age;
    }

    public void setAdmin_age(String admin_age) {
        this.admin_age = admin_age;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }



}

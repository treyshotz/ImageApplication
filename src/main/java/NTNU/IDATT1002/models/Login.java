package NTNU.IDATT1002.models;

public class Login {

    private Integer id;
    private User user;
    private String password;
    private String passwordHash;
    private String passwordSalt;

    public Login() {
    }

    public Login(int id, User user, String password, String passwordHash, String passwordSalt) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }
}

package NTNU.IDATT1002.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "login")
public class Login {


    @Id
    @JoinColumn(name = "username")
    private String username;


    @OneToOne(cascade = {CascadeType.ALL})
    private User user;

    @NotBlank(message = "Hash salt may not be blank")
    private String hash;

    @NotBlank(message = "Password salt may not be blank")
    private String passwordSalt;

    public Login() {
    }

    public Login(User user, String passwordSalt , String hash) {
        this.username = user.getUsername();
        this.user = user;
        this.hash = hash;
        this.passwordSalt = passwordSalt;
    }

    public User getUser() {
        return user;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getHash() {
        return hash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }
}

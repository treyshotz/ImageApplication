package NTNU.IDATT1002.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String username;

    @Email
    @NotBlank(message = "Email may not be blank")
    private String email;

    @NotBlank(message = "Fist name may not be blank")
    private String firstName;

    @NotBlank(message = "Last name may not be blank")
    private String lastName;

    @NotBlank(message = "Calling code may not be blank")
    private String callingCode;

    @NotBlank(message = "Phone number may not be blank")
    private String phoneNumber;

    @Past(message = "Birth date must be in the past")
    private Date birthDate;
    
    private boolean isAdmin;
    private boolean isActive;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Album> albums = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Image> images = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String firstName, String lastName, String callingCode, String phoneNumber, Date birthDate) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.callingCode = callingCode;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.isAdmin = false;
        this.isActive = true;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * Add given album.
     *
     * @param album the album to add
     */
    public void addAlbum(Album album) {
        albums.add(album);
        album.setUser(this);
    }

    /**
     * Remove given album.
     *
     * @param album the album to remove
     */
    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setUser(null);
    }

    /**
     * Add given image.
     *
     * @param image the image to add
     */
    public void addImage(Image image) {
        images.add(image);
        image.setUser(this);
    }

    /**
     * Remove given image.
     *
     * @param image the image to remove
     */
    public void removeAlbum(Image image) {
        images.remove(image);
        image.setUser(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", callingCode='" + callingCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", isAdmin=" + isAdmin +
                ", isActive=" + isActive +
                ", albums=" + albums +
                ", images=" + images +
                '}';
    }
}

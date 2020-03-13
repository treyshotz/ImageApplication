package NTNU.IDATT1002.models;

import java.util.Date;

public class User {

    private Integer id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String callingCode;
    private String phoneNumber;
    private Date birthDate;
    private boolean isAdmin;
    private boolean isActive;

    public User(int id, String email, String username, String firstName, String lastName, String callingCode, String phoneNumber, Date birthDate, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.callingCode = callingCode;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.isAdmin = isAdmin;
        this.isActive = true;
    }

    public User(User user) {
        this(user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCallingCode(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.isAdmin());
    }

    public Integer getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }
}

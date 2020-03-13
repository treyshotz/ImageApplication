package NTNU.IDATT1002.repository;

import NTNU.IDATT1002.models.User;

import java.util.*;


/**
 * User repository to support interacting with a simulated database.
 */
public class UserRepository implements Repository<User, Integer> {

    /**
     * The "database".
     */
    private static List<User> users = new ArrayList<>();

    /**
     * Supply repository with initial test data.
     */
    static {
        users.add(new User(1, "test@mail.com", "test", "Test", "Testesen", "+47", "00000000", new Date(), false));
        users.add(new User(2, "test2@mail.com", "test2", "Test2", "Testesen2", "+47", "00000001", new Date(), false));
        users.add(new User(3, "test3@mail.com", "test3", "Test3", "Testesen3", "+47", "00000002", new Date(), false));
    }

    @Override
    public User save(User user) throws IllegalArgumentException {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        User savedUser = new User(user);
        users.add(savedUser);
        return savedUser;
    }

    @Override
    public Optional<User> update(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        Optional<User> foundUser = users.stream()
                .filter(user::equals)
                .findFirst();

        if (foundUser.isPresent()) {
            delete(foundUser.get());
            User updatedUser = save(user);
            return Optional.of(updatedUser);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

   public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<User> foundUser = findById(id);
        foundUser.ifPresent(this::delete);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }


    @Override
    public long count() {
        return users.size();
    }

    @Override
    public boolean exists(User user) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        return users.stream()
                .anyMatch(user::equals);
    }
}

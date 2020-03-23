package NTNU.IDATT1002;


import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class Application State. Keeps a record of the global application state, such as the current logged in user.
 */
public final class ApplicationState {

    /**
     * The current logged in user.
     */
    private static User currentUser;

    /**
     * Anonymous user for developing purposes.
     */
    private static User anonymousUser;

    private static UserRepository userRepository;

    /**
     * Initiate properties and save an anonymous user once.
     */
    static {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ImageApplication");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);
    }

    public static void setCurrentUser(User currentUser) {
        ApplicationState.currentUser = currentUser;
    }

    /**
     * Retrieve the current logged in user if present, or retrieve an anonymous user.
     *
     * @return the current user.
     * @throws IllegalArgumentException if neither the current user nor the anonymous user are present.
     */
    public static User getCurrentUser() {
        return userRepository.findById(currentUser.getUsername())
                .orElseGet(() -> userRepository.findById(anonymousUser.getUsername())
                        .orElseThrow(IllegalArgumentException::new));
    }
}

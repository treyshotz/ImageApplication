package NTNU.IDATT1002;


import NTNU.IDATT1002.models.User;

/**
 * Class Application State. Keeps a record of the global application state, such as the current logged in user.
 */
final class ApplicationState {

    /**
     * The current logged in user.
     */
    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        ApplicationState.currentUser = currentUser;
    }
}

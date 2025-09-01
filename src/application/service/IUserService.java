package application.service;

import common.LibraryException;
import domain.user.User;

/**
 * Interface defining user-related operations for the library system.
 */
public interface IUserService {

    /**
     * Adds a new user to the system.
     *
     * @param name the name of the user to add
     * @throws LibraryException if a user with the given name already exists
     */
    void addUser(String name) throws LibraryException;

    /**
     * Removes an existing user from the system.
     *
     * @param name the name of the user to remove
     * @return the removed User object, or null if the user did not exist
     */
    User removeUser(String name);

    /**
     * Lists all registered users in the system.
     *
     * @throws LibraryException if there are no registered users
     */
    void listUsers() throws LibraryException;
}

package domain.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a user in the library system.
 * Each user has a name, a role (default "USER"), and a creation date.
 */
public class User {

    /** The name of the user. */
    private final String name;

    /** The date and time when the user was created, formatted as yyyy-MM-dd HH:mm:ss. */
    private final String creationDate;

    /** The role of the user in the system. Default is "USER". */
    private final String role;

    /**
     * Constructs a new user with the specified name.
     * The creation date is automatically set to the current date and time.
     *
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
        this.role = "USER";
        this.creationDate = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    /**
     * Returns the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the user,
     * showing the name, role, and creation date.
     *
     * @return a formatted string representing the user
     */
    @Override
    public String toString() {
        return name + " - (" + role + "): " + creationDate + ".";
    }
}
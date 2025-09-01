package application.service.impl;

import application.service.IUserService;
import common.LibraryException;
import domain.user.User;
import infrastructure.repository.LibraryRepository;

/**
 * Implementation of the IUserService interface that manages users
 * in a library system using a repository.
 */
public class UserService implements IUserService {

    /** Repository used for storing and retrieving users. */
    private final LibraryRepository repo;

    /**
     * Constructs a UserService with the specified repository.
     *
     * @param repo the repository to store and manage users
     */
    public UserService(LibraryRepository repo) {
        this.repo = repo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(String name) throws LibraryException {
        if (repo.getUserMap().containsKey(name))
            throw new LibraryException("User, \"" + name + "\", already exists.");
        repo.getUserMap().put(name, new User(name));
        System.out.println("User \"" + name + "\", successfully added.");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User removeUser(String name) {
        User user = repo.getUserMap().remove(name);
        System.out.println("User \"" + name + "\", successfully removed.");
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listUsers() throws LibraryException {
        if (repo.getUserMap().isEmpty())
            throw new LibraryException("No registered users.");
        System.out.println("--- Registered Users ---");
        repo.getUserMap().values().forEach((user) -> System.out.println(user.toString()));
    }
}

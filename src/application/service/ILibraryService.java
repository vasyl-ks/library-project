package application.service;

import common.LibraryException;
import infrastructure.repository.LibraryRepository;

/**
 * Interface defining all operations of a Library system,
 * including management of users, books, loans, and events.
 */
public interface ILibraryService {

    // ----------------------
    // User methods
    // ----------------------

    /**
     * Adds a new user to the library.
     *
     * @param name Name of the user.
     * @throws LibraryException if the user already exists or cannot be added.
     */
    void addUser(String name) throws LibraryException;

    /**
     * Removes a user from the library.
     *
     * @param name Name of the user.
     * @throws LibraryException if the user does not exist or cannot be removed.
     */
    void removeUser(String name) throws LibraryException;

    /**
     * Lists all users currently registered in the library.
     *
     * @throws LibraryException if no users exist.
     */
    void listUsers() throws LibraryException;

    // ----------------------
    // Book methods
    // ----------------------

    /**
     * Adds a new book to the library inventory.
     *
     * @param title  Title of the book.
     * @param format Format of the book (DIGITAL or PHYSICAL).
     * @throws LibraryException if the book cannot be added.
     */
    void addBook(String title, String format) throws LibraryException;

    /**
     * Removes a book from the library inventory.
     *
     * @param title  Title of the book.
     * @param format Format of the book (DIGITAL or PHYSICAL).
     * @throws LibraryException if the book does not exist or cannot be removed.
     */
    void removeBook(String title, String format) throws LibraryException;

    /**
     * Lists all available books in the library.
     *
     * @throws LibraryException if no books are available.
     */
    void listBooks() throws LibraryException;

    /**
     * Displays the waiting list for a specific book.
     *
     * @param title Title of the book.
     * @throws LibraryException if the book does not exist.
     */
    void showWaitingList(String title) throws LibraryException;

    /**
     * Sorts all books in inverse alphabetical order by title.
     *
     * @throws LibraryException if no books exist.
     */
    void sortInverse() throws LibraryException;

    /**
     * Reserves a book for the next user in the waiting list.
     *
     * @param title Title of the book.
     * @throws LibraryException if the book does not exist or cannot be reserved.
     */
    void reserve(String title) throws LibraryException;

    /**
     * Vacates a book, making it available for other users.
     *
     * @param title Title of the book.
     * @throws LibraryException if the book does not exist or cannot be vacated.
     */
    void vacate(String title) throws LibraryException;

    // ----------------------
    // Loan methods
    // ----------------------

    /**
     * Lends a book to a user.
     *
     * @param name   Name of the user.
     * @param title  Title of the book.
     * @param format Format of the book (DIGITAL or PHYSICAL).
     * @throws LibraryException if the user or book does not exist or cannot be lent.
     */
    void lendBook(String name, String title, String format) throws LibraryException;

    /**
     * Returns a book from a user.
     *
     * @param name   Name of the user.
     * @param title  Title of the book.
     * @param format Format of the book (DIGITAL or PHYSICAL).
     * @throws LibraryException if the user or book does not exist or cannot be returned.
     */
    void returnBook(String name, String title, String format) throws LibraryException;

    /**
     * Lists all current loans in the library.
     */
    void listLoans();

    // ----------------------
    // Other methods
    // ----------------------

    /**
     * Displays the history of library events.
     */
    void showEventHistory();

    /**
     * Provides access to the underlying repository of the library.
     *
     * @return The library repository.
     */
    LibraryRepository accessRepository();
}
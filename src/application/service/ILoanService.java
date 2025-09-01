package application.service;

import common.LibraryException;
import domain.user.User;

/**
 * Interface defining loan-related operations for the library system.
 */
public interface ILoanService {

    /**
     * Lends a book to a user.
     *
     * @param name   the name of the user borrowing the book
     * @param title  the title of the book to lend
     * @param format the format of the book (PHYSICAL or DIGITAL)
     * @return true if the book was successfully lent, false if the user was added to the waiting list
     * @throws LibraryException if the user has already borrowed the book or other errors occur
     */
    boolean lendBook(String name, String title, String format) throws LibraryException;

    /**
     * Processes the return of a book from a user.
     *
     * @param name   the name of the user returning the book
     * @param title  the title of the book being returned
     * @param format the format of the book (PHYSICAL or DIGITAL)
     * @return the next user in the waiting list for the book if applicable, otherwise null
     * @throws LibraryException if the user never borrowed the book
     */
    User returnBook(String name, String title, String format) throws LibraryException;

    /**
     * Lists all active loans in the system.
     */
    void listLoans();

    /**
     * Updates the waiting list for a book and lends it to the next user in the queue.
     *
     * @param title the title of the book
     * @return the user who received the book from the waiting list, or null if no one was waiting
     * @throws LibraryException if an error occurs during lending
     */
    User updateWaitingList(String title) throws LibraryException;

    /**
     * Reserves a book in the inventory.
     *
     * @param title the title of the book to reserve
     * @throws LibraryException if an error occurs during reservation
     */
    void reserve(String title) throws LibraryException;

    /**
     * Vacates a reserved book in the inventory.
     *
     * @param title the title of the book to vacate
     * @return true if the reservation was removed, false otherwise
     * @throws LibraryException if an error occurs during vacating
     */
    boolean vacate(String title) throws LibraryException;
}

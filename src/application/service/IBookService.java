package application.service;

import common.LibraryException;

/**
 * Interface defining operations for managing books in the library system.
 */
public interface IBookService {

    /**
     * Adds a book to the library inventory in the specified format.
     *
     * @param title  the title of the book
     * @param format the format of the book ("PHYSICAL" or "DIGITAL")
     * @throws LibraryException if the book cannot be added or format is invalid
     */
    void addBook(String title, String format) throws LibraryException;

    /**
     * Removes a book from the library inventory in the specified format.
     * Also updates loans and waiting lists accordingly.
     *
     * @param title  the title of the book
     * @param format the format of the book ("PHYSICAL" or "DIGITAL")
     * @throws LibraryException if the book does not exist or cannot be removed
     */
    void removeBook(String title, String format) throws LibraryException;

    /**
     * Removes all book copies loaned to a user as part of the user removal process.
     * This operation is triggered indirectly when a user is deleted, not by an explicit
     * book removal request. Ensures that the library state remains consistent by
     * cleaning up any loans tied to the removed user.
     *
     * @param title the title of the book currently loaned to the removed user
     * @throws LibraryException if an error occurs during the cleanup
     */
    void removeBookByUserDeletion(String title) throws LibraryException;

    /**
     * Lists all available books in the library inventory.
     *
     * @throws LibraryException if there are no available books
     */
    void listAvailableBooks() throws LibraryException;

    /**
     * Displays the waiting list of users for a given book title.
     *
     * @param title the title of the book
     */
    void showWaitingList(String title);

    /**
     * Sorts all books in the library inventory by title in inverse order
     * using the Quicksort algorithm and prints them with their current availability.
     * <p>
     * Note: Although TimSort would be more suitable for this type of data,
     * Quicksort has been chosen for academic purposes, to demonstrate
     * one of the algorithms learned in the Data Structures and Algorithms course.
     *
     * @throws LibraryException if there are no books to sort
     */
    void sortInverseBooksByTitleQuicksort() throws LibraryException;
}
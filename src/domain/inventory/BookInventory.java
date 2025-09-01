package domain.inventory;

import common.LibraryException;
import domain.book.DBook;
import domain.book.PBook;

/**
 * Represents the inventory of a book in the library, supporting
 * both physical (PBook) and digital (DBook) copies.
 */
public interface BookInventory {

    /**
     * Returns the title of the book.
     *
     * @return the book title
     */
    String getTitle();

    /**
     * Compares the book title with the given string ignoring case.
     *
     * @param pivot the string to compare with
     * @return a negative integer, zero, or a positive integer as this title
     *         is less than, equal to, or greater than the pivot
     */
    int compareToIgnoreCase(String pivot);

    // ----------------------
    // Physical Books (PBooks)
    // ----------------------

    /** @return true if a physical book exists in the inventory */
    boolean hasPBook();

    /** @return the physical book object (PBook) */
    PBook getPBook();

    /** Adds a physical book copy to the inventory */
    void addPBook();

    /**
     * Removes a physical book copy from the inventory.
     *
     * @return the updated physical book object
     * @throws LibraryException if no physical copy exists or no copies are available
     */
    PBook removePBook() throws LibraryException;

    /**
     * Removes a physical book copy triggered by a user removal action.
     *
     * @return the updated physical book object
     * @throws LibraryException if an error occurs
     */
    PBook removePBookByRemoveUser() throws LibraryException;

    /**
     * Loans a physical book to a user.
     *
     * @return true if the book was successfully loaned, false if no copies available
     * @throws LibraryException if the book is not registered
     */
    boolean loanPBook() throws LibraryException;

    /**
     * Returns a previously loaned physical book.
     *
     * @throws LibraryException if the book is not registered
     */
    void returnPBook() throws LibraryException;

    /**
     * Reserves a physical book.
     *
     * @throws LibraryException if the book is not registered
     */
    void reservePBook() throws LibraryException;

    /**
     * Vacates a previously reserved physical book.
     *
     * @return true if successful
     * @throws LibraryException if the book is not registered
     */
    boolean vacatePBook() throws LibraryException;

    // ----------------------
    // Digital Books (DBooks)
    // ----------------------

    /** @return true if a digital book exists in the inventory */
    boolean hasDBook();

    /** @return the digital book object (DBook) */
    DBook getDBook();

    /** Adds a digital book to the inventory
     *
     * @throws LibraryException if a digital copy already exists
     */
    void addDBook() throws LibraryException;

    /**
     * Removes the digital book from the inventory.
     *
     * @return the removed digital book object
     * @throws LibraryException if the digital book is not registered
     */
    DBook removeDBook() throws LibraryException;
}
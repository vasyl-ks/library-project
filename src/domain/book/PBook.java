package domain.book;

/**
 * Represents a physical book in the library system.
 * A PBook has a limited number of copies and can be reserved or loaned.
 */
public class PBook extends Book {

    /** Number of available copies not reserved. */
    private int availableCopies = 0;

    /** Total number of copies of this book. */
    private int totalCopies = 0;

    /** Number of copies currently reserved. */
    private int reservedCopies = 0;

    /**
     * Constructs a new physical book with the specified title.
     * The format is automatically set to "PHYSICAL".
     *
     * @param title the title of the physical book
     */
    public PBook(String title) {
        super(title, BookFormat.PHYSICAL.toString());
    }

    /**
     * Checks if the book has any available copies for loan (excluding reserved copies).
     *
     * @return true if at least one copy is available, false otherwise
     */
    public boolean isAvailable() {
        return availableCopies - reservedCopies > 0;
    }

    /**
     * Returns the number of available copies for loan (excluding reserved copies).
     *
     * @return available copies
     */
    public int getAvailableCopies() {
        return availableCopies - reservedCopies;
    }

    /**
     * Returns the total number of copies of this book.
     *
     * @return total copies
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Adds one copy to both total and available copies.
     */
    public void addCopies() {
        totalCopies += 1;
        availableCopies += 1;
    }

    /**
     * Removes one copy from both total and available copies.
     */
    public void removeCopies() {
        availableCopies -= 1;
        totalCopies -= 1;
    }

    /**
     * Adds one reserved copy.
     */
    public void addReservedCopies() {
        reservedCopies += 1;
    }

    /**
     * Removes one reserved copy.
     */
    public void removeReservedCopies() {
        reservedCopies -= 1;
    }

    /**
     * Removes one copy due to user removal (does not affect available copies).
     */
    public void removeCopiesByRemoveUser() {
        totalCopies -= 1;
    }

    /**
     * Loans one copy by decrementing the available copies.
     */
    public void loanCopies() {
        availableCopies -= 1;
    }

    /**
     * Returns one copy by incrementing the available copies.
     */
    public void returnCopies() {
        availableCopies += 1;
    }

    /**
     * Returns a string representation of the physical book,
     * showing title, format, and the number of available/total copies.
     *
     * @return a formatted string representing the physical book
     */
    @Override
    public String toString() {
        return super.toString() + (availableCopies - reservedCopies) + "/" + totalCopies + " available.";
    }
}
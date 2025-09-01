package domain.book;

/**
 * Abstract base class representing a book in the library system.
 * Contains common properties shared by all book types, such as title and format.
 */
public abstract class Book {

    /** Title of the book */
    protected final String title;

    /** Format of the book (e.g., "PHYSICAL" or "DIGITAL") */
    protected final String format;

    /**
     * Constructs a new Book with the specified title and format.
     *
     * @param title  the title of the book
     * @param format the format of the book
     */
    public Book(String title, String format) {
        this.title = title;
        this.format = format;
    }

    /**
     * Returns the title of the book.
     *
     * @return the book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the format of the book.
     *
     * @return the book format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Returns a string representation of the book, including its title and format.
     *
     * @return a formatted string representing the book
     */
    @Override
    public String toString() {
        return title + " - (" + format + "): ";
    }
}
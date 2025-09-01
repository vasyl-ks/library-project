package domain.book;

/**
 * Represents a digital book in the library system.
 * A DBook is always in digital format and has unlimited availability.
 */
public class DBook extends Book {

    /**
     * Constructs a new digital book with the specified title.
     * The format is automatically set to "DIGITAL".
     *
     * @param title the title of the digital book
     */
    public DBook(String title) {
        super(title, BookFormat.DIGITAL.toString());
    }

    /**
     * Returns a string representation of the digital book,
     * indicating its title, format, and unlimited availability.
     *
     * @return a formatted string representing the digital book
     */
    @Override
    public String toString() {
        return super.toString() + "  ∞  available.";
    }
}

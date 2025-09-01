package domain.inventory;

import common.LibraryException;
import domain.book.DBook;
import domain.book.PBook;

/**
 * Implementation of BookInventory that manages physical and digital
 * copies of a book in the library.
 */
public class BookInventoryImpl implements BookInventory {

    /** Title of the book */
    private final String title;

    /** Physical book, null if none exists */
    private PBook pBook;

    /** Digital book, null if none exists */
    private DBook dBook;


    /**
     * Constructs a new BookInventoryImpl with the given title.
     *
     * @param title the title of the book
     */
    public BookInventoryImpl(String title) {
        this.title = title;
    }

    /** {@inheritDoc} */
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public int compareToIgnoreCase(String pivot) {
        if (pivot == null) {
            throw new IllegalArgumentException("Pivot cannot be null");
        }
        return this.title.compareToIgnoreCase(pivot);
    }

    // ----------------------
    // PBooks
    // ----------------------

    /** {@inheritDoc} */
    @Override
    public boolean hasPBook() { return pBook != null; }

    /** {@inheritDoc} */
    @Override
    public PBook getPBook() {
        return pBook;
    }

    /** {@inheritDoc} */
    @Override
    public void addPBook() {
        if (pBook == null) pBook = new PBook(title);
        pBook.addCopies();
    }

    /** {@inheritDoc} */
    @Override
    public PBook removePBook() throws LibraryException {
        if(pBook == null) {
            throw new LibraryException("Physical copy of \"" + title +  "\", not registered in the library.");
        }
        if(!pBook.isAvailable()) {
            throw new LibraryException("Physical copy of \"" + title +  "\", no copies available to remove.");
        }
        pBook.removeCopies();
        if(pBook.getTotalCopies() < 1) pBook = null;
        return pBook;
    }

    /** {@inheritDoc} */
    @Override
    public PBook removePBookByRemoveUser() {
        pBook.removeCopiesByRemoveUser();
        if(pBook.getTotalCopies() < 1) pBook = null;
        return pBook;
    }

    /** {@inheritDoc} */
    @Override
    public boolean loanPBook() throws LibraryException {
        if(pBook == null) {
            throw new LibraryException("Physical copy of \"" + title +  "\", not registered in the library.");
        }
        if(!pBook.isAvailable()) {
            System.out.println("Physical copy of \"" + title +  "\", no copies available to loan.");
            return false;
        }
        pBook.loanCopies();
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void returnPBook() throws LibraryException {
        if(pBook == null) {
            throw new LibraryException("Physical copy of \"" + title +  "\", not registered in the library.");
        }
        pBook.returnCopies();
    }

    /** {@inheritDoc} */
    @Override
    public void reservePBook() throws LibraryException {
        if(pBook == null) {
            throw new LibraryException("Physical copy of \"" + title +  "\", not registered in the library.");
        }
        if(pBook.getAvailableCopies() < 1) {
            System.out.println("Physical copy of \"" + title +  "\", no copies available to reserve.");
        }
        pBook.addReservedCopies();
    }

    /** {@inheritDoc} */
    @Override
    public boolean vacatePBook() throws LibraryException {
        if(pBook == null) {
            throw new LibraryException("Physical copy of \"" + title +  "\", not registered in the library.");
        }
        pBook.removeReservedCopies();
        return true;
    }

    // ----------------------
    // DBooks
    // ----------------------

    /** {@inheritDoc} */
    @Override
    public boolean hasDBook() { return dBook != null; }

    /** {@inheritDoc} */
    @Override
    public DBook getDBook() {
        return dBook;
    }

    /** {@inheritDoc} */
    @Override
    public void addDBook() throws LibraryException {
        if (dBook == null) dBook = new DBook(title);
        else throw new LibraryException("Digital copy of \"" + title +  "\", already registered in the library.");
    }

    /** {@inheritDoc} */
    @Override
    public DBook removeDBook() throws LibraryException {
        if(dBook == null) {
            throw new LibraryException("Digital copy of \"" + title +  "\", not registered in the library.");
        }
        DBook temp = dBook;
        dBook = null;
        return temp;
    }
}

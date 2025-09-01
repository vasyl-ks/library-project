package application.service.impl;

import application.service.IBookService;
import application.service.ILibraryService;
import application.service.ILoanService;
import application.service.IUserService;
import common.LibraryException;
import domain.book.Book;
import domain.book.BookFormat;
import domain.user.User;
import infrastructure.repository.LibraryRepository;
import common.LibraryLogger;


/**
 * Implementation of ILibraryService, handling all library operations
 * by delegating to user, book, and loan services, while logging events.
 */
public class LibraryService implements ILibraryService {

    /** Repository that stores all library data, including users, books, and loans. */
    private final LibraryRepository repo;

    /** Logger responsible for recording all library events and actions. */
    private final LibraryLogger libraryLogger;

    /** Service responsible for managing library users. */
    private final IUserService userService;

    /** Service responsible for managing library books. */
    private final IBookService bookService;

    /** Service responsible for managing loans and reservations. */
    private final ILoanService loanService;

    /**
     * Constructs a new LibraryService and initializes
     * repository, logger, and sub-services.
     */
    public LibraryService() {
        this.repo = new LibraryRepository();
        this.userService = new UserService(repo);
        this.libraryLogger = new LibraryLogger(repo);
        this.bookService = new BookService(repo);
        this.loanService = new LoanService(repo);
    }

    // ----------------------
    // User methods
    // ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(String name) throws LibraryException {
        userService.addUser(name);
        libraryLogger.logEvent("User \"" + name + "\", successfully added.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(String name) throws LibraryException {
        if(!validUserName(name)) { throw new LibraryException("User \"" + name + "\", not found"); }

        User user = userService.removeUser(name);
        for(Book book : repo.getLoansByUser().get(user)) {
            if (book.getFormat().equals(BookFormat.PHYSICAL.toString())) {
                bookService.removeBookByUserDeletion(book.getTitle());
            }
        }
        libraryLogger.logEvent("User \"" + name + "\", successfully removed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listUsers() throws LibraryException {
        userService.listUsers();
    }

    // ----------------------
    // Book methods
    // ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBook(String title, String format) throws LibraryException {
        BookFormat fixedFormat = BookFormat.fromString(format);

        bookService.addBook(title, fixedFormat.toString());
        User user = loanService.updateWaitingList(title);
        if(user != null)
            libraryLogger.logEvent("User \"" + user.getName() + ", joined queue for \"" + title + "\"  (" + fixedFormat + ").");
        libraryLogger.logEvent("Book \"" + title + "\" (" + fixedFormat + "), successfully added.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBook(String title, String format) throws LibraryException {
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }
        BookFormat fixedFormat = BookFormat.fromString(format);

        bookService.removeBook(title, fixedFormat.toString());
        libraryLogger.logEvent("Book \"" + title + "\" (" + fixedFormat + "), successfully removed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listBooks() throws LibraryException {
        bookService.listAvailableBooks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showWaitingList(String title) throws LibraryException {
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }

        bookService.showWaitingList(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortInverse() throws LibraryException {
        bookService.sortInverseBooksByTitleQuicksort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reserve(String title) throws LibraryException {
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }

        loanService.reserve(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vacate(String title) throws LibraryException {
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }

        if(loanService.vacate(title))
            loanService.updateWaitingList(title);
    }

    // ----------------------
    // Loan methods
    // ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void lendBook(String name, String title, String format) throws LibraryException {
        if(!validUserName(name)) { throw new LibraryException("User \"" + name + "\", not found"); }
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }
        BookFormat fixedFormat = BookFormat.fromString(format);

        if(!loanService.lendBook(name, title,  fixedFormat.toString()))
            libraryLogger.logEvent("User \"" + name + ", joined queue for \"" + title + "\"  (" + fixedFormat + ").");
        else libraryLogger.logEvent("User \"" + name + ", took loan for \"" + title + "\"  (" + fixedFormat + ").");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void returnBook(String name, String title, String format) throws LibraryException {
        if(!validUserName(name)) { throw new LibraryException("User \"" + name + "\", not found"); }
        if(!validBookName(title)) { throw new LibraryException("Book \"" + title +  "\", not found"); }
        BookFormat fixedFormat = BookFormat.fromString(format);

        User user = loanService.returnBook(name, title, fixedFormat.toString());
        libraryLogger.logEvent("User \"" + name + ", returned loan for \"" + title + "\"  (" + fixedFormat + ").");
        if(user != null) {
            libraryLogger.logEvent("User \"" + user.getName() + ", took loan for \"" + title + "\"  (" + fixedFormat + ").");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listLoans() {
        loanService.listLoans();
    }

    // ----------------------
    // Other methods
    // ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void showEventHistory() {
        libraryLogger.showEventHistory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LibraryRepository accessRepository() {
        return repo;
    }

    // ----------------------
    // Private helpers
    // ----------------------

    /**
     * Checks if a user with the given name exists in the repository.
     *
     * @param name Name of the user to check.
     * @return true if the user exists, false otherwise.
     */
    private boolean validUserName(String name) {
        return repo.getUserMap().containsKey(name);
    }

    /**
     * Checks if a book with the given title exists in the repository inventory.
     *
     * @param title Title of the book to check.
     * @return true if the book exists, false otherwise.
     */
    private boolean validBookName(String title) {
        return repo.getInventory().containsKey(title);
    }
}
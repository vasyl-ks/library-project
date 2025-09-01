package infrastructure.repository;

import common.dataStructures.list.LibraryList;
import common.dataStructures.list.impl.SLLLibraryList;
import common.dataStructures.map.LibraryMap;
import common.dataStructures.map.impl.HashLibraryMap;
import common.dataStructures.queue.LibraryQueue;
import common.dataStructures.set.LibrarySet;
import domain.book.Book;
import domain.inventory.BookInventory;
import domain.user.User;

/**
 * Repository class storing all library data in memory, including users, books,
 * loans, waiting lists, and event history. Uses custom library data structures
 * for maps, lists, sets, and queues.
 */
public class LibraryRepository {

    /** Estimated number of books in the library for initial map sizing. */
    private final int ESTIMATED_LIBRARY_SIZE = 300;

    /** Estimated number of users for initial map sizing. */
    private final int ESTIMATED_USER_SIZE = 100;

    /**
     * Returns the estimated number of users, used for map initialization.
     * @return estimated user count
     */
    public int getESTIMATED_USER_SIZE() { return ESTIMATED_USER_SIZE; }

    /**
     * Returns the estimated number of books, used for map initialization.
     * @return estimated library size
     */
    public int getESTIMATED_LIBRARY_SIZE() { return ESTIMATED_LIBRARY_SIZE; }

    /** Map of usernames to User objects. */
    private final LibraryMap<String, User> userMap = new HashLibraryMap<>(ESTIMATED_USER_SIZE);

    /** Map of book titles to their inventory details. */
    private final LibraryMap<String, BookInventory> inventory = new HashLibraryMap<>(ESTIMATED_LIBRARY_SIZE);

    /** Map of book titles to waiting lists of users who requested them. */
    private final LibraryMap<String, LibraryQueue<User>> waitingListMap = new HashLibraryMap<>(ESTIMATED_USER_SIZE);

    /** List storing the history of events in the library. */
    private final LibraryList<String> eventHistory = new SLLLibraryList<>();

    /** Map of users to the books they currently have on loan. */
    private final LibraryMap<User, LibrarySet<Book>> loansByUser = new HashLibraryMap<>(ESTIMATED_USER_SIZE);

    /** Map of books to the users who currently have them on loan. */
    private final LibraryMap<Book, LibrarySet<User>> loansByBook = new HashLibraryMap<>(ESTIMATED_LIBRARY_SIZE);

    /**
     * Returns the map of users by name.
     * @return user map
     */
    public LibraryMap<String, User> getUserMap() { return userMap; }

    /**
     * Returns the map of book inventories by title.
     * @return book inventory map
     */
    public LibraryMap<String, BookInventory> getInventory() { return inventory; }

    /**
     * Returns the map of waiting lists for each book.
     * @return waiting list map
     */
    public LibraryMap<String, LibraryQueue<User>> getWaitingListMap() { return waitingListMap; }

    /**
     * Returns the history of library events.
     * @return event history list
     */
    public LibraryList<String> getEventHistory() { return eventHistory; }

    /**
     * Returns the mapping of users to books they currently have on loan.
     * @return loans by user map
     */
    public LibraryMap<User, LibrarySet<Book>> getLoansByUser() { return loansByUser; }

    /**
     * Returns the mapping of books to users who currently have them on loan.
     * @return loans by book map
     */
    public LibraryMap<Book, LibrarySet<User>> getLoansByBook() { return loansByBook; }
}


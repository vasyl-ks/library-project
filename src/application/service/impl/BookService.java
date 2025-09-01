package application.service.impl;

import application.service.IBookService;
import common.LibraryException;
import common.dataStructures.list.LibraryList;
import common.dataStructures.list.impl.SLLLibraryList;
import common.dataStructures.map.LibraryMap;
import common.dataStructures.queue.LibraryQueue;
import common.dataStructures.set.LibrarySet;
import common.dataStructures.set.impl.HashLibrarySet;
import domain.book.Book;
import domain.book.BookFormat;
import domain.inventory.BookInventory;
import domain.inventory.BookInventoryImpl;
import domain.book.DBook;
import domain.user.User;
import infrastructure.repository.LibraryRepository;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of IBookService, managing books in the library inventory.
 * Supports adding, removing, listing, and sorting books, as well as handling waiting lists.
 */
public class BookService implements IBookService {

    /** Reference to the library repository for accessing data structures */
    private final LibraryRepository repo;

    /**
     * Constructs a BookService with the given library repository.
     *
     * @param repo the library repository containing inventory, loans, and waiting lists
     */
    public BookService(LibraryRepository repo) {
        this.repo = repo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBook(String title, String format) throws LibraryException {
        LibraryMap<String, BookInventory> inventory = repo.getInventory();
        BookInventory bookInventory = inventory.computeIfAbsent(title, k -> new BookInventoryImpl(title));

        if(format.equals(BookFormat.DIGITAL.toString())) {
            bookInventory.addDBook();
        }
        else {
            bookInventory.addPBook();
        }
        System.out.println("Book \"" + title + "\" (" + format + "), successfully added.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBook(String title, String format) throws LibraryException {
        LibraryMap<String, BookInventory> inventory = repo.getInventory();
        BookInventory bookInventory = inventory.get(title);
        if(format.equals(BookFormat.DIGITAL.toString())) {
            // Remove the book from the inventory
            DBook dBook = bookInventory.removeDBook();

            // Remove from all users who have it on loan
            LibrarySet<User> users = repo.getLoansByBook().get(dBook);
            if (users != null) {
                // We use a new HashSet to avoid ConcurrentModificationException
                LibrarySet<User> usersCopy = new HashLibrarySet<>(users.size());
                users.forEach(usersCopy::add);

                for (User user : usersCopy) {
                    LibrarySet<Book> books = repo.getLoansByUser().get(user);
                    if (books != null) {
                        books.remove(dBook);
                        if (books.isEmpty()) {
                            repo.getLoansByUser().remove(user);
                        }
                    }
                    users.remove(user);
                }
                // If no users remain, we remove the entry from the map
                if (users.isEmpty()) {
                    repo.getLoansByBook().remove(dBook);
                }
            }

            // Remove queue
            repo.getWaitingListMap().remove(title);
        }
        else {
            if(bookInventory.removePBook() == null) {
                // Remove queue
                repo.getWaitingListMap().remove(title);
            }
        }
        System.out.println("Book \"" + title + "\" (" + format + "), successfully removed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBookByUserDeletion(String title) throws LibraryException {
        LibraryMap<String, BookInventory> inventory = repo.getInventory();
        BookInventory bookInventory = inventory.get(title);
        if(bookInventory.removePBookByRemoveUser() == null) {
            // Remove queue
            repo.getWaitingListMap().remove(title);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listAvailableBooks() throws LibraryException {
        LibraryMap<String, BookInventory> inventory = repo.getInventory();

        if (inventory.isEmpty()) {
            throw new LibraryException("No available books.");
        }
        System.out.println("--- Available books ---");
        inventory.values().forEach((bookInventory) -> {
            if(bookInventory.hasPBook()) {
                System.out.println(bookInventory.getPBook().toString());
            }
            if(bookInventory.hasDBook())
                System.out.println(bookInventory.getDBook().toString());
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showWaitingList(String title) {
        LibraryQueue<User> queue = repo.getWaitingListMap().get(title.toLowerCase());
        if (queue == null || queue.isEmpty()) {
            System.out.println("No users in queue for this book.");
            return;
        }
        System.out.println("Queue for \"" + title + "\":");
        int pos = 1;
        for (User u : queue) System.out.println(pos++ + ". " + u.getName());
    }

    // ----------------------
    // Sort by tile
    // ----------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortInverseBooksByTitleQuicksort() throws LibraryException {
        LibraryList<BookInventory> books = new SLLLibraryList<>(repo.getInventory().values());
        if(books.isEmpty()) {
            throw new LibraryException("No books to sort.");
        }
        quickSort(books, 0, books.size() - 1);

        System.out.println("--- Available books ---");
        // Imprimir cada libro con estado
        for (BookInventory bookInventory : books) {
            if(bookInventory.hasPBook()) {
                System.out.println(bookInventory.getPBook().toString());
            }
            if(bookInventory.hasDBook()) {
                System.out.println(bookInventory.getDBook().toString());
            }
        }
    }

    /** Performs the quicksort on the list of books. */
    private void quickSort(LibraryList<BookInventory> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    /** Partitions the list for quicksort algorithm. */
    private int partition(LibraryList<BookInventory> list, int low, int high) {
        String pivot = list.get(high).getTitle();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).compareToIgnoreCase(pivot) >= 0) {
                i++;
                Collections.swap((List<?>) list, i, j);
            }
        }
        Collections.swap((List<?>) list, i + 1, high);
        return i + 1;
    }
}

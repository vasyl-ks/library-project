package application.service.impl;

import application.service.ILoanService;
import common.LibraryException;
import common.dataStructures.queue.LibraryQueue;
import common.dataStructures.queue.impl.ArrayLibraryQueue;
import common.dataStructures.set.LibrarySet;
import common.dataStructures.set.impl.HashLibrarySet;
import domain.book.Book;
import domain.book.BookFormat;
import domain.book.DBook;
import domain.book.PBook;
import domain.inventory.BookInventory;
import domain.user.User;
import infrastructure.repository.LibraryRepository;
import java.util.Optional;

/**
 * Implementation of the ILoanService interface that manages book loans,
 * returns, reservations, and waiting lists in the library system.
 */
public class LoanService implements ILoanService {

    /** Repository used to store users, books, loans, and waiting lists. */
    private final LibraryRepository repo;

    /**
     * Constructs a LoanService with the specified repository.
     *
     * @param repo the repository used to manage library data
     */
    public LoanService(LibraryRepository repo) {
        this.repo = repo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean lendBook(String name, String title, String format) throws LibraryException {
        User user = repo.getUserMap().get(name);

        LibrarySet<Book> userBooks = repo.getLoansByUser().get(user);
        if (userBooks != null && userBooks.stream().anyMatch(b -> b.getTitle().equals(title))) {
            throw new LibraryException("User \"" + name + ", already loaned \"" + title + "\".");
        }

        BookInventory bookInventory = repo.getInventory().get(title);
        if(format.equals(BookFormat.PHYSICAL.toString())) {
            if(!bookInventory.loanPBook()) {
                LibraryQueue<User> queue = repo.getWaitingListMap().computeIfAbsent(title, t -> new ArrayLibraryQueue<>());
                if(queue.contains(user)) {
                    throw new LibraryException("User \"" + name + ", already in queue for \"" + title + "\" (" + format + ").");
                }
                queue.add(user);
                System.out.println("User \"" + name + ", joined queue for \"" + title + "\"  (" + format + ").");
                return false;
            }
            PBook pBook = bookInventory.getPBook();
            repo.getLoansByUser()
                    .computeIfAbsent(user, u -> new HashLibrarySet<>(repo.getESTIMATED_LIBRARY_SIZE()))
                    .add(pBook);
            repo.getLoansByBook()
                    .computeIfAbsent(pBook, b -> new HashLibrarySet<>(repo.getESTIMATED_USER_SIZE()))
                    .add(user);
        } else {
            //bookInventory.loanEBook(1); Not needed
            DBook dBook = bookInventory.getDBook();
            repo.getLoansByUser()
                    .computeIfAbsent(user, u -> new HashLibrarySet<>(repo.getESTIMATED_LIBRARY_SIZE()))
                    .add(dBook);
            repo.getLoansByBook()
                    .computeIfAbsent(dBook, b -> new HashLibrarySet<>(repo.getESTIMATED_USER_SIZE()))
                    .add(user);
        }
        System.out.println("User \"" + name + ", took loan for \"" + title + "\"  (" + format + ").");
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User returnBook(String name, String title, String format) throws LibraryException {
        User user = repo.getUserMap().get(name);

        LibrarySet<Book> userBooks = repo.getLoansByUser().get(user);
        if (userBooks == null || userBooks.stream().noneMatch(b -> b.getTitle().equals(title))) {
            throw new LibraryException("User \"" + name + ", never loaned \"" + title + "\".");
        }

        BookInventory bookInventory = repo.getInventory().get(title);
        if(format.equals(BookFormat.PHYSICAL.toString())) {
            bookInventory.returnPBook();

            PBook pBook = bookInventory.getPBook();
            Optional.ofNullable(repo.getLoansByUser().get(user)).ifPresent(books -> {
                books.remove(pBook);
                if (books.isEmpty()) {
                    repo.getLoansByUser().remove(user);
                }
            });
            Optional.ofNullable(repo.getLoansByBook().get(pBook)).ifPresent(users -> {
                users.remove(user);
                if (users.isEmpty()) {
                    repo.getLoansByBook().remove(pBook);
                }
            });

            System.out.println("User \"" + name + ", returned loan for \"" + title + "\"  (" + format + ").");
            return updateWaitingList(title);
        }
        else {
            DBook dBook = bookInventory.getDBook();
            Optional.ofNullable(repo.getLoansByUser().get(user)).ifPresent(books -> {
                books.remove(dBook);
                if (books.isEmpty()) {
                    repo.getLoansByUser().remove(user);
                }
            });
            Optional.ofNullable(repo.getLoansByBook().get(dBook)).ifPresent(users -> {
                users.remove(user);
                if (users.isEmpty()) {
                    repo.getLoansByBook().remove(dBook);
                }
            });
            System.out.println("User \"" + name + ", returned loan for \"" + title + "\"  (" + format + ").");
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void listLoans() {
        if (repo.getLoansByUser().isEmpty()) {
            System.out.println("No active loans.");
            return;
        }

        System.out.println("--- Active loans ---");
        repo.getLoansByUser().forEach((user, bookSet) ->
                bookSet.forEach(book -> System.out.println("User \"" + user.getName() + ", returned loan for \"" + book.getTitle() + "\"  (" + book.getFormat() + ")."))
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateWaitingList(String title) throws LibraryException {
        LibraryQueue<User> queue = repo.getWaitingListMap().get(title);
        if (queue != null && !queue.isEmpty()) {
            User nextUser = queue.remove();
            String name = nextUser.getName();
            System.out.println("User \"" + name + ", joined queue for \"" + title + "\"  (" + BookFormat.PHYSICAL + ").");
            lendBook(name, title, BookFormat.PHYSICAL.toString());
            return nextUser;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reserve(String title) throws LibraryException {
        BookInventory bookInventory = repo.getInventory().get(title);
        System.out.println("Added reserve for \"" + title + "\"  (" + BookFormat.PHYSICAL + ").");
        bookInventory.reservePBook();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean vacate(String title) throws LibraryException {
        BookInventory bookInventory = repo.getInventory().get(title);
        System.out.println("Removed reserve for \"" + title + "\"  (" + BookFormat.PHYSICAL + ").");
        return bookInventory.vacatePBook();
    }
}

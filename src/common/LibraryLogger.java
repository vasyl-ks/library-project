package common;

import infrastructure.repository.LibraryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides logging functionality for the library system.
 * Stores and displays a history of events, each timestamped
 * with the current date and time.
 */
public class LibraryLogger {

    /** Formatter for timestamps in the format yyyy-MM-dd HH:mm:ss. */
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Repository that stores the library's event history. */
    private final LibraryRepository repo;

    /**
     * Constructs a logger associated with the given library repository.
     *
     * @param repo the library repository where events will be recorded
     */
    public LibraryLogger(LibraryRepository repo) {
        this.repo = repo;
    }

    /**
     * Records an event in the library's history with a timestamp.
     *
     * @param event a description of the event to log
     */
    public void logEvent(String event) {
        String timestamp = LocalDateTime.now().format(dtf);
        repo.getEventHistory().insert(timestamp + " - " + event, repo.getEventHistory().size() - 1);
    }

    /**
     * Prints the full history of recorded events to the console.
     * If no events are registered, a message is shown instead.
     */
    public void showEventHistory() {
        if (repo.getEventHistory().isEmpty()) {
            System.out.println("No activity registered.");
            return;
        }
        System.out.println("--- Activity ---");
        repo.getEventHistory().forEach(System.out::println);
    }
}

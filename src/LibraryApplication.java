import application.service.ILibraryService;
import application.service.impl.LibraryService;
import domain.book.BookFormat;
import domain.inventory.BookInventory;
import common.LibraryException;
import java.util.Scanner;

/**
 * Main console application for interacting with the library system.
 * <p>
 * Supports commands for managing users, books, loans, reservations, and
 * viewing the activity log. Accepts input via the console and delegates
 * operations to an {@link ILibraryService} implementation.
 */
public class LibraryApplication {

    /**
     * Entry point for the library console application.
     * <p>
     * Displays the library art, prompts the user for commands, and executes
     * them in a loop until the user exits.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ILibraryService library = new LibraryService();
        Scanner sc = new Scanner(System.in);

        printLibraryArt();
        System.out.println("Welcome to the Library. Type 'help' to see the available commands.");

        while (true) {
            System.out.print("\n> ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] commands = input.split(";");
            for (String rawCmd : commands) {
                String cmdLine = rawCmd.trim();
                if (cmdLine.isEmpty()) continue;

                String[] parts = cmdLine.split("\\s+");
                String command = parts[0].toLowerCase();

                try {
                    switch (command) {
                        case "h", "help" -> showHelp();
                        case "u", "users" -> library.listUsers();
                        case "b", "books" -> library.listBooks();
                        case "+u", "+user" -> {
                            String name = (parts.length >= 2) ? parts[1] : ask("Name: ", sc);
                            library.addUser(name);
                        }
                        case "-u", "-user" -> {
                            String name = (parts.length >= 2) ? parts[1] : ask("Name: ", sc);
                            library.removeUser(name);
                        }
                        case "+b", "+book" -> {
                            String title = (parts.length >= 2) ? parts[1] : ask("Title: ", sc);
                            String format = (parts.length >= 3) ? parts[2] : ask("Format (physical/p or digital/d): ", sc);
                            library.addBook(title, format);
                        }
                        case "-b", "-book" -> {
                            String title = (parts.length >= 2) ? parts[1] : ask("Title: ", sc);
                            String format = (parts.length >= 3) ? parts[2] : getFormat(title, sc, library);
                            library.removeBook(title, format);
                        }
                        case "+l", "+loan" -> {
                            String user = (parts.length >= 2) ? parts[1] : ask("User: ", sc);
                            String title = (parts.length >= 3) ? parts[2] : ask("Title: ", sc);
                            String format = (parts.length >= 4) ? parts[3].toLowerCase() : getFormat(title, sc, library);
                            library.lendBook(user, title, format);
                        }
                        case "-l", "-loan" -> {
                            String user = (parts.length >= 2) ? parts[1] : ask("User: ", sc);
                            String title = (parts.length >= 3) ? parts[2] : ask("Title: ", sc);
                            String format = (parts.length >= 4) ? parts[3].toLowerCase() : getFormat(title, sc, library);
                            library.returnBook(user, title, format);
                        }
                        case "l", "loans" -> library.listLoans();
                        case "+r", "+reserve" -> {
                            String title = (parts.length >= 2) ? parts[1] : ask("Title: ", sc);
                            library.reserve(title);
                        }
                        case "-r", "-reserve" -> {
                            String title = (parts.length >= 2) ? parts[1] : ask("Title: ", sc);
                            library.vacate(title);
                        }
                        case "q", "queue" -> {
                            String title = (parts.length >= 2) ? parts[1] : ask("Title: ", sc);
                            library.showWaitingList(title);
                        }
                        case "s", "sort" -> library.sortInverse();
                        case "a", "activity" -> library.showEventHistory();
                        case "e", "exit" -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Unknown command. Type 'help' to see available commands.");
                    }
                } catch (LibraryException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Prompts the user with a question and reads a line of input.
     *
     * @param question the prompt to display to the user
     * @param sc       the Scanner used to read input
     * @return the trimmed user input
     */
    public static String ask(String question, Scanner sc) {
        System.out.print(question);
        return sc.nextLine().trim();
    }

    /**
     * Determines the format of a book for lending or removal, asking the
     * user if both formats are available.
     *
     * @param title   the book title
     * @param sc      the Scanner for user input
     * @param library the library service used to access book inventory
     * @return the selected or determined book format as a string
     * @throws LibraryException if the book title is not found
     */
    public static String getFormat(String title, Scanner sc, ILibraryService library) throws LibraryException {
        String format;
        BookInventory bookInventory = library.accessRepository().getInventory().get(title);
        if(bookInventory.hasPBook() && bookInventory.hasDBook()) {
            format = ask("Format (physical/p or digital/d): ", sc).toLowerCase().trim();
        } else if(bookInventory.hasPBook()) {
            format = BookFormat.DIGITAL.toString();
        } else {
            format = BookFormat.PHYSICAL.toString();
        }
        return format;
    }

    /**
     * Prints a help menu showing all available commands and their usage.
     */
    private static void showHelp() {
        System.out.println("""
        Available commands:
         h,  help                         - Show this help menu
         u,  users                        - List all users
        +u, +user name                    - Add a user
        -u, -user name                    - Remove a user
         b,  books                        - List all books
        +b, +book title format            - Add a book (physical/p or digital/d)
        -b, -book title (format)          - Remove a book (physical/p or digital/d)
        +l, +loan user title (format)     - Lend a book to a user
        -l, -loan user title (format)     - Return a book from a user
         l,  loans                        - List current loans
        +r, +reserve title                - Add a reserve to a book
        -r, -reserve title                - Remove a reserve to a book
         q,  queue title                  - Show waiting list for a book
         s,  sort                         - Sort books by title in descending order
         a,  activity                     - Show full activity log
         e,  exit                         - Exit the program
        """);
    }


    /**
     * Prints an ASCII art banner for the library at application startup.
     */
    public static void printLibraryArt() {
        String art =
"""
        
        __        __   _                            _          _   _            _     _ _                          _\s
        \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  | |   (_) |__  _ __ __ _ _ __ _   _| |
         \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\ | |   | | '_ \\| '__/ _` | '__| | | | |
          \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/ | |___| | |_) | | | (_| | |  | |_| |_|
           \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___| |_____|_|_.__/|_|  \\__,_|_|   \\__, (_)
                                                                                                             |___/  \s
""";

        System.out.println(art);
    }
}

package domain.book;

import common.LibraryException;
import common.dataStructures.map.LibraryMap;
import common.dataStructures.map.impl.HashLibraryMap;
import common.dataStructures.set.LibrarySet;
import common.dataStructures.set.impl.HashLibrarySet;

/**
 * Enumeration representing the possible book formats in the library system.
 * Each format has a display name and a set of string aliases for easy parsing.
 */
public enum BookFormat {

    /** Physical book format. */
    PHYSICAL("PHYSICAL", "P", "physical", "p"),

    /** Digital book format. */
    DIGITAL("DIGITAL", "D", "digital", "d");

    /** Display name of the format. */
    private final String displayName;

    /** Aliases for this format (lowercased). */
    private final LibrarySet<String> aliases;

    /** Lookup map to convert strings to BookFormat enums. */
    private static final LibraryMap<String, BookFormat> lookup = new HashLibraryMap<>(8);

    // Static block to populate the lookup map
    static {
        for (BookFormat bf : values()) {
            lookup.put(bf.displayName.toLowerCase(), bf);
            for (String alias : bf.aliases) {
                lookup.put(alias, bf);
            }
        }
    }

    /**
     * Constructs a BookFormat with a display name and optional aliases.
     *
     * @param displayName the official display name of the format
     * @param aliases     optional alternative strings representing this format
     */
    BookFormat(String displayName, String... aliases) {
        this.displayName = displayName;
        this.aliases = new HashLibrarySet<>(8);
        for (String a : aliases) {
            this.aliases.add(a.toLowerCase());
        }
    }

    /**
     * Converts a string into a BookFormat enum.
     *
     * @param input the input string representing a format
     * @return the corresponding BookFormat
     * @throws LibraryException if the input is null or does not match any known format
     */
    public static BookFormat fromString(String input) throws LibraryException {
        if (input == null) throw new LibraryException("Null format");
        BookFormat bf = lookup.get(input.toLowerCase());
        if (bf == null) throw new LibraryException("Invalid format: must be physical/f or digital/d");
        return bf;
    }

    /**
     * Returns the display name of the book format.
     *
     * @return the format's display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}
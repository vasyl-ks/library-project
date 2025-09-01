```
__        __   _                            _          _   _            _     _ _                          _
\ \      / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  | |   (_) |__  _ __ __ _ _ __ _   _| |
 \ \ /\ / / _ \ |/ __/ _ \| '_ ` _ \ / _ \ | __/ _ \  | __| '_ \ / _ \ | |   | | '_ \| '__/ _` | '__| | | | |
  \ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/ | |___| | |_) | | | (_| | |  | |_| |_|
   \_/\_/ \___|_|\___\___/|_| |_| |_|\___|  \__\___/   \__|_| |_|\___| |_____|_|_.__/|_|  \__,_|_|   \__, (_)
                                                                                                     |___/
```

# Library Management System

Library Management System is a Java command-line application for managing users, books, loans, and reservations. Built as a portfolio project, it showcases object-oriented design, custom data structures, algorithm implementation, and interface-driven architecture. The system combines practical functionality with clean, modular code, highlighting both technical skills and thoughtful software design.

---

## Features

- Add, remove, and list users.
- Add, remove, and list books (physical and digital formats).
- Lend and return books.
- Reserve and vacate books.
- View current loans and waiting lists.
- Sort books by title (descending).
- View a full activity log of all actions.
- Supports multiple commands in a single line separated by `;`.

---

## Rules
- Two users cannot have the same name.
- A user cannot have more than one copy of a book with the same title, regardless of format. They can have multiple books, but never more than one physical version or one digital version.
- Only one digital copy of a book can exist.
- To remove a book (or a single copy):
- If physical, it must be available in the library (not loaned out).
- If digital, it can be removed even if it is currently loaned.
- You can use the +reserve command to prevent a physical book from being loaned (limits available copies to 1).
- Removing a user will also remove all books that were loaned to them but not returned.
- If only one version of a book exists (either physical or digital, but not both), the format argument in -book, +loan, and -loan commands is optional.
- The reserve command does not require a format because only physical books can be reserved.

---

## Learnings from Courses (2024-2025)

This project was designed with the explicit goal of applying and demonstrating the key concepts learned in the courses throughout the 2024-2025 academic year. Rather than focusing solely on creating a functional library system, the emphasis is on integrating and showcasing knowledge from multiple areas in a single, coherent project.

### Human-Computer Interfaces (Interfaces Persona Computador)
- Development of a command-line interface (CLI) that allows direct system interaction, providing flexibility in input arguments and enabling expert users to perform complex actions quickly and concisely.
- The interface follows Norman’s principles, including feedback and error tolerance (through prevention and clear error messages), as well as Nielsen’s heuristics, such as visibility of system status, error prevention, flexibility and efficiency of use (with shortcuts and accelerators), minimalist aesthetic design, user documentation, and effective help systems to aid error recovery.

### Data Structures and Algorithms (Estructura de Datos y Algoritmos)
- Implementation of core data structures from scratch, including HashMaps, HashSets, ArrayQueues, SLLLists, and SLLListsWithPI, with proper separation of interface and implementation.
- Selection and rationale for each data structure in the project: HashMap for fast key-value retrieval, HashSet for ensuring unique collections of elements, ArrayQueue for efficient FIFO operations, SLLList for simple dynamic lists, and SLLListWithPI for enhanced linked list operations with positional iterators.
- Some data structure classes included partially provided code from the Polytechnic University of Valencia. These sections were completed to create fully functional implementations, combining guided exercises with independent development.
- Application of sorting algorithms taught during the course; Quicksort was chosen in this project for academic purposes to demonstrate an algorithm learned in class, although other algorithms like TimSort could be more suitable for real-world datasets.

### Programming Languages, Technologies, and Paradigms (Lenguajes, Tecnologías y Paradigmas de la Programación)
- Use of object-oriented concepts learned in class, such as inheritance, polymorphism, parametrization, method overloading, and implicit/explicit type coercion.
- Emphasis on abstraction through the use of interfaces to decouple implementation from specification and improve code modularity and maintainability.

---

## Getting Started

### Prerequisites

- Java 11 or higher

### Running the Application

1. Clone the repository:
```
git clone https://github.com/vasyl-ks/library-project.git
cd library-project
```

2. Compile and run:
```
javac -d out src/**/*.java
java -cp out LibraryApplication
```

3. Use the command-line interface:
```
Welcome to the Library. Type 'help' to see the available commands.
```

---

## Commands

| Command                                               | Description                             |
| ----------------------------------------------------- | --------------------------------------- |
| `h`, `help`                                           | Show this help menu                     |
| `u`, `users`                                          | List all users                          |
| `+u name`, `+user name`                               | Add a user                              |
| `-u name`, `-user name`                               | Remove a user                           |
| `b`, `books`                                          | List all books                          |
| `+b title format`, `+book title format`               | Add a book (physical/p or digital/d)    |
| `-b title (format)`, `-book title (format)`           | Remove a book                           |
| `+l user title (format)`, `+loan user title (format)` | Lend a book to a user                   |
| `-l user title (format)`, `-loan user title (format)` | Return a book from a user               |
| `l`, `loans`                                          | List current loans                      |
| `+r title`, `+reserve title`                          | Add a reservation to a book             |
| `-r title`, `-reserve title`                          | Remove a reservation from a book        |
| `q title`, `queue title`                              | Show waiting list for a book            |
| `s`, `sort`                                           | Sort books by title in descending order |
| `a`, `activity`                                       | Show full activity log                  |
| `e`, `exit`                                           | Exit the program                        |

---

## Examples

### Adding Users
```
+u Alice
```
User 'Alice' added.
```
+u Bob
```
User 'Bob' added.

### Adding Books
```
+b "Java Basics" p
```
Physical book 'Java Basics' added.
```
+b "Python Guide" d
```
Digital book 'Python Guide' added.

### Lending and Returning Books
```
+l Alice "Java Basics" p
```
Loan created: Alice borrowed 'Java Basics' (physical)
```
-l Alice "Java Basics" p
```
Loan returned: Alice returned 'Java Basics' (physical)

### Reservations and Waiting Lists
```
+r "Java Basics"
```
Reservation added for 'Java Basics'
```
q "Java Basics"
```
Waiting list for 'Java Basics': Alice, Bob

---

## Project Structure

```
src
├───LibraryApplication.java
├───application
│   └───service
│       │   IBookService.java
│       │   ILibraryService.java
│       │   ILoanService.java
│       │   IUserService.java
│       └───impl
│               BookService.java
│               LibraryService.java
│               LoanService.java
│               UserService.java
├───common
│   │   LibraryException.java
│   │   LibraryLogger.java
│   └───dataStructures
│       ├───list
│       │   │   LibraryList.java
│       │   │   LibraryListWithPI.java
│       │   └───impl
│       │           SLLLibraryList.java
│       │           SLLLibraryListWithPI.java
│       │           SLLNode.java
│       ├───map
│       │   │   LibraryMap.java
│       │   └───impl
│       │           HashLibraryEntry.java
│       │           HashLibraryMap.java
│       ├───queue
│       │   │   LibraryQueue.java
│       │   └───impl
│       │           ArrayLibraryQueue.java
│       └───set
│           │   LibrarySet.java
│           └───impl
│                   HashLibrarySet.java
├───domain
│   ├───book
│   │       Book.java
│   │       BookFormat.java
│   │       DBook.java
│   │       PBook.java
│   ├───inventory
│   │       BookInventory.java
│   │       BookInventoryImpl.java
│   └───user
│           User.java
└───infrastructure
    └───repository
            LibraryRepository.java
```

---

## License

This project is licensed under the MIT License. See LICENSE.txt for details.

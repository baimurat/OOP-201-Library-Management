package GUI;

import Entities.Book;
import Entities.Librarian;
import Entities.User;

public class Session {

    public static Book book;

    public static Librarian librarian;

    public static User user;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        Session.book = book;
    }

    public static Librarian getLibrarian() {
        return librarian;
    }

    public static void setLibrarian(Librarian librarian) {
        Session.librarian = librarian;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }
}

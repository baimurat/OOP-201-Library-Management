package Entities;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "book_id")
    private int book_id;

    @Column(name = "isbn")
    private int ISBN;

    @Column(name = "author")
    private String author;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "edition")
    private int edition;

    @Column(name = "booksAmount")
    private int BooksAmount;

    public Book() {

    }

    public Book(int ISBN, String author, String name, String status, int edition, int booksAmount) {
        this.ISBN = ISBN;
        this.author = author;
        this.name = name;
        this.status = status;
        this.edition = edition;
        this.BooksAmount = booksAmount;
    }

    public int getBooksAmount() {
        return BooksAmount;
    }

    public void setBooksAmount(int booksAmount) {
        BooksAmount = booksAmount;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return Integer.toString(ISBN);
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getId() {
        return book_id;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s edition", getName(), getAuthor(), getEdition());
    }

    public void reduce_amount() {
        if (getBooksAmount() == 1) {
            setBooksAmount(0);
            setStatus("Unavailable");
        } else {
            setBooksAmount(getBooksAmount() - 1);
        }
    }
}

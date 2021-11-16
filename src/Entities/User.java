package Entities;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(
            name = "User_Book",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    Set<Book> books = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "is_active")
    private int is_active;

    private String user_active;

    private String name;

    private String phone_num;

    private String type;

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String email, String name, String phone_num, String type, int is_active) {
        this.user_active = "Active";
        this.email = email;
        this.is_active = is_active;
        this.name = name;
        this.phone_num = phone_num;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public int getIs_Active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
        if (is_active == 0){
            user_active = "Deactivated";
        }
        else {
            user_active = "Active";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        this.password = pbkdf2PasswordEncoder.encode(password);
    }

    public String getPassword() {
        return password;
    }

    public String getUser_active() {
        return user_active;
    }

    public void setUser_active(String user_active) {
        this.user_active = user_active;
    }
}

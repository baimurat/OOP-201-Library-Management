package Entities;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="librarians")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int librarian_id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String phone_num;

    private String password;

    public Librarian() {
    }

    public Librarian(String email, String name, String phone_num) {
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
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

    public int getId() {
        return librarian_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
        this.password = pbkdf2PasswordEncoder.encode(password);
    }
}

package GUI.Views.Authentication;

import Entities.Book;
import Entities.Librarian;
import Entities.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class Login {
    private User sUser;

    private Librarian lLibrarian;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorText;

    @FXML
    void initialize() {
        loginBtn.setOnAction(this::authenticate);

        signUpBtn.setOnAction(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Signup.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void authenticate(Event e) {
        // Check for User
        if (check_user()) {
            // Setting session user
            GUI.Session.user = sUser;
            // Changing to User Book views
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("../UserViews/Books.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        // Check for Librarian
        } else if (check_librarian()) {
            // Setting session user
            GUI.Session.librarian = lLibrarian;
            // Changing to Librarian Book views
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("../AdminViews/Books.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //
        else {
            errorText.setText("Incorrect Email or Password");
        }

    }


    private boolean check_user() {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        List<User> users = new ArrayList<>();

        try {
            session.beginTransaction();

            //Taking data from emailField and Making Query in table users
            users = session.createQuery("from User u where u.email=" + String.format("'%s'",emailField.getText())).list();

            session.getTransaction().commit();
            session.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("Checking User");

        // Looping through the Users list
        for (User user : users) {
            Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
            if (pbkdf2PasswordEncoder.matches(passwordField.getText(), user.getPassword()) && user.getIs_Active() == 1){
                // If password matches declaring session user and returning True
                sUser = user;
                return true;
            }
            return false;
        }

        return false;
    }

    private boolean check_librarian() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Librarian.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        List<Librarian> librarians = new ArrayList<>();

        try {
            session.beginTransaction();
            // Taking data from emailField and Making Query in table librarians
            librarians = session.createQuery("from Librarian l where l.email=" + String.format("'%s'",emailField.getText())).list();

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Checking Librarian");
        for (Librarian librarian : librarians) {
            Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

            if (pbkdf2PasswordEncoder.matches(passwordField.getText(), librarian.getPassword())){
                // If password matches declaring session user and returning True
                lLibrarian = librarian;
                return true;
            }
            return false;
        }

        return false;
    }
}

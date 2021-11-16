package GUI.Views.Authentication;

import Entities.Book;
import Entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

public class Signup {

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField typeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField;

    @FXML
    private TextField pass2Field;

    @FXML
    private Text errotText;

    @FXML
    private Button signUpBtn;

    @FXML
    void initialize() {
        cancelBtn.setOnAction(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        signUpBtn.setOnAction(e -> {
            if (nameField.getText().equals("") || typeField.getText().equals("")
                    || phoneField.getText().equals("") || passField.getText().equals("")
                    || pass2Field.getText().equals("") || emailField.getText().equals("")
            ) {
                raise_error();
            } else {
                if (!pass2Field.getText().equals(passField.getText())) {
                    errotText.setText("Password are not matching");
                } else {
                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(Book.class)
                            .buildSessionFactory();

                    Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();
                        // Setting empty book set
                        Set<Book> bookSet = new HashSet<>();
                        // Getting data from signup form and creating new user instance
                        User user = new User(emailField.getText(), nameField.getText(), phoneField.getText(), typeField.getText(), 1);
                        user.setBooks(bookSet);

                        // Setting hashed password for user
                        user.setPassword(passField.getText());

                        session.persist(user);

                        session.getTransaction().commit();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    try {
                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);

                        //This line gets the Stage information
                        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                        window.setScene(tableViewScene);
                        window.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void raise_error() {
        errotText.setText("Fields cant be empty");
    }

}

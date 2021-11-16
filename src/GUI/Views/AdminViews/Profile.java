package GUI.Views.AdminViews;

import Entities.Librarian;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Profile {

    @FXML
    private Text profileView;

    @FXML
    private Text usersView;

    @FXML
    private Text booksView;

    @FXML
    private Button logOut;

    @FXML
    private Text name;

    @FXML
    private Text phoneNumber;

    @FXML
    private Text email;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNUmberField;

    @FXML
    private TextField emailField;

    @FXML
    private Button editProfileBtn;

    @FXML
    private Text name1;

    @FXML
    private TextField passField;


    @FXML
    void initialize() {
        Librarian librarian1 = GUI.Session.librarian;

        nameField.setText(librarian1.getName());
        phoneNUmberField.setText(librarian1.getPhone_num());
        emailField.setText(librarian1.getEmail());


        editProfileBtn.setOnAction(e -> {
            Librarian librarian = GUI.Session.librarian;

            Alert alert;
            // Validating Fields
            if (nameField.getText().equals("") || phoneNUmberField.getText().equals("") || emailField.getText().equals("")) {
                raise_error();
            } else {

                SessionFactory factory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Librarian.class)
                        .buildSessionFactory();

                Session session = factory.getCurrentSession();

                try {
                    session.beginTransaction();

                    Librarian myLibrarian = session.get(Librarian.class, librarian.getId());
                    // Updating Librarian
                    myLibrarian.setEmail(emailField.getText());
                    myLibrarian.setEmail(nameField.getText());
                    myLibrarian.setEmail(phoneNUmberField.getText());
                    // If password is not empty set new password
                    if (!passField.getText().equals("")){
                        myLibrarian.setPassword(passField.getText());
                    }

                    session.getTransaction().commit();
                    session.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Account successfully updated", ButtonType.YES);
                    alert.showAndWait();
                }
            }
        });


        booksView.setOnMouseClicked(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Books.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        usersView.setOnMouseClicked(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Users.fxml"));
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

    private void raise_error() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fields cant be empty", ButtonType.YES);
        alert.showAndWait();
    }

}

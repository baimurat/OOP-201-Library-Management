package GUI.Views.UserViews;

import Entities.Book;
import Entities.User;
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
    private Text myBooksView;

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
    private TextField passField;


    @FXML
    private TextField typeField;

    @FXML
    void initialize() {
        User user1 = GUI.Session.user;

        nameField.setText(user1.getName());
        phoneNUmberField.setText(user1.getPhone_num());
        emailField.setText(user1.getEmail());
        typeField.setText(user1.getType());

        editProfileBtn.setOnAction(e -> {
            User user = GUI.Session.user;

            Alert alert;

            if (nameField.getText().equals("") || phoneNUmberField.getText().equals("")
                    || emailField.getText().equals("") || typeField.getText().equals("")) {
                raise_error();
            } else {
                SessionFactory factory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Book.class)
                        .buildSessionFactory();

                Session session = factory.getCurrentSession();

                try {
                    session.beginTransaction();

                    User myUser = session.get(User.class, user.getUser_id());

                    myUser.setEmail(emailField.getText());
                    myUser.setEmail(nameField.getText());
                    myUser.setEmail(phoneNUmberField.getText());
                    myUser.setType(typeField.getText());

                    if (!passField.getText().equals("")) {
                        myUser.setPassword(passField.getText());
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
        myBooksView.setOnMouseClicked(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("MyBooks.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            } catch (Exception ex) {
                ex.printStackTrace();
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
        logOut.setOnMouseClicked(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("../Authentication/Login.fxml"));
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

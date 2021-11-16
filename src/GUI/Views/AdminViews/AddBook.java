package GUI.Views.AdminViews;

import Entities.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AddBook {

    @FXML
    private TextField isbnField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField editionField;

    @FXML
    private TextField amountField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;


    @FXML
    void initialize() {
        saveBtn.setOnAction(e -> {
            // Validating Form Fields
            if (isbnField.getText().equals("") || nameField.getText().equals("") || authorField.getText().equals("")
                    || editionField.getText().equals("")
                    || amountField.getText().equals("")) {
                raise_error();
            }
            else {
                    // Getting data from fields
                    int ISBN = Integer.parseInt(isbnField.getText());
                    int edition = Integer.parseInt(editionField.getText());
                    int amount = Integer.parseInt(amountField.getText());

                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(Book.class)
                            .buildSessionFactory();

                    Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();
                        //Creating new book instance
                        Book myBook = new  Book(ISBN, authorField.getText(), nameField.getText(), "Available", edition, amount);
                        // Saving book
                        session.save(myBook);
                        session.getTransaction().commit();
                        session.close();
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                    finally {
                        // If no error raised returning to books screen
                        try {
                            Parent tableViewParent = FXMLLoader.load(getClass().getResource("Books.fxml"));
                            Scene tableViewScene = new Scene(tableViewParent);

                            //This line gets the Stage information
                            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
            }
        });
        cancelBtn.setOnAction(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Books.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);

                //This line gets the Stage information
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();

                window.setScene(tableViewScene);
                window.show();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        });

    }
    private void raise_error() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fields cant be empty", ButtonType.YES);
        alert.showAndWait();
    }
}

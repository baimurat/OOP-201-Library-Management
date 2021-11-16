package GUI.Views.UserViews;

import Entities.Book;
import Entities.User;
import GUI.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static jdbc.GeneralDBMethods.execute_sql;
import static jdbc.GeneralDBMethods.get_user_books;

public class MyBooks {

    @FXML
    private Text profileView;

    @FXML
    private Text myBooksView;

    @FXML
    private Text booksView;

    @FXML
    private Button logOut;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, String> ISBN;

    @FXML
    private TableColumn<Book, String> Name;

    @FXML
    private TableColumn<Boolean, String> Author;

    @FXML
    private TableColumn<Boolean, Integer> Edition;

    @FXML
    private Button returnBtn;

    @FXML
    void initialize() {
        ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Edition.setCellValueFactory(new PropertyValueFactory<>("edition"));

        booksTable.getItems().setAll(get_user_books(Session.user));

        returnBtn.setOnAction(e -> {
            Book book = booksTable.getSelectionModel().getSelectedItem();
            User user = Session.user;

            Alert alert;

            if (book == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one book above", ButtonType.YES);
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want ot return this book:" + book + " ?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {

                    // Deleting relation between user and book
                    String sql = "DELETE FROM user_book WHERE book_id=" + book.getId() + "AND user_id=" + user.getUser_id();
                    execute_sql(sql);

                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(Book.class)
                            .addAnnotatedClass(User.class)
                            .buildSessionFactory();

                    org.hibernate.Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();
                        User myUser = session.get(User.class, GUI.Session.user.getUser_id());
                        // Removing book from users book list
                        myUser.getBooks().remove(book);
                        session.getTransaction().commit();
                        session.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            booksTable.getItems().setAll(get_user_books(Session.user));
        });

        logOut.setOnMouseClicked(e -> {

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
        profileView.setOnMouseClicked(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("Profile.fxml"));
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

}

package GUI.Views.AdminViews;

import Entities.Book;
import Entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static jdbc.GeneralDBMethods.execute_sql;
import static jdbc.GeneralDBMethods.get_all_books;

public class Books {

    @FXML
    private Text booksView;

    @FXML
    private Text usersView;

    @FXML
    private Text profileView;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button logOut;

    @FXML
    private TextField searchByTitle;

    @FXML
    private Button searchBytitleBtn;

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
    private TableColumn<Boolean, Integer> Amount;

    @FXML
    private TableColumn<Boolean, String> Status;

    @FXML
    private Button AddBook;

    @FXML
    void initialize() {
        ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Author.setCellValueFactory(new PropertyValueFactory<>("author"));
        Edition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("BooksAmount"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));


        booksTable.getItems().setAll(get_all_books(""));

        searchBytitleBtn.setOnAction(e -> {
            booksTable.getItems().setAll(get_all_books(searchByTitle.getText()));
        });

        deleteBtn.setOnAction(e -> {
            Book book = booksTable.getSelectionModel().getSelectedItem();

            Alert alert;

            if (book == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one book above", ButtonType.YES);
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + book + " ?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {

                    String sql = "DELETE FROM user_book WHERE book_id=" + book.getId();

                    // Deleting all relations of this book with users
                    execute_sql(sql);

                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(Book.class)
                            .addAnnotatedClass(User.class)
                            .buildSessionFactory();


                    Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();
                        Book myBook = session.get(Book.class, book.getId());
                        // Deleting Book
                        session.delete(myBook);
                        session.getTransaction().commit();
                        session.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            booksTable.getItems().setAll(get_all_books(""));

        });

        editBtn.setOnAction(e -> {
            Book book = booksTable.getSelectionModel().getSelectedItem();

            GUI.Session.book = book;

            Alert alert;

            if (book == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one book above", ButtonType.YES);
                alert.showAndWait();
            } else {
                try {
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("EditBook.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);

                    //This line gets the Stage information
                    Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

                    window.setScene(tableViewScene);
                    window.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        AddBook.setOnAction(e -> {
            try {
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
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

}

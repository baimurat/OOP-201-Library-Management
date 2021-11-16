package GUI.Views.UserViews;

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

import static jdbc.GeneralDBMethods.get_all_books;

public class Books {

    @FXML
    private Text profileView;

    @FXML
    private Text myBooksView;

    @FXML
    private Text booksView;

    @FXML
    private Button logOut;

    @FXML
    private TextField searchByTitle;

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
    private Button searchBytitleBtn;

    @FXML
    private Button takeBtn;

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

        takeBtn.setOnAction(e -> {
            // Getting selected book from table
            Book book = booksTable.getSelectionModel().getSelectedItem();
            Alert alert;

            // If no book was selected showing alert box
            if (book == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one book above", ButtonType.YES);
                alert.showAndWait();
            } else {

                // If book was selected showing confirmation box
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want ot take this book:" + book + " ?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                // If user agrees to take specific book
                if (alert.getResult() == ButtonType.YES) {

                    // Amount of available books equals 0
                    if (book.getBooksAmount() == 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION, "Book is unavailable", ButtonType.YES);
                        alert.showAndWait(); }

                    // Book available
                    else {
                        SessionFactory factory = new Configuration()
                                .configure("hibernate.cfg.xml")
                                .addAnnotatedClass(Book.class)
                                .addAnnotatedClass(User.class)
                                .buildSessionFactory();

                        Session session = factory.getCurrentSession();


                        boolean allowed = true;

                        try {
                            session.beginTransaction();
                            Book myBook = session.get(Book.class, book.getId());
                            User user = session.get(User.class, GUI.Session.user.getUser_id());

                            // Checking if user already has this book
                            for (Book b : user.getBooks()) {
                                if (b.getISBN().equals(myBook.getISBN())) {
                                    allowed = false;
                                }
                            }
                            // Checking if user allowed to add this book
                            if (allowed) {
                                myBook.reduce_amount();
                                user.getBooks().add(myBook);
                            }
                            // If not showing alert box that user already has his book
                            else {
                                alert = new Alert(Alert.AlertType.INFORMATION, "You cant add book that you already have", ButtonType.YES);
                                alert.showAndWait();
                            }
                            session.getTransaction().commit();
                            session.close();

                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        booksTable.getItems().setAll(get_all_books(""));
                    }
                }
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
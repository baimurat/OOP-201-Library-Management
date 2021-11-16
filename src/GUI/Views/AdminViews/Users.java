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

import java.util.ArrayList;
import java.util.List;

public class Users {

    @FXML
    private Text profileView;

    @FXML
    private Text booksView;

    @FXML
    private Button logOut;

    @FXML
    private TextField searchByName;

    @FXML
    private TableView<User> booksTable;

    @FXML
    private TableColumn<User, String> Name;

    @FXML
    private TableColumn<User, String> Email;

    @FXML
    private TableColumn<User, String> PhoneNumber;

    @FXML
    private TableColumn<?, ?> Type;

    @FXML
    private TableColumn<User, String> Active;

    @FXML
    private Button searchByNameBtn;

    @FXML
    private Button disableBtn;

    @FXML
    private Button activateUser;


    @FXML
    void initialize() {
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Active.setCellValueFactory(new PropertyValueFactory<>("user_active"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone_num"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));


        booksTable.getItems().setAll(get_all_users(""));

        searchByNameBtn.setOnAction(e -> {
            booksTable.getItems().setAll(get_all_users(searchByName.getText()));
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

        disableBtn.setOnAction(e -> {
            User user = booksTable.getSelectionModel().getSelectedItem();

            Alert alert;

            if (user == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one user above", ButtonType.YES);
                alert.showAndWait();
            }
            else if (user.getIs_Active() == 0){
                alert = new Alert(Alert.AlertType.INFORMATION, "User is already Deactivated", ButtonType.YES);
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to disable " +
                        "this User(Disabling wont allow user to login)", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {

                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(Book.class)
                            .buildSessionFactory();

                    Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();
                        // Getting user insatnce from db
                        User myUser = session.get(User.class, user.getUser_id());
                        // Setting user's user_active field to 0
                        myUser.setIs_active(0);

                        session.getTransaction().commit();
                        session.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        // Showing updated user in table
                        booksTable.getItems().setAll(get_all_users(searchByName.getText()));
                    }
                }

            }
        });

        activateUser.setOnAction(e -> {
            User user = booksTable.getSelectionModel().getSelectedItem();

            Alert alert;

            if (user == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "Please select one user above", ButtonType.YES);
                alert.showAndWait();
            }
            else if (user.getIs_Active() == 1){
                alert = new Alert(Alert.AlertType.INFORMATION, "User already active", ButtonType.YES);
                alert.showAndWait();
            }
            else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Activate " +
                        "this User(Activating will allow user to login)", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(Book.class)
                            .buildSessionFactory();

                    Session session = factory.getCurrentSession();

                    try {
                        session.beginTransaction();

                        User myUser = session.get(User.class, user.getUser_id());

                        myUser.setIs_active(1);
                        session.getTransaction().commit();
                        session.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        booksTable.getItems().setAll(get_all_users(searchByName.getText()));
                    }
                }
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


    private List<User> get_all_users(String query) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        List<User> users = new ArrayList<>();

        try {
            session.beginTransaction();

            if (!query.equals("")) {
                users = session.createQuery("from User b where b.name like '%" + query + "%'").list();
            } else {
                users = session.createQuery("from User").list();
            }

            session.getTransaction().commit();
            session.close();
            System.out.println(users.get(0).getIs_Active());
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}

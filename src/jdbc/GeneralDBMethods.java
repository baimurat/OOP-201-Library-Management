package jdbc;

import Entities.Book;
import Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class GeneralDBMethods {

    public static List<Book> get_all_books(String query) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Book.class).buildSessionFactory();
        Session session = factory.getCurrentSession();

        List<Book> books = new ArrayList<>();
        try {
            session.beginTransaction();

            if (!query.equals("")) {
                books = session.createQuery("from Book b  where b.name like '%" + query + "%'").list();
            } else {
                books = session.createQuery("from Book").list();
            }
            session.getTransaction().commit();
            session.close();

            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;

    }

    public static List<Book> get_user_books(User user) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        List<Book> books = new ArrayList<>();

        try {
            session.beginTransaction();


            User user1 = session.get(User.class, user.getUser_id());

            books = new ArrayList<>(user1.getBooks());

            System.out.println(books);
            session.getTransaction().commit();
            session.close();

            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void execute_sql(String sql) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library_db", "arslansd", "Weldire");

            pstmt = connection.prepareStatement(sql);
            int affectedRows = pstmt.executeUpdate();
            System.out.println(affectedRows + " row(s) affected !!");
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

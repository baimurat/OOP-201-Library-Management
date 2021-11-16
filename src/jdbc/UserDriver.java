package jdbc;

import Entities.Book;
import Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDriver {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            List<Book> books = session.createQuery("from Book").list();
            Set<Book> bookSet = new HashSet<>(books);

            User user = new User("a@a.com", "Arslan Sultanbek uulu", "0555013411", "Student", 1);
            user.setBooks(bookSet);
            user.setPassword("1234");
            session.persist(user);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

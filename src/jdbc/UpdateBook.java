package jdbc;

import Entities.Book;
import Entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UpdateBook {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            int bookId = 1;

            Book book = session.get(Book.class, bookId);

            System.out.println("Updating Student");
            book.setName("Keke Book");

            session.getTransaction().commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}

package jdbc;

import Entities.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReadAllBooks {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            List<Book> books = session.createQuery("from Book b where b.name='a'").list();

            for (Book i : books) {
                System.out.println(i);
            }

            session.getTransaction().commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}

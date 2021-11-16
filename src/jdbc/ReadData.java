package jdbc;

import Entities.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ReadData {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try{
            session.beginTransaction();

            Book myBook = session.get(Book.class, 1);

            System.out.println("Get complete " + myBook);

            session.getTransaction().commit();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

package jdbc;

import Entities.Book;
import Entities.Librarian;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LibrarianDriver {


    public static void main(String[] args){

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Librarian.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try{
            session.beginTransaction();


            Librarian librarian = new Librarian("li@li.com", "li", "131");
            librarian.setPassword("12345");

            session.save(librarian);

            session.getTransaction().commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

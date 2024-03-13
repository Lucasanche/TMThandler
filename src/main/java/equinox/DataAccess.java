package equinox;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DataAccess {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex){
            System.err.println("Hibernate configuration error: " + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }
    public static SessionFactory get(){
        return sessionFactory;
    }
}

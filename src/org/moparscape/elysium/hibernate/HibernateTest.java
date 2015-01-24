package org.moparscape.elysium.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by daniel on 18/01/2015.
 */
public class HibernateTest {

    public static void listCustomers(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List customers = session.createQuery("FROM Customer").list();
            for (Customer c : (List<Customer>) customers) {
                System.out.printf("Id=%d, Name=%s\n", c.getId(), c.getAccountOwnerName());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
//        SessionFactory sessionFactory = new Configuration()
//                .configure()
//                .addAnnotatedClass(Customer.class)
//                .buildSessionFactory();
//        listCustomers(sessionFactory);
    }
}

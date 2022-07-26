package dao;
import com.example.java_ee_project.HibernateUtil;
import entity.Child;
import entity.Wife;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ChildHibernateDAO {
    public static Session getCurrentSessionFromConfig() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Сессия успешно создана");
        return session;
    }

    public static void transactionDeleteChildExecute(Child child) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = getCurrentSessionFromConfig();
            transaction = session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.delete(child);
            System.out.println("Объект human удален из базы" + child.getId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public static void transactionNewChildExecute(Child child) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        System.out.println("Транзакция успешно открыта");
        session.persist(child);
        session.save(child);
        System.out.println("Объект child сохранен в базу");
        transaction.commit();
        System.out.println("Транзакция успешно закрыта");
        session.close();
        System.out.println("Сессия успешно закрыта");
        }

    public static Child simpleFindByID(Long id) {
        Session session = getCurrentSessionFromConfig();
        System.out.println("Простой метод поиска по id:");
        System.out.println(session.get(Child.class, id));
        Child foundChild = session.get(Child.class, id);
        session.close();
        System.out.println("Сессия успешно закрыта");
        return foundChild;
    }

    public static List<Child> hqlFindAll() {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Child");
        List<Child> children = query.list();

        transaction.commit();

        session.close();
        System.out.println("Сессия успешно закрыта");

        System.out.println("Сформирован лист детей");
        return children;
    }
}

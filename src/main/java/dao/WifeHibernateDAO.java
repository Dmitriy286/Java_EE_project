package dao;
import com.example.java_ee_project.HibernateUtil;
import entity.Child;
import entity.Wife;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WifeHibernateDAO {

    public static Session getCurrentSessionFromConfig() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Сессия успешно создана");
        return session;
    }

    public static void transactionDeleteWifeExecute(Wife wife) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = getCurrentSessionFromConfig();
            transaction = session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.delete(wife);
            System.out.println("Объект human удален из базы" + wife.getId());

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

    public static void transactionNewWifeExecute(Wife wife) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        System.out.println("Транзакция успешно открыта");
        session.persist(wife);
        session.save(wife);
        System.out.println("Объект wife сохранен в базу");
        transaction.commit();
        System.out.println("Транзакция успешно закрыта");
        session.close();
        System.out.println("Сессия успешно закрыта");
        }

    public static Wife simpleFindByID(int id) {
        Session session = getCurrentSessionFromConfig();
        System.out.println("Простой метод поиска по id:");
        System.out.println(session.get(Wife.class, id));
        Wife foundWife = session.get(Wife.class, id);
        session.close();
        System.out.println("Сессия успешно закрыта");
        return foundWife;
    }

    public static List<Wife> hqlFindAll() {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Wife");
        List<Wife> wifes = query.list();

        transaction.commit();

        session.close();
        System.out.println("Сессия успешно закрыта");

        System.out.println("Сформирован лист хуманов");
        return wifes;
    }

    public static Set<Child> findWifesChildrenHQL(int wifeId) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Child as c WHERE c.wife.id=:id");
        query.setParameter("id", wifeId);

//        Query query = session.createQuery("FROM Wife as w INNER JOIN Child as c ON w = c.wife");
//        List<Object[]> list = query.list();
//        for (Object[] objects : list) {
//            //todo как из получившейся строки достать wife и children в более читабельном виде?
//            System.out.println("Из списка обджектов:");
//            System.out.println(Arrays.toString(objects));
//            System.out.println(Arrays.toString(objects));
//        }

       /* при таком запросе ошибка
        null index column for collection: entity.Wife.children*/
//        Query query = session.createQuery("FROM Wife as w INNER JOIN FETCH w.children");

        Set<Child> children = (HashSet)query.list();

        transaction.commit();
        session.close();

        System.out.println("Сессия успешно закрыта");
        System.out.println("Сформирован лист детей");
        return children;
    }

    public static Set<Child> findWifesChildren(int wifeId) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Wife foundWife = session.get(Wife.class, wifeId);
        System.out.println("Тестим вывод жены: " + foundWife);

        Set<Child> children = foundWife.getChildren();
        System.out.println("Класс: " + children.getClass());
        System.out.println("Тест: " + children);
        System.out.println("Проверка, пустой ли: " + children.isEmpty());

        transaction.commit();
        session.close();

        return children;
    }
}

package dao;
import com.example.java_ee_project.HibernateUtil;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HumanHibernateDAO {

    public static Session getCurrentSessionFromConfig() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Сессия успешно создана");
        return session;
    }

    public static void transactionDeleteHumanExecute(Human human) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = getCurrentSessionFromConfig();
            transaction = session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.delete(human);
            System.out.println("Объект human удален из базы" + human.getId());

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

    public static void transactionNewHumanExecute(Human human) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();

        session.persist(human);
        session.save(human);

        transaction.commit();
        session.close();
        }

    public static Human simpleFindByID(int id) {
        Session session = getCurrentSessionFromConfig();
        System.out.println("Простой метод поиска по id:");
        System.out.println(session.get(Human.class, id));
        Human foundHuman = session.get(Human.class, id);
        session.close();
        System.out.println("Сессия успешно закрыта");
        return foundHuman;
    }

    public static List<Human> hqlFindAll() {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Human");
        List<Human> humanity = query.list();

        transaction.commit();
        session.close();

        System.out.println("Сессия успешно закрыта");
        System.out.println("Сформирован лист хуманов");
        return humanity;
    }

//    Query query = session.createQuery("from ContactEntity where firstName = :paramName");
//query.setParameter("paramName", "Nick");
//    List list = query.list();

//    // Определение Named SQL Query
//    @NamedQuery(
//            name = "getContacts",
//            query = "select ce from ContactEntity ce where ce.id >=:id"
//    )
//
//// Сущность
//    @Entity
//    @Table(name="contact", schema = "", catalog = "javastudy")
//    public class ContactEntity {
//    }
//. . .
//    // использование Named SQL Query
//    Query query = session.getNamedQuery("getContacts")
//            .setString("id", "10");

//    saveOrUpdate

    /*
    Пример обработки пакетов:
     Session session = sessionFactory.openSession();
     ransaction = session.beginTransaction();
     for(int i = 0; i < 100000; i++){
     Developer developer = new Developer();
     session.save(developer);
     if(i % 50 == 0){
        session.flush();
        session.clear();
        }
    }
   transaction.commit();
           session.close();
     */


}

package dao;

import com.example.java_ee_project.HibernateUtil;
import entity.Child;
import entity.Human;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

public class HibernateCriteria {


    public static Session getCurrentSessionFromConfig() {
//        Configuration configuration = new Configuration();
//        configuration.configure();
        //Вариант маппинга:
//        configuration.addAnnotatedClass(Human.class);
//        "/src/main/resources/hibernate.cfg.xml"

//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Сессия успешно создана");
        return session;

    }

    ////    Пример Criteria с необязательным обрамлением в транзакцию :
//Приведенный выше запрос вернет первые 10 записей из таблицы сущности User.
// Метод setMaxResults представляет собой аналог команды LIMIT в SQL-запросе.

    public static List<Human> findThreeHumans() {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();

        List<Human> humanity;
        humanity = session.createCriteria(Human.class)
                .setMaxResults(3).list();

        transaction.commit();
        session.close();

        return humanity;
    }


//    Чтобы прочитать определенное количество записей с с определенной позиции (LIMIT 2, 15)
//    необходимо дополнительно использовать метод setFirstResult :


    public static List<Human> findThreeHumansFromSecondPoint() {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();

        List<Human> humanity;
        humanity = session
                .createCriteria(Human.class)
                .setFirstResult(2)
                .setMaxResults(3)
                .list();

        transaction.commit();
        session.close();

        return humanity;

    }


    public static List<Child> findByNameJPAHibernate(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Child.class);

        Root<Child> root = cq.from(Child.class);

        Selection[] selection = {root.get("id"), root.get("name")};

        ParameterExpression<String> paramName = cb.parameter(String.class, "name");
        cq.select(cb.construct(Child.class, selection)).where(cb.like(root.get("name"), paramName));

        Query query = session.createQuery(cq);
        query.setParameter("name", name);

        List<Child> children = query.getResultList();

        session.close();

        return children;



    }
}

package com.example.java_ee_project;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();

        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            new RuntimeException(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    //другой вариант создания сессии:
    //        Configuration configuration = new Configuration();
//        configuration.configure();
//        Вариант маппинга:
//        configuration.addAnnotatedClass(Human.class);
//        "/src/main/resources/hibernate.cfg.xml"
//        SessionFactory sessionFactory = configuration.buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

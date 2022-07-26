package com.example.java_ee_project;

import dao.*;
import entity.Child;
import entity.Human;
import entity.Wife;
import org.jboss.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    //для логгирования:
    private static final Logger LOG = Logger.getLogger(HumanHibernateDAO.class.getName());

    private static JDBCexample jdbcQueries;
    private static Connection connection;

    static{
        jdbcQueries = new JDBCexample();
    }


    public static void shouldGetJdbcConnection() throws SQLException {
        try (Connection connection = jdbcQueries.getNewConnection()) {
            System.out.println(connection.isValid(1));
            System.out.println(connection.isClosed());
        }
    }

    public static void shouldGetDataSourceConnection() throws SQLException {
        DataSource dataSource = jdbcQueries.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection.isValid(1));
            System.out.println(connection.isClosed());
        }
    }

    public static void main(String[] args) throws SQLException {

        //Проверка подключенного драйвера JDBC
//        try {
//            Class.forName("org.postgresql.Driver");
//            System.out.println("Driver loaded successfully");
//            System.out.println(Class.forName("org.postgresql.Driver"));
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
////        shouldGetJdbcConnection(); //true //false
//        System.out.println("DataSource test:");
//        shouldGetDataSourceConnection();
//        connection = jdbcQueries.getNewConnection();
//
////        System.out.println("Тест создания схемы. JDBC:");
////        connection.createStatement().execute("create schema new_schema;");
//
//        String tableName = "test";
//        String schemaName = "new_schema";
//
////        jdbcQueries.createTestTable(schemaName, tableName);
////        connection.createStatement().execute("SELECT * FROM " + tableName);
//
//        String testInsertQuery = "INSERT INTO " + schemaName + "." + tableName + " VALUES (1, 'test_one')";
//        String testInsertQueryTwo = "INSERT INTO " + schemaName + "." + tableName + " VALUES (2, 'test_two')";
////        String testInsertQuery = "INSERT INTO " + schemaName + "." + tableName + " VALUES (3, 'test_three')";
//
////        String testInsertQuery = "INSERT INTO test VALUES (1, 'test_one')";
//
////        jdbcQueries.insertRow(testInsertQuery);
////        jdbcQueries.insertRow(testInsertQueryTwo);
//
//        ResultSet resultSet = jdbcQueries.findByName(schemaName, tableName, "test_two");
//        resultSet.next();
//        System.out.println("Достаем данные из result set:");
//        System.out.println(resultSet.getString("name"));
//        System.out.println(resultSet.getInt("id"));
//        resultSet.close();
//
//
//
////        jdbcQueries.executeUpdate(testInsertQuery);
////        jdbcQueries.transaction();
//
////        jdbcQueries.dropRow(tableName, 1);
////        jdbcQueries.createTestTable("new_schema", "human");
////        jdbcQueries.insertFewRows("new_schema", "human");
//        System.out.println(jdbcQueries.getAllHumans());
//        jdbcQueries.insertNewHuman("Five");
//        System.out.println(jdbcQueries.getAllHumans());
////        System.out.println("Удаляем строку: ");
////        jdbcQueries.dropRow("human", 6);
//        System.out.println(jdbcQueries.getAllHumans());
//
//
////        HumanDAO dao = new HumanDAO();
////        Human result_1 = dao.findById(4);
////        System.out.println(result_1);
//
//        HumanHibernateDAO humanDAO = new HumanHibernateDAO();
////        humanDAO.transactionDeleteHumanExecute(result_1);
//
////        Human testHuman = new Human();
//////        testHuman.setId(10);
////        testHuman.setName("testNameThree");
////        sc.transactionNewHumanExecute(testHuman);
//
//
//        System.out.println("Лист людей, созданный с помощью HQL-запроса:");
//        System.out.println(HumanHibernateDAO.hqlFindAll());
//        System.out.println("Лист людей, созданный с помощью HQL-запроса, вывожу с помощью логгера:");
//        LOG.info(HumanHibernateDAO.hqlFindAll());
//
//        HumanHibernateDAO.simpleFindByID(78);
////        System.out.println(sc.hqlFindAll());
//
//
////        HibernateCriteria hc = new HibernateCriteria();
////        System.out.println(hc.findThreeHumans());
////        System.out.println(hc.findThreeHumansFromSecondPoint());
//
//        System.out.println("SQL-запрос Criteria:");
//        System.out.println(HibernateCriteria.findThreeHumans());
//        System.out.println(HibernateCriteria.findThreeHumansFromSecondPoint());
//
////        Human newHuman = new Human();
////        newHuman.setName("Ivan");
////        newHuman.setMoney(5000);
////        HumanHibernateDAO.transactionNewHumanExecute(newHuman);
//
////        Wife newWife = new Wife();
////        newWife.setName("Mary");
////        newWife.setMoney(100);
////        WifeHibernateDAO.transactionNewWifeExecute(newWife);
////
//
//        Human h = HumanHibernateDAO.simpleFindByID(19);
//        Wife w = WifeHibernateDAO.simpleFindByID(1);
//        System.out.println(h);
//        System.out.println(w);
//
////        MoneyTransfering.simpleMoneyTransfering(19, 1, 100);
//
//        Human h1 = HumanHibernateDAO.simpleFindByID(19);
//        Wife w1 = WifeHibernateDAO.simpleFindByID(1);
//
//        System.out.println(h1);
//        System.out.println(w1);
//
////        MoneyTransfering.transactionMoneyTransfering(19, 1, 100);
//
//        Human h2 = HumanHibernateDAO.simpleFindByID(19);
//        Wife w2 = WifeHibernateDAO.simpleFindByID(1);
//        System.out.println(h2);
//        System.out.println(w2);
//
////        Child child_one = new Child();
////        child_one.setName("Mike");
////        child_one.setAge(10);
////        child_one.setWife(w2);
////        ChildHibernateDAO.transactionNewChildExecute(child_one);
////
////        Child child_two = new Child();
////        child_two.setName("Greg");
////        child_two.setAge(15);
////        child_two.setWife(w2);
////        ChildHibernateDAO.transactionNewChildExecute(child_two);
//
//        Wife w3 = WifeHibernateDAO.simpleFindByID(1);
//        System.out.println(w3);
//
////если так просто запускаем, то выскакивает ошибка:
////Exception in thread "main" org.hibernate.LazyInitializationException:
//// failed to lazily initialize a collection of role: entity.Wife.children, could not initialize proxy - no Session
//
////        System.out.println(w3.getChildren());
//
        System.out.println(WifeHibernateDAO.findWifesChildren(1));
//        System.out.println(WifeHibernateDAO.findWifesChildrenHQL(1));
//
////        System.out.println(HibernateCriteria.findByNameJPAHibernate("Oleg"));

    }
}

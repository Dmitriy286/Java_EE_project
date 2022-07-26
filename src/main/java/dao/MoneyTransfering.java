package dao;

import com.example.java_ee_project.HibernateUtil;
import entity.Human;
import entity.Wife;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoneyTransfering {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "admin";

/*    !!!ЭТО БЛОК РАБОТЫ JDBC. ПРИ ВОЗНИКНОВЕНИИ ОШИБКИ ДЕНЬГИ СПИСЫВАЮТСЯ С ПЕРВОГО СЧЕТА,
    НО НЕ ПОЯВЛЯЮТСЯ НА ВТОРОМ!!!*/

    public static Connection getNewConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("---!!!Здесь загружается драйвер!!!---");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(url, user, password);
    }

    public static void simpleMoneyTransfering(int humanId, int wifeId, int summ) {
        Connection connection = null;
        PreparedStatement testStatement = null;
        PreparedStatement getHumanMoneyStatement = null;
        PreparedStatement getWifeMoneyStatement = null;

        int humanMoney = 0;
        int wifeMoney = 0;

        String testQuery = "SELECT money FROM new_schema.human WHERE id = ?";

        String humanMoneyQuery = "SELECT * FROM new_schema.human WHERE id = ?";
        String wifeMoneyQuery = "SELECT * FROM new_schema.wife WHERE id = ?";
        try {
            connection = getNewConnection();

            testStatement = connection.prepareStatement(testQuery);
            testStatement.setInt(1, humanId);
            testStatement.execute();
            System.out.println("Тест вытаскивания результата без Result Set:");
            System.out.println(testStatement);
            System.out.println(testStatement.getResultSet());
            System.out.println(testStatement.getResultSet().next());
            System.out.println(testStatement.getResultSet());

            getHumanMoneyStatement = connection.prepareStatement(humanMoneyQuery);
            getHumanMoneyStatement.setInt(1, humanId);
            getHumanMoneyStatement.execute();
            getHumanMoneyStatement.getResultSet().next();
            humanMoney = getHumanMoneyStatement.getResultSet().getInt("money");

            getWifeMoneyStatement = connection.prepareStatement(wifeMoneyQuery);
            getWifeMoneyStatement.setInt(1, wifeId);
            getWifeMoneyStatement.execute();
            getWifeMoneyStatement.getResultSet().next();
            wifeMoney = getWifeMoneyStatement.getResultSet().getInt("money");
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        finally {
            try {
                connection.close();
                getHumanMoneyStatement.close();
                getWifeMoneyStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        PreparedStatement humanStatement = null;
        PreparedStatement wifeStatement = null;
        int humanLessMoney = humanMoney - summ;
        int wifeMoreMoney = wifeMoney + summ;

        String humanQuery = "UPDATE new_schema.human SET money = ? WHERE id = ?;";
        String wifeQuery = "UPDATE new_schema.wife SET money = ? WHERE id = ?;";
        try {
            connection = getNewConnection();

            humanStatement = connection.prepareStatement(humanQuery);
            humanStatement.setInt(1, humanLessMoney);
            humanStatement.setInt(2, humanId);
            humanStatement.execute();

            //внезапное тестовое прерывание соединения
//            connection.close();
//            humanStatement.close();

            wifeStatement = connection.prepareStatement(wifeQuery);
            wifeStatement.setInt(1, wifeMoreMoney);
            wifeStatement.setInt(2, wifeId);
            wifeStatement.execute();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        finally {
            try {
                connection.close();
                humanStatement.close();
                wifeStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // !!!ЭТО БЛОК С ИСПОЛЬЗОВАНИЕМ ТРАНЗАКЦИИ. ПРОВЕРКА ДЕЙСТВИЯ ROLLBACK!!!

    public static Session getCurrentSessionFromConfig() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Сессия успешно создана");
        return session;
    }

    public static void transactionMoneyTransfering(int humanId, int wifeId, int summ) {
        Session session = getCurrentSessionFromConfig();
        Transaction transaction = session.beginTransaction();
        Human h = HumanHibernateDAO.simpleFindByID(humanId);

        int humanMoney = h.getMoney();
        h.setMoney(humanMoney - summ);
        session.update(h);

// Внезапное тестовое прерывание соединения
//        session.close();

        Wife w = WifeHibernateDAO.simpleFindByID(wifeId);
        int wifeMoney = w.getMoney();
        w.setMoney(wifeMoney + summ);
        session.update(w);

        transaction.commit();
        session.close();
    }

}

package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;

public class DataBase {

    String url = "jdbc:postgresql://pg:5432/studs";
    String user = "postgres";
    String password = "123456";
    //String user = "s288870";
    //String password = "qta730";
    Connection con;

    static final Logger logger = LogManager.getRootLogger();

    public DataBase(/*String url_arg, String user_arg, String password_arg*/) {
        /*this.url = url_arg;
        this.user = user_arg;
        this.password = password_arg;*/
        try {
            this.con = DriverManager.getConnection(url, user, password);
            logger.info("Подключение к базе данных успешно");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка подключения к базе данных");
        }

    }

    public ResultSet executeQuery(String q) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public int executeUpdate(String q) {
        try {
            Statement st = con.createStatement();
            int result = st.executeUpdate(q);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

}

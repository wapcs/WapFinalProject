package utility;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    Context ctx = null;

    Statement stmt = null;
    ResultSet rs = null;

    public static Connection getCon() throws NamingException, SQLException {
        Context initContext = new InitialContext();
        Connection con = null;
        Context envContext = (Context) initContext.lookup("java:comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/taskdb");
        con = ds.getConnection();
        return con;
    }
}

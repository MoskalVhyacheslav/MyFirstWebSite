package db;

import exceptions.DAOException;
import messages.DAOMessages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static DataSource dataSource;
    private static ConnectionPool instance;

    private ConnectionPool() {
        try {

            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Project");
            LOG.trace("Data source ==> " + dataSource);
        } catch (NamingException exception) {
            LOG.error(DAOMessages.ERR_WITH_NAMING);
        }
    }

    public synchronized static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public static Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            LOG.error(DAOMessages.ERR_CANNOT_GET_CONNECTION, ex);
            throw new DAOException(DAOMessages.ERR_CANNOT_GET_CONNECTION, ex);
        }
        return connection;
    }

}

package TestCommands.UserTest;

import DAO.BookDAO;
import DAO.DAOFactory;
import DAO.OrderDAO;
import DAO.UserDAO;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddOrderCommandTest {
    private String login;
    private String password;
    private String title;
    private String author;

    @Before
    public void before() throws DAOException, SQLException {
        login = "user";
        password = HashingPassword.hash("user");

        title = "title";
        author = "author";

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login, "user@gmail.com", password);

        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.insertBook(title, author, "edition",
                LocalDate.now(), 0, 0);
    }

    @After
    public void after() throws DAOException,SQLException{
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.deleteOrder(login,title,author);

        BookDAO bookDAO =DAOFactory.getBookDAO();
        bookDAO.deleteBook(title,author);

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void addOrder() throws Exception {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.insertOrder(login,title,author,15);
    }

    @Test(expected = DAOException.class)
    public void addOrderAgain() throws DAOException{
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.insertOrder(login,title,author,15);
    }
}
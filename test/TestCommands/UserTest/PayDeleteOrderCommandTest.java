package TestCommands.UserTest;

import DAO.BookDAO;
import DAO.DAOFactory;
import DAO.OrderDAO;
import DAO.UserDAO;
import entity.Order;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class PayDeleteOrderCommandTest {
    private String login;
    private String title;
    private String author;

    @Before
    public void before() throws DAOException {
        login = "user";
        String password = HashingPassword.hash("user");

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login, "user@gmail.com", password);

        title = "book";
        author = "author";

        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.insertBook(title, author,
                "edition", LocalDate.now(), 5, 9);

        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.insertOrder(login, title, author, 5);
    }

    @After
    public void after() throws DAOException {
        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.deleteBook(title, author);

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void testDeleteOrder() throws Exception{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.selectUserBalance(login);

        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        Order order = orderDAO.selectOrderByBookAndUser(login,title,author);
    }

}
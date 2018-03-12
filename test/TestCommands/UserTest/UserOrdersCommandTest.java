package TestCommands.UserTest;

import DAO.BookDAO;
import DAO.DAOFactory;
import DAO.OrderDAO;
import DAO.UserDAO;
import entity.Book;
import entity.Order;
import exceptions.DAOException;
import javafx.util.Pair;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserOrdersCommandTest {
    private String login;
    private String password;
    private String title;
    private String author;
    List<Order> orders = new ArrayList<>();

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

        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.insertOrder(login, title, author, 10);

        orders.add(orderDAO.selectOrderByBookAndUser(login, title, author));
    }

    @After
    public void after() throws DAOException,SQLException{
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        orderDAO.deleteOrder(login,title,author);

        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.deleteBook(title,author);

        UserDAO userDAO =DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void test() throws Exception {
        OrderDAO orderDAO = DAOFactory.getOrderDAO();
        Pair<List<Order>, List<Book>> pair = orderDAO.selectOrderByUser(login);

        Assert.assertEquals(orders, pair.getKey());
    }
}
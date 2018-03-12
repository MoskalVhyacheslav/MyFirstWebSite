package TestCommands.GuestTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import entity.Role;
import entity.User;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class LogInCommandTest {
    private String login;
    private String password;
    UserDAO userDAO = DAOFactory.getUserDAO();

    @Before
    public void before() throws DAOException, SQLException {
        login = "user";
        password = HashingPassword.hash("user");

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login, "user@gmail.com", password);
        userDAO.insertNewLibrarian("librarian","librarian@gmail.com"
                ,HashingPassword.hash("libr"));
    }

    @After
    public void after() throws DAOException, SQLException {
        userDAO.deleteUser(login);
        userDAO.deleteUser("librarian");
    }

    @Test
    public void logInUser() throws Exception {
        User user = userDAO.authorizeAccount(login, password);
        Role userRole = Role.getRole(user);

        Assert.assertEquals(userRole.toString().toLowerCase(),"user");
    }

    @Test
    public void logInLibrarian() throws Exception {
        User user = userDAO.authorizeAccount("librarian",HashingPassword.hash("libr"));
        Role userRole = Role.getRole(user);

        Assert.assertEquals(userRole.toString().toLowerCase(),"librarian");
    }

    @Test
    public void logInAdmin() throws Exception {
        User user = userDAO.authorizeAccount(login, password);
        Role userRole = Role.getRole(user);

        Assert.assertEquals(userRole.toString().toLowerCase(),"admin");
    }

    @Test
    public void logInFail() throws Exception {
        User user = userDAO.authorizeAccount("FailUser", password);
        Assert.assertNull(user);
    }


}
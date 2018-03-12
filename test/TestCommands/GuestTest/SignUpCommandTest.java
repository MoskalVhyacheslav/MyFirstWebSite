package TestCommands.GuestTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import entity.User;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SignUpCommandTest {
    private UserDAO userDAO = DAOFactory.getUserDAO();
    private String login;
    private String password;

    @Before
    public void before() {
        login = "user";
        password = HashingPassword.hash("user");
    }

    @After
    public void after() throws DAOException {
        userDAO.deleteUser(login);
    }

    @Test
    public void signUp() throws Exception {
        userDAO.insertNewAccount(login, "user@gmail.com", password);

        User user = userDAO.findUserByLogin(login);
        Assert.assertEquals(login, user.getLogin());
    }

    @Test(expected = DAOException.class)
    public void signUpWithSameTitle() throws Exception {
        userDAO.insertNewAccount(login, "user@gmail.com", password);
    }

    @Test(expected = DAOException.class)
    public void signUpWithSameMail() throws Exception {
        userDAO.insertNewAccount(login, "user@gmail.com", password);
    }

}
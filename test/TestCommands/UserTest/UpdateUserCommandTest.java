package TestCommands.UserTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import entity.User;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateUserCommandTest {
    String login;
    String password;
    String mail;

    @Before
    public void before() throws DAOException {
        login = "юзер";
        password = "пароль";
        mail = "user@gmail.com";

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login, mail, HashingPassword.hash(password));
    }

    @After
    public void after() throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void testUpdatePassword() throws Exception {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.updateUserPassword(login, password);

        User user = userDAO.findUserByLogin(login);
        Assert.assertEquals(password, user.getPassword());
    }

    @Test
    public void testUpdateMail() throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.updateUserMail(login, "updatemail@gmail.com");

        User user = userDAO.findUserByLogin(login);
        Assert.assertEquals(mail, user.getMail());
    }

    @Test(expected = DAOException.class)
    public void testUpdateExistMail()throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.updateUserMail(login, "updatemail@gmail.com");
    }
}
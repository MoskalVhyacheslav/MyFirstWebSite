package TestCommands.AdminTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import entity.User;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnblockUserCommandTest {
    private String login;

    @Before
    public void before()throws DAOException{
        login = "user";
        String password = HashingPassword.hash("password");

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login,"user@gmail.com",password);
    }

    @After
    public void after()throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void testUnblockUser() throws Exception {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.blockUser(login);
        userDAO.unblockUser(login);

        User user = userDAO.findUserByLogin(login);
        Assert.assertEquals(false,user.isBlocked());
    }
}
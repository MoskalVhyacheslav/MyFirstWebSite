package TestCommands.UserTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ChangeBalanceCommandTest {
    private String login;

    @Before
    public void before()throws DAOException{
        login="user";
        String password = HashingPassword.hash("пароль");

        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount(login,"user@gmail.com",password);
    }

    @After
    public void after()throws DAOException{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser(login);
    }

    @Test
    public void testChangeBalance() throws Exception {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.updateUserBalance(login,15);

        int  balance = (int) userDAO.selectUserBalance(login);
        Assert.assertEquals(15,balance);
    }

}
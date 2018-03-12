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

import java.util.ArrayList;
import java.util.List;

public class GetUsersCommandTest {
    List<User> userList = new ArrayList<>();

    @Before
    public void before() throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewAccount("user1", "user1@gmail.com", HashingPassword.hash("user"));
        userDAO.insertNewAccount("user2", "user2@gmail.com", HashingPassword.hash("user"));
        userDAO.insertNewAccount("user3", "user3@gmail.com", HashingPassword.hash("user"));

        userList.add(new User("user1", "user1@gmail.com", HashingPassword.hash("user")));
        userList.add(new User("user2", "user2@gmail.com", HashingPassword.hash("user")));
        userList.add(new User("user3", "user3@gmail.com", HashingPassword.hash("user")));
    }

    @After
    public void after() throws DAOException {
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser("user1");
        userDAO.deleteUser("user2");
        userDAO.deleteUser("user3");
    }

    @Test
    public void getUsers() throws Exception {
        UserDAO userDAO = DAOFactory.getUserDAO();
        List<User> list = userDAO.selectUsers();
        Assert.assertEquals(list,userList);
    }

}
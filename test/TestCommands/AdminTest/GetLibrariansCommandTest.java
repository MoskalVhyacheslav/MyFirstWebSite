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

public class GetLibrariansCommandTest {
    List<User> librarianList = new ArrayList<>();

    @Before
    public void before()throws DAOException{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewLibrarian("librarian1", "librarian1@gmail.com", HashingPassword.hash("user"));
        userDAO.insertNewLibrarian("librarian2", "librarian2@gmail.com", HashingPassword.hash("user"));
        userDAO.insertNewLibrarian("librarian3", "librarian3@gmail.com", HashingPassword.hash("user"));

        librarianList.add(new User("librarian1", "librarian1@gmail.com", HashingPassword.hash("user")));
        librarianList.add(new User("librarian2", "librarian2@gmail.com", HashingPassword.hash("user")));
        librarianList.add(new User("librarian3", "librarian3@gmail.com", HashingPassword.hash("user")));
    }

    @After
    public void after()throws DAOException{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteUser("librarian1");
        userDAO.deleteUser("librarian2");
        userDAO.deleteUser("librarian3");
    }

    @Test
    public void getLibrarians() throws Exception {
        UserDAO userDAO = DAOFactory.getUserDAO();
        List<User> list= userDAO.selectLibrarians();
        Assert.assertEquals(list,librarianList);
    }

}
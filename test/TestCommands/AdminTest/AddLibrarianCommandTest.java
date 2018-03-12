package TestCommands.AdminTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddLibrarianCommandTest {
    private String login;

    @Before
    public void before()throws DAOException{
        login ="librarian";
    }

    @After
    public void after()throws DAOException{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.deleteLibrarian(login);
    }

    @Test
    public void testAddLibrarian()throws DAOException{
        UserDAO userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewLibrarian(login,"user@gmail.com",
                HashingPassword.hash("password"));
    }

}
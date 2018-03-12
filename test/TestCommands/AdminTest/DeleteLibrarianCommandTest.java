package TestCommands.AdminTest;

import DAO.DAOFactory;
import DAO.UserDAO;
import exceptions.DAOException;
import util.HashingPassword;
import org.junit.Before;
import org.junit.Test;

public class DeleteLibrarianCommandTest {
    private String login;

    @Before
    public void before()throws DAOException{
        login ="librarian";
        String password = HashingPassword.hash("librarian");

        UserDAO  userDAO = DAOFactory.getUserDAO();
        userDAO.insertNewLibrarian(login,"libr@gmail.com",password);
    }

    @Test
    public void testDelete() throws Exception {
        UserDAO userDAO =DAOFactory.getUserDAO();
        userDAO.deleteLibrarian(login);
    }

}
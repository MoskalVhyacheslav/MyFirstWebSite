package TestCommands.AdminTest;

import DAO.BookDAO;
import DAO.DAOFactory;
import entity.Book;
import exceptions.DAOException;
import org.junit.*;

import java.time.LocalDate;

public class AddBookCommandTest {
    private String title;
    private String author;
    private Book book;

    @Before
    public void before() throws DAOException {
        title = "title";
        author = "author";

        book = new Book(title, author, "edition",
                LocalDate.now(), 3, 4);
    }

    @After
    public void after() throws DAOException {
        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.deleteBook(title, author);
    }

    @Test
    public void testAddBook() throws DAOException {
        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.insertBook(title, author, "edition",
                LocalDate.now(), 3, 4);

        Book expected = bookDAO.selectByTitleAndAuthor(title, author);
        Assert.assertEquals(book,expected);
    }
}
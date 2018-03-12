package TestCommands.BookTest;

import DAO.BookDAO;
import DAO.DAOFactory;
import entity.Book;
import exceptions.DAOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBooksCommandTest {
    List<Book> bookList = new ArrayList<>();

    @Before
    public void before() throws DAOException {
        Book book = new Book
                ("книга", "автор", "издательство", LocalDate.now(), 5, 4);
        Book book2 = new Book
                ("азбука", "читатель", "неизвестность", LocalDate.MAX, 5, 4);

        Book book3 = new Book
                ("я", "я", "я", LocalDate.MIN, 5, 4);

        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.insertBook("книга", "автор",
                "издательство", LocalDate.now(), 5, 4);
        bookDAO.insertBook("азбука", "читатель",
                "неизвестность", LocalDate.MAX, 5, 4);
        bookDAO.insertBook("я", "я", "я",
                LocalDate.MIN, 5, 4);

        bookList.add(book);
        bookList.add(book2);
        bookList.add(book3);
    }

    @After
    public void after() throws DAOException {
        BookDAO bookDAO = DAOFactory.getBookDAO();
        for (int i = 0; i < 4; i++) {
            bookDAO.deleteBook(bookList.get(i).getTitle(), bookList.get(i).getAuthor());
        }
    }

    @Test
    public void testOrderByTitle() throws Exception {
        BookDAO bookDAO = DAOFactory.getBookDAO();
        bookDAO.orderByTitle();

        List<String> titles = new ArrayList<>();
        titles.add(bookList.get(0).getTitle());
        titles.add(bookList.get(1).getTitle());
        titles.add(bookList.get(2).getTitle());
    }

    @Test
    public void testOrderByAuthor() {

    }

    @Test
    public void testOrderByEdition() {

    }

    @Test
    public void testOrderByEditionDate() {

    }

}